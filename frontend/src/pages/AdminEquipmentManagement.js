import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import logo from '../assets/logo.png';
import { getEquipments, changeResourceRestriction } from '../services/api';
import './AdminEquipmentManagement.css';

function AdminEquipmentManagement() {
  const navigate = useNavigate();
  const location = useLocation();
  const [equipments, setEquipments] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchEquipment = async () => {
      setLoading(true);
      try {
        const response = await getEquipments();
        if (response.success) {
          setEquipments(response.data);
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

  const handleRestrictionChange = async (equipment, newRestriction) => {
    try {
      const response = await changeResourceRestriction(equipment, newRestriction);
      if (response.success) {
        setEquipments((prevEquipments) =>
                prevEquipments.map((e) =>
                  e.resourceId === equipment.resourceId
                    ? { ...e, restriction: newRestriction }
                    : e
                )
              );
      } else {
        setError('Failed to update venue availability.');
      }
    } catch (err) {
      setError(err.response?.data?.error || 'An error occurred while updating venue availability.');
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
          ) : equipments.length > 0 ? (
            equipments.map((equipment) => (
              <div key={equipment.resourceId} className="equipment-item">
                <span className="equipment-name">{equipment.resourceName}</span>
                 <div className="availability-toggle">
                  <select
                    value={equipment.restriction}
                    onChange={(e) => handleRestrictionChange(equipment, e.target.value)} >
                        <option value="NonRestriction">NonRestriction</option>
                        <option value="ApprovalRequired">ApprovalRequired</option>
                        <option value="Restricted">Restricted</option>
                        <option value="NonBookable">NonBookable</option>
                      </select>
                    </div>
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