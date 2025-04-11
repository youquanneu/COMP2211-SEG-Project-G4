// src/pages/AdminUserManagement.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaUser, FaCog, FaBell } from 'react-icons/fa';
import logo from '../assets/logo.png';
import RegisterNewUser from './RegisterNewUser'; // Import the new component
import './AdminUserManagement.css';

function AdminUserManagement() {
    const navigate = useNavigate();
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [filter, setFilter] = useState('');
    const [showRegisterPopup, setShowRegisterPopup] = useState(false); // State for pop-up visibility

    const students = [
        { id: 'S001', name: 'John Doe', email: 'john.doe@example.com' },
        { id: 'S002', name: 'Jane Smith', email: 'jane.smith@example.com' },
    ];

    const lecturers = [
        { id: 'L001', name: 'Dr. Alice Brown', email: 'alice.brown@example.com' },
        { id: 'L002', name: 'Prof. Bob Wilson', email: 'bob.wilson@example.com' },
    ];

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
        } else if (selectedPage === 'dashboard') {
            navigate('/adminhome');
        }
    };

    const handleLogout = () => {
        setIsDropdownOpen(false);
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

    const handleFilterChange = (event) => {
        setFilter(event.target.value);
    };

    const handleRegisterNewUser = () => {
        setShowRegisterPopup(true); // Show the pop-up
    };

    const closeRegisterPopup = () => {
        setShowRegisterPopup(false); // Close the pop-up
    };

    const userList = filter === 'students' ? students : filter === 'lecturers' ? lecturers : [];

    return (
        <div className="user-management-container">
            <header className="user-management-header">
                <img src={logo} alt="Logo" className="user-management-logo" />
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
            <main className="user-management-content">
                <h1>User Management</h1>
                <div className="filter-section">
                    <label>Filter:</label>
                    <select className="filter-dropdown" onChange={handleFilterChange} value={filter}>
                        <option value="">Select a filter</option>
                        <option value="students">Students</option>
                        <option value="lecturers">Lecturers</option>
                    </select>
                </div>
                {filter && (
                    <div className="info-section">
                        <h3>Information:</h3>
                        <div className="info-box">
                            {userList.length > 0 ? (
                                userList.map((user) => (
                                    <div key={user.id} className="user-item">
                                        <p><strong>ID:</strong> {user.id}</p>
                                        <p><strong>Name:</strong> {user.name}</p>
                                        <p><strong>Email:</strong> {user.email}</p>
                                    </div>
                                ))
                            ) : (
                                <p>No users found.</p>
                            )}
                        </div>
                    </div>
                )}
                <div className="register-link">
                    <button className="register-button" onClick={handleRegisterNewUser}>
                        Register New User
                    </button>
                </div>
            </main>
            {showRegisterPopup && (
                <RegisterNewUser onClose={closeRegisterPopup} />
            )}
        </div>
    );
}

export default AdminUserManagement;