import React, { useState } from 'react';
import { registerUser } from '../services/api'; // Import the registerUser function
import './RegisterNewUser.css';

function RegisterNewUser({ onClose }) {
  const [role, setRole] = useState('');
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [showSuccessPopup, setShowSuccessPopup] = useState(false);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!role || !username || !email ) {
      alert('Please fill in all fields.');
      return;
    }
    setLoading(true);
    setError(null);

    const userData = { username, email, userRole:role };
    console.log("Input :" )
    console.log(userData)
    try {
      const response = await registerUser(userData);
      if (response.success) {
        setShowSuccessPopup(true);
        setTimeout(() => {
          setShowSuccessPopup(false);
          onClose();
        }, 2000);

        setRole('');
        setUsername('');
        setEmail('');
      } else {
        setError('Failed to register user.');
      }
    } catch (err) {
      setError(err.response?.data?.error || 'An error occurred while registering the user.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="register-modal-overlay">
      <div className="register-modal">
        <h2>User Registration</h2>
        {error && (
          <div className="error-message">
            {error}
          </div>
        )}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Role:</label>
            <select
              value={role}
              onChange={(e) => setRole(e.target.value)}
              className="role-dropdown"
            >
              <option value="">Select a role</option>
              <option value="Student">Student</option>
              <option value="Lecturer">Lecturer</option>
            </select>
          </div>
          <div className="form-group">
            <label>Username:</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder=""
            />
          </div>
          <div className="form-group">
            <label>Email:</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder=""
            />
          </div>
          <button type="submit" className="register-button" disabled={loading}>
            {loading ? 'Registering...' : 'Register'}
          </button>
        </form>
        <button className="close-button" onClick={onClose}>
          Close
        </button>
      </div>
      {showSuccessPopup && (
        <div className="success-popup">
          <p>New user registered</p>
        </div>
      )}
    </div>
  );
}

export default RegisterNewUser;