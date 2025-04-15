// src/pages/UserEmergency.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './UserEmergency.css';

function UserEmergency() {
  const navigate = useNavigate();
  const [location, setLocation] = useState('');
  const [description, setDescription] = useState('');
  const [showPopup, setShowPopup] = useState(false);

  const handleSubmit = () => {
    if (!location || !description) {
      alert('Please fill in both the location and description before submitting.');
      return;
    }
    setShowPopup(true);
    setLocation('');
    setDescription('');
    setTimeout(() => {
      setShowPopup(false);
    }, 2000);
  };

  const handleBack = () => {
    const isLoggedIn = !!localStorage.getItem('userRole');
    console.log('isLoggedIn:', isLoggedIn); // Debug: Check login status
    if (isLoggedIn) {
      console.log('Navigating to /userhome'); // Debug: Confirm navigation path
      navigate('/userhome'); // Navigate to /userhome if logged in
    } else {
      console.log('Navigating to /'); // Debug: Confirm navigation path
      navigate('/'); // Navigate to root (Home page) if not logged in
    }
  };

  return (
    <div className="emergency-container">
      <main className="emergency-content">
        <h1>Report an Emergency</h1>
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