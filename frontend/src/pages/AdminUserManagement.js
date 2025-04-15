// src/pages/AdminUserManagement.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import RegisterNewUser from './RegisterNewUser';
import './AdminUserManagement.css';

function AdminUserManagement() {
  const navigate = useNavigate();
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

  const handleFilterChange = (event) => {
    setFilter(event.target.value);
  };

  const handleRegisterNewUser = (event) => {
    event.preventDefault(); // Prevent default navigation behavior of the <a> tag
    setShowRegisterPopup(true); // Show the pop-up
  };

  const closeRegisterPopup = () => {
    setShowRegisterPopup(false); // Close the pop-up
  };

  const handleBack = () => {
    navigate('/admindashboard'); // Navigate back to AdminHome
  };

  const userList = filter === 'students' ? students : filter === 'lecturers' ? lecturers : [];

  return (
    <div className="user-management-container">
      <header className="user-management-header">
        <img src={logo} alt="Logo" className="user-management-logo" />
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
          <a href="#" className="register-link-text" onClick={handleRegisterNewUser}>
            Register New User
          </a>
        </div>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
      </main>
      {showRegisterPopup && <RegisterNewUser onClose={closeRegisterPopup} />}
    </div>
  );
}

export default AdminUserManagement;