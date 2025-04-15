// src/pages/AdminVenueManagement.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import './AdminVenueManagement.css';

function AdminVenueManagement() {
  const navigate = useNavigate();

  // Mock data for venues
  const initialVenues = [
    { id: 1, name: 'Lecture Hall A', available: true },
    { id: 2, name: 'Seminar Room B', available: true },
    { id: 3, name: 'Auditorium C', available: true },
    { id: 4, name: 'Conference Room D', available: true },
  ];

  const [venues, setVenues] = useState(initialVenues);

  const handleToggleAvailability = (id) => {
    setVenues((prevVenues) =>
      prevVenues.map((venue) =>
        venue.id === id ? { ...venue, available: !venue.available } : venue
      )
    );
  };

  const handleBack = () => {
    navigate('/resourcemanagement');
  };

  return (
    <div className="admin-venue-management-container">
      <header className="admin-venue-management-header">
        <img src={logo} alt="Logo" className="admin-venue-management-logo" />
      </header>
      <main className="admin-venue-management-content">
        <h1>Venue Management</h1>
        <div className="venue-list">
          {venues.map((venue) => (
            <div key={venue.id} className="venue-item">
              <span className="venue-name">{venue.name}</span>
              <label className="availability-toggle">
                <input
                  type="checkbox"
                  checked={venue.available}
                  onChange={() => handleToggleAvailability(venue.id)}
                />
                <span className="toggle-label">
                  {venue.available ? 'Available' : 'Unavailable'}
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

export default AdminVenueManagement;