import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import logo from '../assets/logo.png';
import { getEquipment, updateEquipmentAvailability } from '../api'; // Import the new functions
import './AdminEquipmentManagement.css';

function AdminEquipmentManagement() {
  const navigate = useNavigate();
  const location = useLocation();
  const [equipment, setEquipment] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchEquipment = async () => {
      setLoading(true);
      try {
        const response = await getEquipment();
        if (response.success) {
          setEquipment(response.data);
        } else {
          setError('Failed to fetch equipment.');
        }
      } catch (err) {
        setError(err.response?.data?.error || 'An error occurred while fetching equipment.');
      } finally {
        setLoading(false);
      }
    };

    fetchEquipment();
  }, []);

  const handleToggleAvailability = async (id) => {
    const item = equipment.find((e) => e.id === id);
    const updatedAvailability = !item.available;

    try {
      const response = await updateEquipmentAvailability(id, updatedAvailability);
      if (response.success) {
        setEquipment((prevEquipment) =>
          prevEquipment.map((item) =>
            item.id === id ? { ...item, available: updatedAvailability } : item
          )
        );
      } else {
        setError('Failed to update equipment availability.');
      }
    } catch (err) {
      setError(err.response?.data?.error || 'An error occurred while updating equipment availability.');
    }
  };

  const handleBack = () => {
    const fromState = location.state?.from;
    const fromStorage = localStorage.getItem('previousPage');
    const from = fromState || fromStorage || '/admindashboard';
    navigate(from);
  };

  return (
    <div className="admin-equipment-management-container">
      <header className="admin-equipment-management-header">
        <img src={logo} alt="Logo" className="admin-equipment-management-logo" />
      </header>
      <main className="admin-equipment-management-content">
        <h1>Equipment Management</h1>
        {error && (
          <div className="error-message">
            {error}
          </div>
        )}
        <div className="equipment-list">
          {loading ? (
            <p>Loading equipment...</p>
          ) : equipment.length > 0 ? (
            equipment.map((item) => (
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
            ))
          ) : (
            <p>No equipment found.</p>
          )}
        </div>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
      </main>
    </div>
  );
}

export default AdminEquipmentManagement;