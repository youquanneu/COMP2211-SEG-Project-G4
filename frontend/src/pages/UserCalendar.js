// src/pages/UserEmergency.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaUser, FaCog, FaBell } from 'react-icons/fa';
import logo from '../assets/logo.png';
import './UserEmergency.css';

function UserEmergency() {
  const navigate = useNavigate();
  const [location, setLocation] = useState('');
  const [description, setDescription] = useState('');
  const [showPopup, setShowPopup] = useState(false);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  // Check if the user is logged in by looking for userRole in localStorage
  const isLoggedIn = !!localStorage.getItem('userRole');

  const handleNavigation = (event) => {
    const selectedPage = event.target.value;
    if (selectedPage === 'userbooking') {
      navigate('/userbooking');
    } else if (selectedPage === 'calendar') {
      navigate('/calendar');
    } else if (selectedPage === 'emergency') {
      navigate('/emergency');
    }
  };

  const handleLogout = () => {
    setIsDropdownOpen(false);
    localStorage.removeItem('userRole'); // Clear role on logout
    navigate('/'); // Navigate to the root (Home page)
  };

  const handleProfile = () => {
    setIsDropdownOpen(false);
    navigate('/userprofile');
  };

  const handleSettings = () => {
    navigate('/settings');
  };

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

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
    console.log('isLoggedIn:', isLoggedIn); // Debug: Check login status
    if (isLoggedIn) {
      console.log('Navigating to /userhome'); // Debug: Confirm navigation path
      navigate('/userhome'); // Navigate to /userhome if logged in
    } else {
      console.log('Navigating to /home'); // Debug: Confirm navigation path
      navigate('/home'); // Navigate to /home if not logged in
    }
  };

  return (
    <div className="emergency-container">
      {/* Show header only if logged in */}
      {isLoggedIn && (
        <header className="emergency-header">
          <img src={logo} alt="Logo" className="emergency-logo" />
          <div className="nav-wrapper">
            <select className="nav-dropdown" onChange={handleNavigation}>
              <option value="home">Home</option>
              <option value="events">Events</option>
              <option value="directory">Directory</option>
              <option value="emergency">Emergency</option>
              <option value="userbooking">Booking</option>
              <option value="calendar">Calendar</option>
            </select>
          </div>
          <div className="icon-wrapper">
            <FaBell className="notification-icon" />
            <FaCog className="settings-icon" onClick={handleSettings} />
            <div className="user-menu">
              <FaUser className="user-icon" onClick={toggleDropdown} />
              {isDropdownOpen && (
                <div className="dropdown-menu">
                  <div className="dropdown-item" onClick={handleProfile}>
                    My Profile
                  </div>
                  <div className="dropdown-item" onClick={handleLogout}>
                    Logout
                  </div>
                </div>
              )}
            </div>
          </div>
        </header>
      )}
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