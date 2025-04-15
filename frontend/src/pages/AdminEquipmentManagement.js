// src/pages/AdminEquipmentManagement.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import './AdminEquipmentManagement.css';

function AdminEquipmentManagement() {
  const navigate = useNavigate();

  // Mock data for equipment
  const initialEquipment = [
    { id: 1, name: 'Projector A', available: true },
    { id: 2, name: 'Laptop B', available: true },
    { id: 3, name: 'Microphone C', available: true },
    { id: 4, name: 'Speaker D', available: true },
  ];

  const [equipment, setEquipment] = useState(initialEquipment);

  const handleToggleAvailability = (id) => {
    setEquipment((prevEquipment) =>
      prevEquipment.map((item) =>
        item.id === id ? { ...item, available: !item.available } : item
      )
    );
  };

  const handleBack = () => {
    navigate('/resourcemanagement');
  };

  return (
    <div className="admin-equipment-management-container">
      <header className="admin-equipment-management-header">
        <img src={logo} alt="Logo" className="admin-equipment-management-logo" />
      </header>
      <main className="admin-equipment-management-content">
        <h1>Equipment Management</h1>
        <div className="equipment-list">
          {equipment.map((item) => (
            <div key={item.id} className="equipment-item">
              <span className="equipment-name">{item.name}</span>
              <label className="availability-toggle">
                <input
                  type="checkbox"
                  checked={item.available}
                  onChange={() => handleToggleAvailability(item.id)}
                />
                <span className="toggle-label">
                  {item.available ? 'Available' : 'Unavailable'}
                </span>
              </label>
            </div>
          ))}
        </div>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
      </main>
    </div>
  );
}

export default AdminEquipmentManagement;