import { allNodes, isRoom } from '../data/navigationData';

// Some scaling stuff for calculating real world distances
const UNITS_TO_METRES_FACTOR = 0.06; // Approx. 1 unit = 0.06 metres

// Massive penalty for cutting through rooms - we never want this
const ROOM_CROSSING_PENALTY = 10000; 

// Calculate distance between any two navigation nodes
export const calculateDistance = (node1, node2) => {
  let x1, y1, x2, y2;
  
  // Get center point for room, or exact point for waypoint
  if (isRoom(node1)) {
    x1 = node1.x + node1.width / 2;
    y1 = node1.y + node1.height / 2;
  } else {
    x1 = node1.x;
    y1 = node1.y;
  }
  
  if (isRoom(node2)) {
    x2 = node2.x + node2.width / 2;
    y2 = node2.y + node2.height / 2;
  } else {
    x2 = node2.x;
    y2 = node2.y;
  }
  
  return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
};

// Heuristic for A* - just using Euclidean distance
const heuristic = (node, goalNode) => {
  return calculateDistance(node, goalNode);
};

// Check if a node is a valid corridor waypoint
const isCorridorWaypoint = (node) => {
  return !isRoom(node) && (node.type === "corridor" || node.type === "junction");
};

// A* pathfinding algorithm - had to add penalties for going through rooms
export const findPath = (startNodeId, endNodeId) => {
  if (startNodeId === endNodeId) {
    return [startNodeId]; // Already at destination
  }

  // Create a map of all nodes by ID
  const nodeMap = new Map();
  allNodes.forEach(node => nodeMap.set(node.id, node));

  const startNode = nodeMap.get(startNodeId);
  const endNode = nodeMap.get(endNodeId);

  if (!startNode || !endNode) {
    console.log("Invalid node IDs:", startNodeId, endNodeId);
    return []; // Invalid node IDs
  }

  // Initialize open and closed sets
  const openSet = [];
  const closedSet = new Set();
  const pathNodeMap = new Map();

  // Create start node
  const initialPathNode = {
    id: startNodeId,
    g: 0,
    h: calculateDistance(startNode, endNode),
    f: 0,
    parent: null
  };
  initialPathNode.f = initialPathNode.g + initialPathNode.h;
  openSet.push(initialPathNode);
  pathNodeMap.set(startNodeId, initialPathNode);

  // A* algorithm
  while (openSet.length > 0) {
    // Find node with lowest f cost
    openSet.sort((a, b) => a.f - b.f);
    const current = openSet.shift();

    // Reached the goal
    if (current.id === endNodeId) {
      // Reconstruct path
      const path = [];
      let currentNode = current;
      while (currentNode) {
        path.unshift(currentNode.id);
        currentNode = currentNode.parent;
      }
      return path;
    }

    // Add current to closed set
    closedSet.add(current.id);

    // Get current navigation node
    const currentNavNode = nodeMap.get(current.id);

    // Process neighbors
    for (const neighborId of currentNavNode.connections) {
      // Skip if already processed
      if (closedSet.has(neighborId)) continue;

      const neighborNavNode = nodeMap.get(neighborId);
      if (!neighborNavNode) {
        console.log("Warning: Missing neighbor node:", neighborId);
        continue;
      }

      // Calculate base movement cost
      let gScore = current.g + calculateDistance(currentNavNode, neighborNavNode);
      
      // Apply penalty for going through rooms that aren't the start or end
      if (isRoom(neighborNavNode) && 
          neighborId !== startNodeId && 
          neighborId !== endNodeId) {
        gScore += ROOM_CROSSING_PENALTY;
      }
      
      // This stops paths from jumping from room to room
      if (isRoom(currentNavNode) && isRoom(neighborNavNode) && 
          currentNavNode.id !== startNodeId && neighborNavNode.id !== endNodeId) {
        gScore += ROOM_CROSSING_PENALTY; // Heavily penalize room-to-room transitions
      }

      // Check if neighbor is in open set
      let neighbor = pathNodeMap.get(neighborId);
      const inOpenSet = neighbor !== undefined;

      if (!inOpenSet || gScore < neighbor.g) {
        // Create or update neighbor
        if (!neighbor) {
          neighbor = {
            id: neighborId,
            g: gScore,
            h: calculateDistance(neighborNavNode, endNode),
            f: 0,
            parent: current
          };
          neighbor.f = neighbor.g + neighbor.h;
          pathNodeMap.set(neighborId, neighbor);
        } else {
          neighbor.g = gScore;
          neighbor.parent = current;
          neighbor.f = neighbor.g + neighbor.h;
        }

        if (!inOpenSet) {
          openSet.push(neighbor);
        }
      }
    }
  }

  // No path found
  return [];
};

// Helper function to get node by ID
export const getNodeById = (id) => {
  return allNodes.find(node => node.id === id);
};

// Helper to get node position (center for rooms)
export const getNodePosition = (node) => {
  if (isRoom(node)) {
    return {
      x: node.x + node.width / 2,
      y: node.y + node.height / 2
    };
  }
  return { x: node.x, y: node.y };
};

// Checks if all rooms can reach each other - this was for debugging
export const verifyConnections = () => {
  const connectedRooms = [];
  const disconnectedRooms = [];
  
  // Check each room
  allNodes.filter(isRoom).forEach(room => {
    const hasPath = allNodes.filter(isRoom)
      .every(otherRoom => {
        if (room.id === otherRoom.id) return true;
        const path = findPath(room.id, otherRoom.id);
        return path.length > 0;
      });
    
    if (hasPath) {
      connectedRooms.push(room.id);
    } else {
      disconnectedRooms.push(room.id);
    }
  });
  
  return { connectedRooms, disconnectedRooms };
};

// Commented out a bunch of code below that I was working on for better directions
// Will finish this later once the core nav works

// // Helper function to reconstruct the path from the cameFrom map
// const reconstructPath = (cameFrom, currentNodeName, nodeMap) => {
//   const path = [nodeMap[currentNodeName]];
  
//   while (cameFrom[currentNodeName]) {
//     currentNodeName = cameFrom[currentNodeName];
//     path.unshift(nodeMap[currentNodeName]);
//   }
  
//   return path;
// };

// // Calculate the angle between two nodes in degrees (0-360)
// const getAngle = (fromNode, toNode) => {
//   const dx = toNode.x - fromNode.x;
//   const dy = toNode.y - fromNode.y;
  
//   // Calculate angle in radians, then convert to degrees
//   let angle = Math.atan2(dy, dx) * (180 / Math.PI);
  
//   // Normalize to 0-360 range
//   if (angle < 0) {
//     angle += 360;
//   }
  
//   return angle;
// };

// // Determine the turn description based on angle change
// const getTurnDescription = (fromAngle, toAngle) => {
//   // Calculate the difference between the angles
//   let angleDiff = toAngle - fromAngle;
  
//   // Normalize to -180 to 180 for easier turn detection
//   if (angleDiff > 180) angleDiff -= 360;
//   if (angleDiff < -180) angleDiff += 360;
  
//   // Determine turn type based on angle difference
//   if (Math.abs(angleDiff) < 20) {
//     return "Continue straight";
//   } else if (angleDiff >= 20 && angleDiff < 70) {
//     return "Turn slightly right";
//   } else if (angleDiff >= 70 && angleDiff < 110) {
//     return "Turn right";
//   } else if (angleDiff >= 110 && angleDiff <= 180) {
//     return "Turn sharp right";
//   } else if (angleDiff <= -20 && angleDiff > -70) {
//     return "Turn slightly left";
//   } else if (angleDiff <= -70 && angleDiff > -110) {
//     return "Turn left";
//   } else if (angleDiff <= -110 && angleDiff >= -180) {
//     return "Turn sharp left";
//   }
  
//   return "Continue";
// };

// // Generate directions
// export const generateDirections = (path) => {
//   if (!path || path.length < 2) {
//     return "No path found or path too short.";
//   }

//   const directions = [];
//   let totalDistance = 0;
  
//   // Start with origin point
//   directions.push(`Start at ${path[0].name}.`);
  
//   // Simplify the path by combining segments with the same direction
//   let simplifiedPath = [path[0]];
//   let lastDirection = null;
  
//   for (let i = 1; i < path.length; i++) {
//     const prevNode = path[i-1];
//     const currentNode = path[i];
    
//     // Calculate the cardinal direction
//     const currentDirection = getCardinalDirection(prevNode, currentNode);
    
//     // Add distance to total
//     const segmentDistance = calculateDistance(prevNode, currentNode);
//     totalDistance += segmentDistance;
    
//     // If this is a significant node (room or last node) or the direction changes,
//     // add it to our simplified path
//     if (i === path.length - 1 || 
//         currentNode.type === "room" || 
//         currentDirection !== lastDirection) {
//       simplifiedPath.push(currentNode);
//       lastDirection = currentDirection;
//     }
//   }
  
//   // Now generate directions from simplified path
//   for (let i = 1; i < simplifiedPath.length; i++) {
//     const prevNode = simplifiedPath[i-1];
//     const currentNode = simplifiedPath[i];
    
//     // Skip if this is a junction and not the destination
//     if (currentNode.type === "Junction" && i < simplifiedPath.length - 1) {
//       continue;
//     }
    
//     // Calculate distance for this segment
//     const segmentDistanceUnits = calculateDistance(prevNode, currentNode);
//     const segmentMeters = Math.round(segmentDistanceUnits * UNITS_TO_METRES_FACTOR);
    
//     // Only include meaningful segments (longer than 1 meter)
//     if (segmentMeters < 1 && i < simplifiedPath.length - 1) {
//       continue;
//     }
    
//     // If not the first segment, we need a turn instruction
//     if (i > 1) {
//       const prevPrevNode = simplifiedPath[i-2];
      
//       // Calculate the turn direction
//       const incomingDir = getCardinalDirection(prevPrevNode, prevNode);
//       const outgoingDir = getCardinalDirection(prevNode, currentNode);
//       const turnDirection = getTurn(incomingDir, outgoingDir);
      
//       if (turnDirection !== "continue straight") {
//         // For the final segment, use special formatting
//         if (i === simplifiedPath.length - 1) {
//           directions.push(`Turn ${turnDirection} and walk ${segmentMeters} meters.`);
//           directions.push(`${currentNode.name} should be on your ${getRelativePosition(outgoingDir)}.`);
//         } else {
//           directions.push(`Turn ${turnDirection} and walk ${segmentMeters} meters.`);
//         }
//       } else {
//         // If continuing straight, just mention the distance
//         directions.push(`Continue walking ${segmentMeters} meters.`);
//       }
//     } else {
//       // First segment - just walk
//       directions.push(`Walk ${segmentMeters} meters.`);
//     }
//   }
  
//   // Add total distance at the end
//   const totalMeters = Math.round(totalDistance * UNITS_TO_METRES_FACTOR);
//   directions.push(`Total distance: ${totalMeters} meters.`);
  
//   return directions.join("\n");
// };

// // Helper function to determine the relative position (left/right/ahead/behind)
// const getRelativePosition = (direction) => {
//   // When facing a direction, what's on your left/right
//   switch (direction) {
//     case "north": return "right";  // If facing north, destination is on your right
//     case "east": return "right";   // If facing east, destination is on your right
//     case "south": return "left";   // If facing south, destination is on your left
//     case "west": return "left";    // If facing west, destination is on your left
//     default: return "right";
//   }
// };

// // Helper function to get cardinal direction between two nodes
// // Stick to North, South, East, West only for simplicity
// const getCardinalDirection = (fromNode, toNode) => {
//   const dx = toNode.x - fromNode.x;
//   const dy = toNode.y - fromNode.y;
  
//   // Simplify to just 4 cardinal directions
//   if (Math.abs(dx) > Math.abs(dy)) {
//     // Predominantly horizontal
//     return dx > 0 ? "east" : "west";
//   } else {
//     // Predominantly vertical
//     return dy > 0 ? "south" : "north";
//   }
// };

// // Helper function to determine turn direction
// const getTurn = (fromDir, toDir) => {
//   const directions = ["north", "east", "south", "west"];
//   const fromIndex = directions.indexOf(fromDir);
//   const toIndex = directions.indexOf(toDir);
  
//   if (fromIndex === -1 || toIndex === -1) {
//     return "to " + toDir;
//   }
  
//   // Calculate turn (clockwise is positive)
//   const turn = (toIndex - fromIndex + 4) % 4;
  
//   switch (turn) {
//     case 0: return "continue straight";
//     case 1: return "right";
//     case 2: return "around";
//     case 3: return "left";
//     default: return "to " + toDir;
//   }
// };

// // Get a list of all rooms for dropdown selection
// export const getAllRooms = () => {
//   return navigationGraph.nodes
//     .filter(node => node.type === 'room')
//     .map(room => ({ name: room.name, x: room.x, y: room.y }))
//     .sort((a, b) => a.name.localeCompare(b.name));
// };

// // Get connections for a specific node (for debugging)
// export const getNodeConnections = (nodeName) => {
//   if (!nodeName) return [];
  
//   const { nodeMap } = navigationGraph;
//   const node = nodeMap[nodeName];
  
//   if (!node) {
//     console.warn(`Node ${nodeName} not found in the navigation graph`);
//     return [];
//   }
  
//   return node.connections;
// };

// // Function to get the entire navigation graph (for debugging)
// export const getNavigationGraph = () => {
//   return navigationGraph;
// };

// // Function to find isolated or disconnected nodes in the graph
// export const findIsolatedNodes = () => {
//   const { nodes, nodeMap } = navigationGraph;
//   const isolatedNodes = [];
//   const lowConnectivityNodes = [];
  
//   // Check for nodes with no connections or just one connection
//   nodes.forEach(node => {
//     if (node.connections.length === 0) {
//       isolatedNodes.push({
//         name: node.name,
//         type: node.type,
//         connections: 0
//       });
//     } else if (node.connections.length === 1) {
//       lowConnectivityNodes.push({
//         name: node.name,
//         type: node.type,
//         connections: 1,
//         connectedTo: node.connections[0]
//       });
//     }
//   });
  
//   // Check for potentially disconnected subgraphs
//   const visited = new Set();
//   const subgraphs = [];
  
//   // Simple BFS to find connected components
//   const bfs = (startNode) => {
//     const subgraph = new Set();
//     const queue = [startNode];
    
//     while (queue.length > 0) {
//       const nodeName = queue.shift();
      
//       if (subgraph.has(nodeName) || visited.has(nodeName)) continue;
      
//       subgraph.add(nodeName);
//       visited.add(nodeName);
      
//       const node = nodeMap[nodeName];
//       if (node && node.connections) {
//         node.connections.forEach(neighbor => {
//           if (!subgraph.has(neighbor)) {
//             queue.push(neighbor);
//           }
//         });
//       }
//     }
    
//     return Array.from(subgraph);
//   };
  
//   // Find all connected components
//   nodes.forEach(node => {
//     if (!visited.has(node.name)) {
//       const subgraph = bfs(node.name);
//       if (subgraph.length > 0) {
//         subgraphs.push(subgraph);
//       }
//     }
//   });
  
//   return {
//     isolatedNodes,
//     lowConnectivityNodes,
//     subgraphs,
//     totalSubgraphs: subgraphs.length
//   };
// };