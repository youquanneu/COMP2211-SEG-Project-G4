// src/pages/AdminResourceManagement.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './AdminResourceManagement.css';

function AdminResourceManagement() {
  const navigate = useNavigate();

  const handleVenueManagement = () => {
    navigate('/adminvenuemanagement');
  };

  const handleEquipmentManagement = () => {
    navigate('/adminequipmentmanagement');
  };

  const handleEquipmentApproval = () => {
    navigate('/equipmentapproval');
  };

  const handleBack = () => {
    navigate('/admindashboard'); // Updated to navigate to AdminDashboard
  };

  return (
    <div className="admin-resource-management-container">
      <h1>Resource Management</h1>
      <button className="action-button" onClick={handleVenueManagement}>
        Venue Management
      </button>
      <button className="action-button" onClick={handleEquipmentManagement}>
        Equipment Management
      </button>
      <button className="action-button" onClick={handleEquipmentApproval}>
        Approval of Equipment
      </button>
      <button className="back-button" onClick={handleBack}>
        Back
      </button>
    </div>
  );
}

export default AdminResourceManagement;