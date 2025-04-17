import axios from 'axios';

// base URL for API - will connect to backend later
export function getAPI_URL(path) {
  return `http://localhost:8082/${path}`;
}

// User authentication
export const loginUser = async (credentials) => {
  try {
    // increment 1, simulate a successful response
    // this will be replaced with actual API call later

    
    // mock response for now
    return {
      success: true,
      user: {
        id: 1,
        name: 'Test User',
        email: credentials.email,
        role: 'student'
      },
      token: 'mock-token-12345'
    };
  } catch (error) {
    console.error('Login error:', error);
    throw error;
  }
};

// Get campus locations (mock example which is highly subject to change)
export const getCampusLocations = async () => {
  try {
    // For Increment 1, return mock data
    // Will be replaced with API call later
    
    return [
      { id: 1, name: 'Main Building', type: 'academic', position: { x: 100, y: 150 } },
      { id: 2, name: 'Library', type: 'academic', position: { x: 250, y: 200 } },
      { id: 3, name: 'Computer Lab A', type: 'lab', position: { x: 180, y: 300 } },
      { id: 4, name: 'Student Center', type: 'dining', position: { x: 400, y: 280 } },
      { id: 5, name: 'Study Hall 1', type: 'study', position: { x: 320, y: 120 } }
    ];
  } catch (error) {
    console.error('Error fetching locations:', error);
    throw error;
  }
};

// mock room booking api which is highly subject to change
export const getAvailableRooms = async (date) => {
  try {
    // mock data for Increment 1
    return [
      { id: 1, name: 'Study Room 101', capacity: 4, building: 'Library', availableTimes: ['9:00', '10:00', '11:00'] },
      { id: 2, name: 'Conference Room A', capacity: 12, building: 'Main Building', availableTimes: ['13:00', '14:00', '15:00'] },
      { id: 3, name: 'Lab B', capacity: 24, building: 'Science Building', availableTimes: ['16:00', '17:00'] }
    ];
  } catch (error) {
    console.error('Error fetching available rooms:', error);
    throw error;
  }
};

// Fetch emergencies
export const getEmergencies = async () => {
  try {
    const response = await axios.get(getAPI_URL('emergency/getAllEmergency'));
    const emergency = response.data
    console.log(emergency)
    const formattedEmergencies = emergency.map((item) => ({
      date: item.reportedTime ? item.reportedTime.split('T')[0] : 'Unknown',
      location: item.location?.resourceName || 'Unknown',
      description: item.description || 'No description',
      status: item.status || 'Pending', // If 'status' isn't in the data, default to 'Pending'
    }));
    return {
      success: true,
      data: formattedEmergencies,
    };
  } catch (error) {
    console.error('Error fetching emergencies:', error);
    throw error;
  }
};
// Send panic alert
export const sendPanicAlert = async () => {
  try {
    return { success: true };
  } catch (error) {
    console.error('Error sending panic alert:', error);
    throw error;
  }
};