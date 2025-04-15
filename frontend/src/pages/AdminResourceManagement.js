// src/pages/AdminResourceManagement.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './AdminResourceManagement.css';

function AdminResourceManagement() {
  const navigate = useNavigate();

  const handleVenueBooking = () => {
    // Placeholder for now; can add navigation or functionality later
    alert('Venue Booking functionality coming soon!');
  };

  const handleEquipmentBooking = () => {
    // Placeholder for now; can add navigation or functionality later
    alert('Equipment Booking functionality coming soon!');
  };

  const handleEquipmentApproval = () => {
    navigate('/equipmentapproval'); // Navigate to the Equipment Approval page
  };

  const handleBack = () => {
    navigate('/adminhome'); // Navigate back to AdminHome
  };

  return (
    <div className="admin-resource-management-container">
      <h1>Resource Management</h1>
      <button className="action-button" onClick={handleVenueBooking}>
        Venue Booking
      </button>
      <button className="action-button" onClick={handleEquipmentBooking}>
        Equipment Booking
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