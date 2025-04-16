// Import or define your nodes
const nodes = [
  { name: "3R018", x: 2116, y: 725, type: "room" },
  { name: "3R017", x: 1989, y: 466, type: "room" },
  { name: "3R019", x: 1989, y: 723, type: "room" },
  { name: "WC1", x: 2116, y: 605, type: "room" },
  { name: "3R0T1", x: 2116, y: 509, type: "room" },
  { name: "3R0T2", x: 2116, y: 357, type: "room" },
  { name: "Emergency-Exit", x: 2075, y: 269, type: "room" },
  { name: "3R016", x: 1926, y: 269, type: "room" },
  { name: "3R014", x: 1656, y: 269, type: "room" },
  { name: "3R012", x: 1266, y: 269, type: "room" },
  { name: "3R010", x: 817, y: 269, type: "room" },
  { name: "3R008", x: 526, y: 269, type: "room" },
  { name: "3R013", x: 1142, y: 326, type: "room" },
  { name: "3R006", x: 374, y: 414, type: "room" },
  { name: "3R009", x: 558, y: 587, type: "room" },
  { name: "3R005", x: 558, y: 670, type: "room" },
  { name: "3R011", x: 793, y: 587, type: "room" },
  { name: "3R004", x: 793, y: 670, type: "room" },
  { name: "3R003", x: 1023, y: 670, type: "room" },
  { name: "3R002", x: 1254, y: 670, type: "room" },
  { name: "3R015", x: 1585, y: 850, type: "room" },
  { name: "3R020", x: 2116, y: 916, type: "room" },
  { name: "3R021", x: 1706, y: 1147, type: "room" },
  { name: "3R022", x: 2116, y: 1156, type: "room" },
  { name: "3R024", x: 2116, y: 1441, type: "room" },
  { name: "3R025", x: 2116, y: 1711, type: "room" },
  { name: "3R023", x: 1848, y: 1776, type: "room" },
  { name: "3R026", x: 1588, y: 1845, type: "room" },
  { name: "3R027", x: 1588, y: 1777, type: "room" },
  { name: "3R028", x: 1588, y: 1270, type: "room" },
  { name: "3R030", x: 670, y: 2097, type: "room" },
  { name: "3R035", x: 1023, y: 2038, type: "room" },
  { name: "Emergency-Exit-2", x: 1368, y: 2142, type: "room" },
  { name: "3R031", x: 1021, y: 1565, type: "room" },
  { name: "3R032", x: 904, y: 1503, type: "room" },
  { name: "3R033", x: 786, y: 1565, type: "room" },
  { name: "3R034", x: 549, y: 1565, type: "room" },
  
  // Main junctions - keep existing
  { name: "Junction1", x: 1420, y: 960, type: "Junction" },
  { name: "Junction2", x: 1420, y: 631, type: "Junction" },
  { name: "Junction3", x: 1420, y: 309, type: "Junction" },
  { name: "Junction4", x: 410, y: 310, type: "Junction" },
  { name: "Junction5", x: 410, y: 631, type: "Junction" },
  { name: "Junction6", x: 2048, y: 312, type: "Junction" },
  { name: "Junction7", x: 2048, y: 931, type: "Junction" },
  { name: "Junction8", x: 2048, y: 1809, type: "Junction" },
  { name: "Junction9", x: 1308, y: 1531, type: "Junction" },
  { name: "Junction10", x: 1308, y: 1812, type: "Junction" },
  { name: "Junction11", x: 1308, y: 2087, type: "Junction" },
  { name: "Junction12", x: 1588, y: 1147, type: "Junction" },
  { name: "Junction13", x: 1588, y: 1531, type: "Junction" },
  { name: "Junction14", x: 1848, y: 1147, type: "Junction" },
  { name: "Junction15", x: 1848, y: 1531, type: "Junction" },
  { name: "Junction16", x: 675, y: 1565, type: "Junction" },
  { name: "Junction17", x: 675, y: 2087, type: "Junction" },
  { name: "Junction18", x: 1023, y: 1812, type: "Junction" },
  
  // Add more junctions for problematic areas (especially upper left quadrant)
  { name: "Junction19", x: 526, y: 350, type: "Junction" }, // Junction for 3R008 hallway
  { name: "Junction20", x: 675, y: 350, type: "Junction" }, // Junction between 3R008 and 3R010
  { name: "Junction21", x: 558, y: 450, type: "Junction" }, // Junction connecting 3R009 to upper hallway
  { name: "Junction22", x: 675, y: 450, type: "Junction" }, // Junction for central upper left area
  { name: "Junction23", x: 793, y: 450, type: "Junction" }, // Junction for central upper area
  { name: "Junction24", x: 910, y: 450, type: "Junction" }, // Junction for right side of upper area
  { name: "Junction25", x: 910, y: 350, type: "Junction" }, // Junction near 3R013
  { name: "Junction26", x: 1142, y: 450, type: "Junction" }, // Junction connecting to 3R013
  
  // Existing hallway nodes
  { name: "Hallway1", x: 1138, y: 670, type: "Hallway" },
  { name: "Hallway2", x: 910, y: 670, type: "Hallway" },
  { name: "Hallway3", x: 676, y: 670, type: "Hallway" },
  { name: "Hallway4", x: 676, y: 587, type: "Hallway" },
  { name: "Hallway5", x: 1138, y: 309, type: "Hallway" },
  { name: "Hallway6", x: 675, y: 309, type: "Hallway" },
  { name: "Hallway7", x: 1420, y: 450, type: "Hallway" },
  { name: "Hallway8", x: 1420, y: 800, type: "Hallway" },
  { name: "Hallway9", x: 1308, y: 1650, type: "Hallway" },
  { name: "Hallway10", x: 1308, y: 1950, type: "Hallway" },
  { name: "Hallway11", x: 1023, y: 1800, type: "Hallway" },
  { name: "Hallway12", x: 786, y: 1450, type: "Hallway" },
  { name: "Hallway13", x: 2048, y: 600, type: "Hallway" },
  { name: "Hallway14", x: 2048, y: 1300, type: "Hallway" },
  { name: "Hallway15", x: 2048, y: 1600, type: "Hallway" },
  { name: "Hallway16", x: 1588, y: 1400, type: "Hallway" },
  { name: "Hallway17", x: 1588, y: 1650, type: "Hallway" },
  { name: "Hallway18", x: 1706, y: 1270, type: "Hallway" },
  { name: "Hallway19", x: 1706, y: 1400, type: "Hallway" },
  { name: "Hallway20", x: 1706, y: 1531, type: "Hallway" },
  { name: "Hallway21", x: 1706, y: 1650, type: "Hallway" },
  { name: "Hallway22", x: 1848, y: 1270, type: "Hallway" },
  { name: "Hallway23", x: 1848, y: 1400, type: "Hallway" },
  { name: "Hallway24", x: 1848, y: 1650, type: "Hallway" },
  { name: "Hallway25", x: 2048, y: 1147, type: "Hallway" },
  { name: "Hallway26", x: 1706, y: 1777, type: "Hallway" },
  
  // Add more hallway nodes for problematic areas
  { name: "Hallway27", x: 526, y: 450, type: "Hallway" }, // Hallway connecting 3R008 and 3R009
  { name: "Hallway28", x: 526, y: 520, type: "Hallway" }, // Hallway vertical between 3R009 and upper hallway
  { name: "Hallway29", x: 675, y: 520, type: "Hallway" }, // Hallway horizontal for middle section
  { name: "Hallway30", x: 793, y: 520, type: "Hallway" }, // Hallway for 3R011 connections
  { name: "Hallway31", x: 910, y: 520, type: "Hallway" }, // Hallway for right side middle section
  { name: "Hallway32", x: 910, y: 580, type: "Hallway" }, // Hallway near 3R003
  { name: "Hallway33", x: 1142, y: 580, type: "Hallway" }, // Hallway near 3R002
  { name: "Hallway34", x: 1266, y: 450, type: "Hallway" }, // Hallway connecting upper middle
  { name: "Hallway35", x: 1266, y: 580, type: "Hallway" }, // Hallway near 3R002
  { name: "Hallway36", x: 374, y: 350, type: "Hallway" }, // Hallway near 3R006
  { name: "Hallway37", x: 374, y: 520, type: "Hallway" }, // Hallway vertical left side
  { name: "Hallway38", x: 1656, y: 450, type: "Hallway" }, // Upper right hallway near 3R014
  { name: "Hallway39", x: 1926, y: 450, type: "Hallway" }, // Upper right hallway near 3R016
  { name: "Hallway40", x: 817, y: 350, type: "Hallway" }, // Hallway near 3R010
];

// Create adjacency list for graph
const createNavigationGraph = () => {
  // Clone the nodes array and add connections property
  const graph = nodes.map(node => ({
    ...node,
    connections: []
  }));

  // Create node lookup by name for faster access
  const nodeMap = {};
  graph.forEach(node => {
    nodeMap[node.name] = node;
  });

  // Helper function to calculate distance between two nodes
  const calculateDistance = (node1, node2) => {
    return Math.sqrt(
      Math.pow(node1.x - node2.x, 2) + Math.pow(node1.y - node2.y, 2)
    );
  };

  // Helper function to find adjacent nodes of the same type (room-to-room, hallway-to-hallway)
  const findSameTypeAdjacentNodes = (node, threshold = 80) => {
    const adjacentNodes = [];
    
    graph.forEach(otherNode => {
      if (otherNode.name !== node.name && otherNode.type === node.type) {
        const distance = calculateDistance(node, otherNode);
        if (distance < threshold) {
          adjacentNodes.push(otherNode);
        }
      }
    });
    
    return adjacentNodes;
  };

  // Helper function to find nearest junction or hallway for a room
  const findNearestNavigationNode = (room, maxDistance = 150) => {
    const navigationNodes = [];
    
    graph.forEach(node => {
      if ((node.type === "Junction" || node.type === "Hallway") && node.name !== room.name) {
        const distance = calculateDistance(room, node);
        if (distance < maxDistance) {
          navigationNodes.push({ node, distance });
        }
      }
    });
    
    // Sort by distance and return the closest nodes (up to 3)
    return navigationNodes
      .sort((a, b) => a.distance - b.distance)
      .slice(0, 3)
      .map(item => item.node);
  };

  // Connect each node to appropriate navigation nodes
  graph.forEach(node => {
    // Connect rooms to navigation nodes
    if (node.type === "room") {
      // Find multiple nearby navigation nodes (not just the closest)
      const nearestNodes = findNearestNavigationNode(node);
      
      nearestNodes.forEach(navNode => {
        if (!node.connections.includes(navNode.name)) {
          node.connections.push(navNode.name);
          navNode.connections.push(node.name);
        }
      });
      
      // Also connect to adjacent rooms
      const adjacentRooms = findSameTypeAdjacentNodes(node);
      adjacentRooms.forEach(adjacentRoom => {
        if (!node.connections.includes(adjacentRoom.name)) {
          node.connections.push(adjacentRoom.name);
          adjacentRoom.connections.push(node.name);
        }
      });
    } 
    // Connect hallways and junctions to nearby nodes of the same type
    else if (node.type === "Hallway" || node.type === "Junction") {
      const adjacentNodes = findSameTypeAdjacentNodes(node, 150);
      adjacentNodes.forEach(adjacentNode => {
        if (!node.connections.includes(adjacentNode.name)) {
          node.connections.push(adjacentNode.name);
          adjacentNode.connections.push(node.name);
        }
      });
    }
  });

  // Connect nodes explicitly to ensure proper hallway navigation
  const connectNodes = (node1Name, node2Name) => {
    const n1 = nodeMap[node1Name];
    const n2 = nodeMap[node2Name];
    if (n1 && n2) {
      if (!n1.connections.includes(n2.name)) {
        n1.connections.push(n2.name);
      }
      if (!n2.connections.includes(n1.name)) {
        n2.connections.push(n1.name);
      }
    }
  };

  // Connect all main hallways and junctions structurally
  // Main corridor connections
  connectNodes("Junction1", "Junction2");
  connectNodes("Junction2", "Junction3");
  connectNodes("Junction3", "Junction4");
  connectNodes("Junction4", "Junction5");
  connectNodes("Junction5", "Junction1");
  
  connectNodes("Junction3", "Junction6");
  connectNodes("Junction6", "Junction7");
  connectNodes("Junction7", "Junction8");
  
  connectNodes("Junction1", "Junction9");
  connectNodes("Junction9", "Junction10");
  connectNodes("Junction10", "Junction11");
  
  // Connect new junctions
  connectNodes("Junction9", "Junction13");
  connectNodes("Junction10", "Junction18");
  connectNodes("Junction13", "Junction15");
  connectNodes("Junction12", "Junction14");
  connectNodes("Junction14", "Junction15");
  connectNodes("Junction16", "Junction17");
  
  // Upper left quadrant - specific connections for 3R008, 3R009, etc.
  connectNodes("Junction4", "Junction19");
  connectNodes("Junction19", "Junction20");
  connectNodes("Junction20", "Junction23");
  connectNodes("Junction23", "Junction24");
  connectNodes("Junction24", "Junction25");
  connectNodes("Junction25", "Junction26");
  connectNodes("Junction26", "Junction3");
  
  connectNodes("Junction19", "Junction21");
  connectNodes("Junction21", "Junction22");
  connectNodes("Junction22", "Junction23");
  
  // Connect rooms to proper junctions
  connectNodes("3R008", "Junction19");
  connectNodes("3R010", "Junction20");
  connectNodes("3R009", "Junction21");
  connectNodes("3R011", "Junction23");
  connectNodes("3R013", "Junction26");
  connectNodes("3R006", "Junction4");
  
  // Connect hallway nodes for proper paths
  connectNodes("Hallway1", "Hallway2");
  connectNodes("Hallway2", "Hallway3");
  connectNodes("Hallway7", "Hallway8");
  connectNodes("Hallway9", "Hallway10");
  connectNodes("Hallway13", "Hallway14");
  connectNodes("Hallway14", "Hallway15");
  
  // Connect 3R002, 3R003, 3R004, and 3R005
  connectNodes("3R002", "Hallway33");
  connectNodes("Hallway33", "Hallway35");
  connectNodes("Hallway35", "Hallway1");
  connectNodes("Hallway1", "3R003");
  connectNodes("3R003", "Hallway32");
  connectNodes("Hallway32", "Hallway31");
  connectNodes("Hallway31", "Hallway30");
  connectNodes("Hallway30", "3R004");
  connectNodes("3R004", "Hallway3");
  connectNodes("Hallway3", "3R005");
  
  // Connect the new hallway nodes for complex paths
  connectNodes("3R027", "Hallway17");
  connectNodes("Hallway17", "Hallway16");
  connectNodes("Hallway16", "Junction13");
  connectNodes("Junction13", "Hallway20");
  connectNodes("Hallway20", "Hallway19");
  connectNodes("Hallway19", "Hallway18");
  connectNodes("Hallway18", "Junction12");
  connectNodes("Junction12", "3R021");
  
  // Add new connections for improved navigation
  connectNodes("3R009", "Hallway28");
  connectNodes("Hallway28", "Hallway27");
  connectNodes("Hallway27", "3R008");
  connectNodes("3R009", "Hallway29");
  connectNodes("Hallway29", "Hallway30");
  connectNodes("3R011", "Hallway30");
  
  // Connect upper hallways
  connectNodes("Junction19", "Hallway27");
  connectNodes("Hallway27", "Hallway28");
  connectNodes("Hallway28", "Hallway29");
  connectNodes("Hallway29", "Hallway30");
  connectNodes("Hallway30", "Hallway31");
  connectNodes("Hallway31", "Hallway32");
  connectNodes("Hallway32", "Hallway33");
  connectNodes("Hallway33", "Hallway35");
  connectNodes("Hallway35", "Hallway34");
  connectNodes("Hallway34", "Junction2");
  
  // Additional connections for 3R006
  connectNodes("3R006", "Hallway36");
  connectNodes("Hallway36", "Hallway37");
  connectNodes("Hallway37", "3R005");
  
  // Connect upper right section
  connectNodes("Junction3", "Hallway38");
  connectNodes("Hallway38", "3R014");
  connectNodes("Hallway38", "Hallway39");
  connectNodes("Hallway39", "3R016");
  
  // Left side connections
  connectNodes("Junction4", "Hallway36");
  connectNodes("Hallway36", "3R006");
  
  // Center upper connections
  connectNodes("Junction20", "Hallway40");
  connectNodes("Hallway40", "3R010");
  
  // Let's also verify and fix any missing connections
  connectNodes("3R028", "Hallway18");
  connectNodes("3R023", "Hallway24");
  connectNodes("Hallway24", "Hallway23");
  connectNodes("Hallway23", "Hallway22");
  connectNodes("Hallway22", "Junction14");
  connectNodes("3R026", "Hallway26");
  connectNodes("Hallway26", "3R027");
  
  // Horizontal connections for grid-like movement
  connectNodes("Hallway16", "Hallway19");
  connectNodes("Hallway19", "Hallway23");
  connectNodes("Hallway17", "Hallway21");
  connectNodes("Hallway21", "Hallway24");
  connectNodes("Junction15", "Hallway23");
  connectNodes("Hallway25", "Junction14");
  connectNodes("3R022", "Hallway25");
  connectNodes("Junction7", "Hallway25");
  connectNodes("Junction12", "Hallway16");

  return { nodes: graph, nodeMap };
};

// Create and export the navigation graph
const navigationGraph = createNavigationGraph();

export default navigationGraph; 