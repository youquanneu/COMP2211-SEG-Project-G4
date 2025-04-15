// src/pages/EquipmentApproval.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './EquipmentApproval.css';

function EquipmentApproval() {
  const navigate = useNavigate();

  const handleBack = () => {
    navigate('/resourcemanagement'); // Navigate back to AdminResourceManagement
  };

  return (
    <div className="equipment-approval-container">
      <h1>Approval of Equipment</h1>
      <p>Equipment approval functionality coming soon!</p>
      <button className="back-button" onClick={handleBack}>
        Back
      </button>
    </div>
  );
}

export default EquipmentApproval;