// src/pages/UserProfile.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { FaUser, FaCog, FaBell } from 'react-icons/fa';
import logo from '../assets/logo.png';
import profilePic from '../assets/profile-pic-placeholder.png'; // Add a placeholder profile picture
import './UserProfile.css';

function UserProfile() {
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = React.useState(false);

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
    navigate('/');
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

  const handleBack = () => {
    const userRole = localStorage.getItem('userRole') || 'user';
    navigate(userRole === 'admin' ? '/adminhome' : '/userhome');
  };

  return (
    <div className="profile-container">
      <header className="profile-header">
        <img src={logo} alt="Logo" className="profile-logo" />
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
      <main className="profile-content">
        {/* Add Profile Picture */}
        <div className="profile-header-section">
          <img src={profilePic} alt="Profile" className="profile-picture" />
          <div className="profile-info">
            <h2>John Doe</h2>
          </div>
        </div>
        {/* User Details */}
        <div className="profile-details">
          <p><strong>Email:</strong> john.doe@example.com</p>
          <p><strong>Role:</strong> Student</p>
        </div>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
        {/* Add Sign Out Button */}
        <button className="sign-out-button" onClick={handleLogout}>
          Sign Out
        </button>
      </main>
    </div>
  );
}

export default UserProfile;