import axios from 'axios';

// Create an Axios instance with a base URL
const api = axios.create({
  baseURL: 'http://localhost:8082',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;

// Utility function to construct API paths
export function getAPI_URL(path) {
  return `http://localhost:8082/${path}`;
}

// User authentication
export const loginUser = async (credentials) => {
  try {
    return {
      success: true,
      user: {
        id: 1,
        name: 'Test User',
        email: credentials.email,
        role: 'student',
      },
      token: 'mock-token-12345',
    };
  } catch (error) {
    console.error('Login error:', error);
    throw error;
  }
};

// Get campus locations
export const getCampusLocations = async () => {
  try {
    return [
      { id: 1, name: 'Main Building', type: 'academic', position: { x: 100, y: 150 } },
      { id: 2, name: 'Library', type: 'academic', position: { x: 250, y: 200 } },
      { id: 3, name: 'Computer Lab A', type: 'lab', position: { x: 180, y: 300 } },
      { id: 4, name: 'Student Center', type: 'dining', position: { x: 400, y: 280 } },
      { id: 5, name: 'Study Hall 1', type: 'study', position: { x: 320, y: 120 } },
    ];
  } catch (error) {
    console.error('Error fetching locations:', error);
    throw error;
  }
};

// Get available rooms
export const getAvailableRooms = async (date) => {
  try {
    return [
      { id: 1, name: 'Study Room 101', capacity: 4, building: 'Library', availableTimes: ['9:00', '10:00', '11:00'] },
      { id: 2, name: 'Conference Room A', capacity: 12, building: 'Main Building', availableTimes: ['13:00', '14:00', '15:00'] },
      { id: 3, name: 'Lab B', capacity: 24, building: 'Science Building', availableTimes: ['16:00', '17:00'] },
    ];
  } catch (error) {
    console.error('Error fetching available rooms:', error);
    throw error;
  }
};







// Add event (Re-added to fix the export error)
export const addEvent = async (eventData) => {
  try {
    return { success: true, data: eventData }; // Mock response for now
    // Uncomment the below when backend is ready
    // const response = await api.post('/events', eventData, {
    //   headers: {
    //     'Content-Type': 'multipart/form-data', // For file uploads
    //   },
    // });
    // return response.data;
  } catch (error) {
    console.error('Error adding event:', error);
    throw error;
  }
};







// Fetch venues
export const getVenues = async () => {
  try {
    return {
      success: true,
      data: [
        { id: 1, name: 'Lecture Hall A', available: true },
        { id: 2, name: 'Seminar Room B', available: true },
        { id: 3, name: 'Auditorium C', available: true },
        { id: 4, name: 'Conference Room D', available: true },
      ],
    };
  } catch (error) {
    console.error('Error fetching venues:', error);
    throw error;
  }
};

// Update venue availability
export const updateVenueAvailability = async (id, available) => {
  try {
    return { success: true };
  } catch (error) {
    console.error('Error updating venue availability:', error);
    throw error;
  }
};

// Fetch equipment
export const getEquipment = async () => {
  try {
    return {
      success: true,
      data: [
        { id: 1, name: 'Projector A', available: true },
        { id: 2, name: 'Laptop B', available: true },
        { id: 3, name: 'Microphone C', available: true },
        { id: 4, name: 'Speaker D', available: true },
      ],
    };
  } catch (error) {
    console.error('Error fetching equipment:', error);
    throw error;
  }
};

// Update equipment availability
export const updateEquipmentAvailability = async (id, available) => {
  try {
    return { success: true };
  } catch (error) {
    console.error('Error updating equipment availability:', error);
    throw error;
  }
};