// src/pages/AdminHome.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaUser, FaCog, FaBell } from 'react-icons/fa';
import logo from '../assets/logo.png';
import './AdminHome.css';

function AdminHome() {
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const handleNavigation = (event) => {
    const selectedPage = event.target.value;
    if (selectedPage === 'userbooking') {
      navigate('/userbooking');
    } else if (selectedPage === 'calendar') {
      navigate('/calendar');
    } else if (selectedPage === 'resourcemanagement') {
      navigate('/resourcemanagement');
    } else if (selectedPage === 'usermanagement') {
      navigate('/usermanagement');
    }
  };

  const handleLogout = () => {
    setIsDropdownOpen(false);
    localStorage.removeItem('userRole');
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

  return (
    <div className="admin-home-container">
      <header className="admin-home-header">
        <img src={logo} alt="Logo" className="admin-home-logo" />
        <div className="nav-wrapper">
          <select className="nav-dropdown" onChange={handleNavigation}>
            <option value="dashboard">Dashboard</option>
            <option value="events">Events</option>
            <option value="directory">Directory</option>
            <option value="emergency">Emergency</option>
            <option value="userbooking">Booking</option>
            <option value="calendar">Calendar</option>
            <option value="resourcemanagement">Resource Management</option>
            <option value="usermanagement">User Management</option>
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
      <main className="admin-home-content">
        <h1>Admin Dashboard</h1>
        <p>Welcome to the Admin Dashboard. Use the navigation above to manage resources, users, and more.</p>
      </main>
    </div>
  );
}

export default AdminHome;