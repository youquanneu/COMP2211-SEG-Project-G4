// src/pages/Dashboard.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png'; // Optional: Reuse the logo
import './Dashboard.css'; // Import new styles

function Dashboard() {
  const navigate = useNavigate();

  const handleLogout = () => {
    navigate('/login');
  };

  return (
    <div className="dashboard-container">
      <img src={logo} alt="Logo" className="dashboard-logo" />
      <h1>Welcome to Your Homepage!</h1>
      <p>You have successfully logged in.</p>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
}

export default Dashboard;