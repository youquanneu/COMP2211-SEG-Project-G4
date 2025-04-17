// src/pages/AdminResourceManagement.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './AdminResourceManagement.css';

function AdminResourceManagement() {
  const navigate = useNavigate();

  const handleVenueManagement = () => {
    localStorage.setItem('previousPage', '/resourcemanagement');
    navigate('/adminvenuemanagement', { state: { from: '/resourcemanagement' } });
  };

  const handleEquipmentManagement = () => {
    localStorage.setItem('previousPage', '/resourcemanagement');
    navigate('/adminequipmentmanagement', { state: { from: '/resourcemanagement' } });
  };

  const handleEquipmentApproval = () => {
    localStorage.setItem('previousPage', '/resourcemanagement');
    navigate('/equipmentapproval', { state: { from: '/resourcemanagement' } });
  };

  const handleBack = () => {
    localStorage.setItem('previousPage', '/admindashboard');
    navigate('/admindashboard', { state: { from: '/admindashboard' } });
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