// src/pages/AdminDashboard.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaBuilding, FaCogs, FaUser, FaCog } from 'react-icons/fa';
import logo from '../assets/logo.png';
import './AdminDashboard.css';

function AdminDashboard() {
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const venueStats = { booked: 6, total: 30 };
  const equipmentStats = { booked: 8, total: 50 };

  const handleNavigation = (event) => {
    const selectedPage = event.target.value;
    if (selectedPage === 'usermanagement') {
      navigate('/usermanagement');
    } else if (selectedPage === 'dashboard') {
      navigate('/admindashboard');
    } else if (selectedPage === 'emergency') {
      navigate('/adminemergency');
    } else if (selectedPage === 'resourcemanagement') {
      navigate('/resourcemanagement');
    } else if (selectedPage === 'events') { // Updated to route to /adminaddevent
      navigate('/adminaddevent');
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

  const handleVenueClick = () => {
    navigate('/adminvenuemanagement');
  };

  const handleEquipmentClick = () => {
    navigate('/adminequipmentmanagement');
  };

  return (
    <div className="admin-dashboard-container">
      <header className="admin-dashboard-header">
        <img src={logo} alt="Logo" className="admin-dashboard-logo" />
        <div className="nav-wrapper">
          <select className="nav-dropdown" onChange={handleNavigation}>
            <option value="dashboard">Dashboard</option>
            <option value="events">Events</option>
            <option value="emergency">Emergency</option>
            <option value="usermanagement">User Management</option>
            <option value="resourcemanagement">Resource Management</option>
          </select>
        </div>
        <div className="icon-wrapper">
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
      <main className="admin-dashboard-content">
        <h1>Admin Dashboard</h1>
        <div className="analytics-section">
          <div className="analytics-card" onClick={handleVenueClick}>
            <h3>Venue Booked</h3>
            <FaBuilding className="analytics-icon" />
            <p className="fraction">
              {venueStats.booked}/{venueStats.total}
            </p>
          </div>
          <div className="analytics-card" onClick={handleEquipmentClick}>
            <h3>Equipment Booked</h3>
            <FaCogs className="analytics-icon" />
            <p className="fraction">
              {equipmentStats.booked}/{equipmentStats.total}
            </p>
          </div>
        </div>
      </main>
    </div>
  );
}

export default AdminDashboard;