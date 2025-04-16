import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './UserEmergency.css';

function UserEmergency() {
  const navigate = useNavigate();
  const [location, setLocation] = useState('');
  const [description, setDescription] = useState('');
  const [showPopup, setShowPopup] = useState(false);
  const [error, setError] = useState('');

  // Send log to backend
  const sendLog = async (action, value) => {
    try {
      await axios.post('http://localhost:8080/api/logs', { action, value });
    } catch (err) {
      console.error('Log error:', err.message);
    }
  };

  const handleSubmit = async () => {
    if (!location || !description) {
      setError('Please fill in both location and description.');
      return;
    }

    try {
      const reportData = { location, description };
      await axios.post('http://localhost:8080/api/emergencies', reportData);
      await sendLog('submit_emergency', `Location: ${location}, Description: ${description}`);
      setShowPopup(true);
      setError('');
      setLocation('');
      setDescription('');
      setTimeout(() => {
        setShowPopup(false);
      }, 2000);
    } catch (err) {
      setError('Failed to submit emergency. Please try again.');
      console.error('Submit error:', err.message, err.response?.data);
    }
  };

  const handleBack = () => {
    sendLog('back', 'Clicked back');
    const isLoggedIn = !!localStorage.getItem('userRole');
    console.log('isLoggedIn:', isLoggedIn);
    if (isLoggedIn) {
      console.log('Navigating to /userhome');
      navigate('/userhome');
    } else {
      console.log('Navigating to /');
      navigate('/');
    }
  };

  return (
    <div className="emergency-container">
      <main className="emergency-content">
        <h1>Report an Emergency</h1>
        {error && <p className="error-message">{error}</p>}
        <div className="form-group">
          <label>Location:</label>
          <input
            type="text"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
            placeholder=""
          />
        </div>
        <div className="form-group">
          <label>Description:</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder=""
            rows="6"
          />
        </div>
        <button className="submit-button" onClick={handleSubmit}>
          Submit
        </button>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
        {showPopup && (
          <div className="popup">
            <p>Emergency sent to admin</p>
          </div>
        )}
      </main>
    </div>
  );
}

export default UserEmergency;