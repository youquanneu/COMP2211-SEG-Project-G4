import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import logo from '../assets/logo.png';
import { getVenues, updateVenueAvailability } from '../api'; // Import the new functions
import './AdminVenueManagement.css';

function AdminVenueManagement() {
  const navigate = useNavigate();
  const location = useLocation();
  const [venues, setVenues] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchVenues = async () => {
      setLoading(true);
      try {
        const response = await getVenues();
        if (response.success) {
          setVenues(response.data);
        } else {
          setError('Failed to fetch venues.');
        }
      } catch (err) {
        setError(err.response?.data?.error || 'An error occurred while fetching venues.');
      } finally {
        setLoading(false);
      }
    };

    fetchVenues();
  }, []);

  const handleToggleAvailability = async (id) => {
    const venue = venues.find((v) => v.id === id);
    const updatedAvailability = !venue.available;

    try {
      const response = await updateVenueAvailability(id, updatedAvailability);
      if (response.success) {
        setVenues((prevVenues) =>
          prevVenues.map((venue) =>
            venue.id === id ? { ...venue, available: updatedAvailability } : venue
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
    <div className="admin-venue-management-container">
      <header className="admin-venue-management-header">
        <img src={logo} alt="Logo" className="admin-venue-management-logo" />
      </header>
      <main className="admin-venue-management-content">
        <h1>Venue Management</h1>
        {error && (
          <div className="error-message">
            {error}
          </div>
        )}
        <div className="venue-list">
          {loading ? (
            <p>Loading venues...</p>
          ) : venues.length > 0 ? (
            venues.map((venue) => (
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
            ))
          ) : (
            <p>No venues found.</p>
          )}
        </div>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
      </main>
    </div>
  );
}

export default AdminVenueManagement;