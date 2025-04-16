import navigationGraph from '../data/navigationData';

// --- scaling factor ---
const UNITS_TO_METRES_FACTOR = 0.06; // Approx. 1 unit = 0.06 metres

// Helper function to calculate distance between two nodes
export const calculateDistance = (node1, node2) => {
  return Math.sqrt(
    Math.pow(node1.x - node2.x, 2) + Math.pow(node1.y - node2.y, 2)
  );
};

// Heuristic function for A* algorithm (Euclidean distance)
const heuristic = (node, goalNode) => {
  return calculateDistance(node, goalNode);
};

// Implementation of the A* algorithm
export const findShortestPath = (startNodeName, endNodeName) => {
  const { nodeMap } = navigationGraph;
  
  // Get the start and end nodes
  const startNode = nodeMap[startNodeName];
  const endNode = nodeMap[endNodeName];
  
  if (!startNode || !endNode) {
    console.error('Start or end node not found');
    return { path: [], distance: 0 };
  }
  
  // Set of visited nodes
  const closedSet = new Set();
  
  // Set of discovered nodes to be evaluated
  const openSet = new Set([startNodeName]);
  
  // For each node, which node it can most efficiently be reached from
  const cameFrom = {};
  
  // For each node, the cost of getting from the start node to that node
  const gScore = {};
  gScore[startNodeName] = 0;
  
  // For each node, the total cost of getting from the start node to the goal
  // by passing by that node. fScore(n) = gScore(n) + heuristic(n)
  const fScore = {};
  fScore[startNodeName] = heuristic(startNode, endNode);
  
  while (openSet.size > 0) {
    // Find node in openSet with lowest fScore
    let currentNodeName = null;
    let lowestFScore = Infinity;
    
    openSet.forEach(nodeName => {
      if ((fScore[nodeName] || Infinity) < lowestFScore) {
        lowestFScore = fScore[nodeName];
        currentNodeName = nodeName;
      }
    });
    
    if (currentNodeName === endNodeName) {
        const path = reconstructPath(cameFrom, currentNodeName, nodeMap);

        // Calculate total distance IN UNITS first
        let distanceInUnits = 0;
        for (let i = 0; i < path.length - 1; i++) {
            distanceInUnits += calculateDistance(path[i], path[i + 1]);
        }
        // Convert total distance to metres for the final result object
        const distanceInMetres = distanceInUnits * UNITS_TO_METRES_FACTOR;

        return { 
            path, 
            distance: distanceInMetres 
        };
    }
    
    openSet.delete(currentNodeName);
    closedSet.add(currentNodeName);
    
    const currentNode = nodeMap[currentNodeName];
    
    // Evaluate all neighbors
    for (const neighborName of currentNode.connections) {
      if (closedSet.has(neighborName)) {
        continue; // Ignore already evaluated neighbors
      }
      
      const neighborNode = nodeMap[neighborName];
      
      // Prevent direct room-to-room connections unless they are the start and end nodes
      // This forces paths through hallways and junctions
      if (currentNode.type === "room" && neighborNode.type === "room" &&
          !(currentNodeName === startNodeName && neighborName === endNodeName) && 
          !(neighborName === startNodeName && currentNodeName === endNodeName)) {
        continue; // Skip direct room-to-room connections
      }
      
      // Tentative gScore
      const tentativeGScore = gScore[currentNodeName] + calculateDistance(currentNode, neighborNode);
      
      if (!openSet.has(neighborName)) {
        // Discover a new node
        openSet.add(neighborName);
      } else if (tentativeGScore >= (gScore[neighborName] || Infinity)) {
        continue; // This is not a better path
      }
      
      // This path is the best until now, record it
      cameFrom[neighborName] = currentNodeName;
      gScore[neighborName] = tentativeGScore;
      fScore[neighborName] = gScore[neighborName] + heuristic(neighborNode, endNode);
    }
  }
  
  // No path found
  return { path: [], distance: 0 };
};

// Helper function to reconstruct the path from the cameFrom map
const reconstructPath = (cameFrom, currentNodeName, nodeMap) => {
  const path = [nodeMap[currentNodeName]];
  
  while (cameFrom[currentNodeName]) {
    currentNodeName = cameFrom[currentNodeName];
    path.unshift(nodeMap[currentNodeName]);
  }
  
  return path;
};

// Calculate the angle between two nodes in degrees (0-360)
const getAngle = (fromNode, toNode) => {
  const dx = toNode.x - fromNode.x;
  const dy = toNode.y - fromNode.y;
  
  // Calculate angle in radians, then convert to degrees
  let angle = Math.atan2(dy, dx) * (180 / Math.PI);
  
  // Normalize to 0-360 range
  if (angle < 0) {
    angle += 360;
  }
  
  return angle;
};

// Determine the turn description based on angle change
const getTurnDescription = (fromAngle, toAngle) => {
  // Calculate the difference between the angles
  let angleDiff = toAngle - fromAngle;
  
  // Normalize to -180 to 180 for easier turn detection
  if (angleDiff > 180) angleDiff -= 360;
  if (angleDiff < -180) angleDiff += 360;
  
  // Determine turn type based on angle difference
  if (Math.abs(angleDiff) < 20) {
    return "Continue straight";
  } else if (angleDiff >= 20 && angleDiff < 70) {
    return "Turn slightly right";
  } else if (angleDiff >= 70 && angleDiff < 110) {
    return "Turn right";
  } else if (angleDiff >= 110 && angleDiff <= 180) {
    return "Turn sharp right";
  } else if (angleDiff <= -20 && angleDiff > -70) {
    return "Turn slightly left";
  } else if (angleDiff <= -70 && angleDiff > -110) {
    return "Turn left";
  } else if (angleDiff <= -110 && angleDiff >= -180) {
    return "Turn sharp left";
  }
  
  return "Continue";
};

// Generate directions
export const generateDirections = (path) => {
  if (!path || path.length < 2) {
    return "No path found or path too short.";
  }

  const directions = [];
  let totalDistance = 0;
  
  // Start with origin point
  directions.push(`Start at ${path[0].name}.`);
  
  // Simplify the path by combining segments with the same direction
  let simplifiedPath = [path[0]];
  let lastDirection = null;
  
  for (let i = 1; i < path.length; i++) {
    const prevNode = path[i-1];
    const currentNode = path[i];
    
    // Calculate the cardinal direction
    const currentDirection = getCardinalDirection(prevNode, currentNode);
    
    // Add distance to total
    const segmentDistance = calculateDistance(prevNode, currentNode);
    totalDistance += segmentDistance;
    
    // If this is a significant node (room or last node) or the direction changes,
    // add it to our simplified path
    if (i === path.length - 1 || 
        currentNode.type === "room" || 
        currentDirection !== lastDirection) {
      simplifiedPath.push(currentNode);
      lastDirection = currentDirection;
    }
  }
  
  // Now generate directions from simplified path
  for (let i = 1; i < simplifiedPath.length; i++) {
    const prevNode = simplifiedPath[i-1];
    const currentNode = simplifiedPath[i];
    
    // Skip if this is a junction and not the destination
    if (currentNode.type === "Junction" && i < simplifiedPath.length - 1) {
      continue;
    }
    
    // Calculate distance for this segment
    const segmentDistanceUnits = calculateDistance(prevNode, currentNode);
    const segmentMeters = Math.round(segmentDistanceUnits * UNITS_TO_METRES_FACTOR);
    
    // Only include meaningful segments (longer than 1 meter)
    if (segmentMeters < 1 && i < simplifiedPath.length - 1) {
      continue;
    }
    
    // If not the first segment, we need a turn instruction
    if (i > 1) {
      const prevPrevNode = simplifiedPath[i-2];
      
      // Calculate the turn direction
      const incomingDir = getCardinalDirection(prevPrevNode, prevNode);
      const outgoingDir = getCardinalDirection(prevNode, currentNode);
      const turnDirection = getTurn(incomingDir, outgoingDir);
      
      if (turnDirection !== "continue straight") {
        // For the final segment, use special formatting
        if (i === simplifiedPath.length - 1) {
          directions.push(`Turn ${turnDirection} and walk ${segmentMeters} meters.`);
          directions.push(`${currentNode.name} should be on your ${getRelativePosition(outgoingDir)}.`);
        } else {
          directions.push(`Turn ${turnDirection} and walk ${segmentMeters} meters.`);
        }
      } else {
        // If continuing straight, just mention the distance
        directions.push(`Continue walking ${segmentMeters} meters.`);
      }
    } else {
      // First segment - just walk
      directions.push(`Walk ${segmentMeters} meters.`);
    }
  }
  
  // Add total distance at the end
  const totalMeters = Math.round(totalDistance * UNITS_TO_METRES_FACTOR);
  directions.push(`Total distance: ${totalMeters} meters.`);
  
  return directions.join("\n");
};

// Helper function to determine the relative position (left/right/ahead/behind)
const getRelativePosition = (direction) => {
  // When facing a direction, what's on your left/right
  switch (direction) {
    case "north": return "right";  // If facing north, destination is on your right
    case "east": return "right";   // If facing east, destination is on your right
    case "south": return "left";   // If facing south, destination is on your left
    case "west": return "left";    // If facing west, destination is on your left
    default: return "right";
  }
};

// Helper function to get cardinal direction between two nodes
// Stick to North, South, East, West only for simplicity
const getCardinalDirection = (fromNode, toNode) => {
  const dx = toNode.x - fromNode.x;
  const dy = toNode.y - fromNode.y;
  
  // Simplify to just 4 cardinal directions
  if (Math.abs(dx) > Math.abs(dy)) {
    // Predominantly horizontal
    return dx > 0 ? "east" : "west";
  } else {
    // Predominantly vertical
    return dy > 0 ? "south" : "north";
  }
};

// Helper function to determine turn direction
const getTurn = (fromDir, toDir) => {
  const directions = ["north", "east", "south", "west"];
  const fromIndex = directions.indexOf(fromDir);
  const toIndex = directions.indexOf(toDir);
  
  if (fromIndex === -1 || toIndex === -1) {
    return "to " + toDir;
  }
  
  // Calculate turn (clockwise is positive)
  const turn = (toIndex - fromIndex + 4) % 4;
  
  switch (turn) {
    case 0: return "continue straight";
    case 1: return "right";
    case 2: return "around";
    case 3: return "left";
    default: return "to " + toDir;
  }
};

// Get a list of all rooms for dropdown selection
export const getAllRooms = () => {
  return navigationGraph.nodes
    .filter(node => node.type === 'room')
    .map(room => ({ name: room.name, x: room.x, y: room.y }))
    .sort((a, b) => a.name.localeCompare(b.name));
};

// Get connections for a specific node (for debugging)
export const getNodeConnections = (nodeName) => {
  if (!nodeName) return [];
  
  const { nodeMap } = navigationGraph;
  const node = nodeMap[nodeName];
  
  if (!node) {
    console.warn(`Node ${nodeName} not found in the navigation graph`);
    return [];
  }
  
  return node.connections;
};

// Function to get the entire navigation graph (for debugging)
export const getNavigationGraph = () => {
  return navigationGraph;
};

// Function to find isolated or disconnected nodes in the graph
export const findIsolatedNodes = () => {
  const { nodes, nodeMap } = navigationGraph;
  const isolatedNodes = [];
  const lowConnectivityNodes = [];
  
  // Check for nodes with no connections or just one connection
  nodes.forEach(node => {
    if (node.connections.length === 0) {
      isolatedNodes.push({
        name: node.name,
        type: node.type,
        connections: 0
      });
    } else if (node.connections.length === 1) {
      lowConnectivityNodes.push({
        name: node.name,
        type: node.type,
        connections: 1,
        connectedTo: node.connections[0]
      });
    }
  });
  
  // Check for potentially disconnected subgraphs
  const visited = new Set();
  const subgraphs = [];
  
  // Simple BFS to find connected components
  const bfs = (startNode) => {
    const subgraph = new Set();
    const queue = [startNode];
    
    while (queue.length > 0) {
      const nodeName = queue.shift();
      
      if (subgraph.has(nodeName) || visited.has(nodeName)) continue;
      
      subgraph.add(nodeName);
      visited.add(nodeName);
      
      const node = nodeMap[nodeName];
      if (node && node.connections) {
        node.connections.forEach(neighbor => {
          if (!subgraph.has(neighbor)) {
            queue.push(neighbor);
          }
        });
      }
    }
    
    return Array.from(subgraph);
  };
  
  // Find all connected components
  nodes.forEach(node => {
    if (!visited.has(node.name)) {
      const subgraph = bfs(node.name);
      if (subgraph.length > 0) {
        subgraphs.push(subgraph);
      }
    }
  });
  
  return {
    isolatedNodes,
    lowConnectivityNodes,
    subgraphs,
    totalSubgraphs: subgraphs.length
  };
};