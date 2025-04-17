// This file has all the room data and connections for the navigation system

// Quick check to see if a node is a room (vs a junction/corridor)
export const isRoom = (node) => {
  return 'width' in node && 'height' in node;
};

// Config settings for nav system
export const navigationConfig = {
  // Turn this on to show all the node labels (can get messy)
  showNodeLabels: false
};

export const waypoints = [
  // Blue waypoints (junctions)
  { id: "J1", type: "junction", x: 205, y: 160, connections: ["J2", "J4", "C1", "3R006", "3R008"] },
  { id: "J2", type: "junction", x: 710, y: 160, connections: ["J1", "J3", "J5", "C13", "C3", "3R012", "3R014", "C43"] },
  { id: "J3", type: "junction", x: 1025, y: 220, connections: ["J2", "J6", "C9", "C42", "3R016", "3R012T", "3R017"] },
  { id: "J4", type: "junction", x: 205, y: 330, connections: ["J1", "J5", "C11", "C18", "3R005", "3R009"] },
  { id: "J5", type: "junction", x: 710, y: 330, connections: ["J2", "J4", "J7", "C13", "C19", "3R015"] },
  { id: "J6", type: "junction", x: 1025, y: 485, connections: ["J3", "J7", "J10", "C22", "CR2", "3R018", "3R019", "3R020"] },
  { id: "J7", type: "junction", x: 710, y: 485, connections: ["J5", "J6", "J8", "C24", "3R021"] },
  { id: "J8", type: "junction", x: 710, y: 800, connections: ["J7", "J9", "J11", "CV4"] },
  { id: "J9", type: "junction", x: 775, y: 600, connections: ["J7", "J8", "J10", "J12", "C34", "3R027", "3R028"] },
  { id: "J10", type: "junction", x: 1025, y: 775, connections: ["J6", "J6", "J12", "C42", "3R024", "3R025"] },
  { id: "J11", type: "junction", x: 650, y: 800, connections: ["J8", "J13", "C31", "C28", "C32", "C33", "C38", "CH7"] },
  { id: "J12", type: "junction", x: 1025, y: 940, connections: [ "J10", "J13", "C40", "C41"] },
  { id: "J13", type: "junction", x: 642, y: 945, connections: ["J11", "J12", "C41", "CH3", "CH7", "3R026", "C40"] },

  // Corridors - needed these for smoother paths and direct room access
  { id: "C1", type: "corridor", x: 267, y: 160, connections: ["J1", "J2", "3R008", "3R010", "C43"] },
  { id: "C3", type: "corridor", x: 630, y: 160, connections: ["J2", "3R012", "3R014"] },
  { id: "C9", type: "corridor", x: 1000, y: 200, connections: ["J3", "3R017", "3R011T"] },
  { id: "C11", type: "corridor", x: 368, y: 290, connections: ["J4", "J5", "3R011", "3R004"] },
  { id: "C13", type: "corridor", x: 580, y: 160, connections: ["J2", "3R013", "3R015"] },
  { id: "C18", type: "corridor", x: 368, y: 325, connections: ["J4", "J5", "3R004", "3R003"] },
  { id: "C19", type: "corridor", x: 477, y: 325, connections: ["J5", "3R003", "3R002"] },
  { id: "C22", type: "corridor", x: 1025, y: 420, connections: ["J6", "3R018", "3R019", "C24"] },
  { id: "C24", type: "corridor", x: 863, y: 490, connections: ["J7", "J6", "3R019", "3R021"] },
  { id: "C28", type: "corridor", x: 460, y: 800, connections: ["J8", "J11", "3R032"] },
  { id: "C31", type: "corridor", x: 300, y: 800, connections: ["J11", "3R034"] },
  { id: "C32", type: "corridor", x: 400, y: 800, connections: ["J11", "3R033"] },
  { id: "C33", type: "corridor", x: 510, y: 800, connections: ["J11", "3R031"] },
  { id: "C34", type: "corridor", x: 715, y: 940, connections: ["J8", "J9", "3R027"] },
  { id: "C34.5", type: "corridor", x: 800, y: 940, connections: ["C34", "3R027"] },
  { id: "C38", type: "corridor", x: 500, y: 1080, connections: ["3R035", "CH7", "3R030"] },
  { id: "C40", type: "corridor", x: 900, y: 940, connections: ["J12", "J13", "3R023"] },
  { id: "C41", type: "corridor", x: 742, y: 940, connections: ["J13", "3R026"] },
  { id: "C42", type: "corridor", x: 1025, y: 600, connections: ["J6", "J10", "3R022"] },
  { id: "C43", type: "corridor", x: 380, y: 160, connections: [ "C1", "J2", "3R022"] },
  
  { id: "CV4", type: "corridor", x: 800, y: 625, connections: ["J8", "J9", "3R028"] },
  { id: "CR2", type: "corridor", x: 1020, y: 450, connections: ["J6", "3R020"] },
  { id: "CH1", type: "corridor", x: 300, y: 780, connections: ["J11", "3R034"] },
  { id: "CH3", type: "corridor", x: 580, y: 945, connections: ["J11", "J13"] },
  { id: "CH7", type: "corridor", x: 642, y: 1080, connections: ["J13", "3R035"] }
];

export const rooms = [
  { id: "3R002", name: "Lecture Room", type: "Room", x: 584, y: 362, width: 65, height: 41, connections: ["C19", "J5"] },
  { id: "3R003", name: "Lecture Room", type: "Room", x: 477, y: 362, width: 65, height: 41, connections: ["C18", "C19", "J5"] },
  { id: "3R004", name: "Lecture Room", type: "Room", x: 365, y: 362, width: 65, height: 41, connections: ["C11", "C18"] },
  { id: "3R005", name: "Enterprise and Innovation Centre", type: "Centre", x: 254, y: 398, width: 83, height: 41, connections: ["J4"] },
  { id: "3R006", name: "Lecture Room", type: "Room", x: 135, y: 244, width: 83, height: 41, connections: ["J1"] },
  { id: "3R008", name: "RMC", type: "Centre", x: 226, y: 94, width: 83, height: 41, connections: ["C1", "J1"] },
  { id: "3R009", name: "Lecture Room", type: "Room", x: 230, y: 216, width: 122, height: 47, connections: ["J4"] },
  { id: "3R010", name: "Mechanical Workshop", type: "Lab", x: 380, y: 74, width: 122, height: 47, connections: ["C43"] },
  { id: "3R011", name: "Lecture Room", type: "Room", x: 368, y: 216, width: 122, height: 47, connections: ["C11"] },
  { id: "3R011T", name: "Women's Bathroom", type: "Bathroom", x: 1065, y: 250, width: 65, height: 41, connections: ["C9", "J3"] },
  { id: "3R012", name: "Green Engineering Lab", type: "Lab", x: 589, y: 74, width: 122, height: 47, connections: ["C3", "J2"] },
  { id: "3R012T", name: "Men's Bathroom", type: "Bathroom", x: 1065, y: 170, width: 65, height: 41, connections: ["J3"] },
  { id: "3R013", name: "Materials and structure Lab", type: "Lab", x: 533, y: 223, width: 122, height: 47, connections: ["C13"] },
  { id: "3R014", name: "Aerospace Lab", type: "Lab", x: 771, y: 74, width: 122, height: 47, connections: ["C3", "J2"] },
  { id: "3R015", name: "Design Studio", type: "Studio", x: 741, y: 287, width: 122, height: 47, connections: ["C13", "J5"] },
  { id: "3R016", name: "Thermodynamics & Fluid Mechanics Lab", type: "Lab", x: 915, y: 74, width: 122, height: 47, connections: ["J3"] },
  { id: "3R017", name: "Lecture Room", type: "Room", x: 863, y: 228, width: 122, height: 47, connections: ["C9", "J3"] },
  { id: "3R018", name: "Lecture Room", type: "Room", x: 1085, y: 350, width: 65, height: 41, connections: ["C22", "J6"] },
  { id: "3R019", name: "Lecture Room", type: "Room", x: 863, y: 341, width: 122, height: 47, connections: ["C22"] },
  { id: "3R020", name: "Lecture Room", type: "Room", x: 1085, y: 450, width: 65, height: 41, connections: ["CR2", "J6"] },
  { id: "3R021", name: "Lecture Room", type: "Room", x: 863, y: 543, width: 122, height: 47, connections: ["C24", "J7"] },
  { id: "3R022", name: "Lecture Room", type: "Room", x: 1085, y: 600, width: 65, height: 41, connections: ["C42"] },
  { id: "3R023", name: "Computer Science Lab 1", type: "Lab", x: 863, y: 850, width: 122, height: 47, connections: ["C40"] },
  { id: "3R024", name: "Lecture Room", type: "Room", x: 1085, y: 750, width: 65, height: 41, connections: ["J10"] },
  { id: "3R025", name: "Lecture Room", type: "Room", x: 1085, y: 850, width: 65, height: 41, connections: ["J10", "J12"] },
  { id: "3R026", name: "Lecture Hall", type: "Hall", x: 742, y: 972, width: 122, height: 47, connections: ["C41"] },
  { id: "3R027", name: "Computer Science Lab 2", type: "Lab", x: 735, y: 820, width: 122, height: 47, connections: ["C34.5"] },
  { id: "3R028", name: "Computer Science Lab 3", type: "Lab", x: 742, y: 650, width: 122, height: 47, connections: ["CV4"] },
  { id: "3R030", name: "Lecture Room", type: "Room", x: 477, y: 950, width: 65, height: 41, connections: ["C38"] },
  { id: "3R031", name: "Lecture Room", type: "Room", x: 477, y: 820, width: 65, height: 41, connections: ["C33"] },
  { id: "3R032", name: "Engineering Foundation Lab 1", type: "Lab", x: 400, y: 700, width: 122, height: 47, connections: ["C28"] },
  { id: "3R033", name: "Engineering Foundation Lab 2", type: "Lab", x: 365, y: 820, width: 122, height: 47, connections: ["C32"] },
  { id: "3R034", name: "Engineering Foundation Lab 3", type: "Lab", x: 254, y: 820, width: 122, height: 47, connections: ["C31", "CH1"] },
  { id: "3R035", name: "Female Surau", type: "Room", x: 254, y: 1080, width: 83, height: 41, connections: ["CH7"] }
];

// Combined array of all the nodes - easier to loop through
export const allNodes = [...rooms, ...waypoints];