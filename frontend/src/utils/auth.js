import { getStoredToken, setAuthHeader } from '../services/api';

// Check if user is authenticated
export const isAuthenticated = () => {
  const token = getStoredToken();
  return !!token;
};

// Initialize auth from localStorage
export const initializeAuth = () => {
  const token = getStoredToken();
  if (token) {
    setAuthHeader(token);
    return true;
  }
  return false;
};

// Handle authentication errors
export const handleAuthError = (error) => {
  if (error.response && (error.response.status === 401 || error.response.status === 403)) {
    // Clear auth data
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setAuthHeader(null);
    // Redirect to login
    window.location.href = '/login';
  }
  return error;
}; 