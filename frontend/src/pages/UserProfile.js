// src/pages/UserProfile.js
import React,{ useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaUser, FaCog, FaBell } from 'react-icons/fa';
import logo from '../assets/logo.png';
import profilePic from '../assets/profile-pic-placeholder.png'; // Add a placeholder profile picture
import './UserProfile.css';
import { getAPI_URL } from "../services/api";
import axios from 'axios';

function UserProfile() {
  const navigate = useNavigate();
  const [userData, setUserData] = useState(null)
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isDropdownOpen, setIsDropdownOpen] = React.useState(false);

  useEffect(() => {
      const fetchUserProfile = async () => {
        try {
          const userEmail = localStorage.getItem('userEmail');
          const response = await axios.post(getAPI_URL('user/userProfile'), { email: userEmail });
          setUserData(response.data);
        } catch (error) {
          console.error('Error fetching user profile:', error);
          setError('Failed to load user profile.');
        } finally {
          setLoading(false);
        }
      };

      fetchUserProfile();
    }, []);

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
    navigate(userRole === 'AdministrativeStaff' ? '/admindashboard' : '/userhome');
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
            {userData && <h2>{userData.username}</h2>}
          </div>
        </div>
        {/* User Details */}
        <div className="profile-details">
          {userData && <p><strong>Email:</strong> {userData.email}</p>}
          {userData && <p><strong>Role:</strong> {userData.userRole}</p>}
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