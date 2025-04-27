import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import logo from '../assets/logo.png';
import './AdminVenueManagement.css';
import { getVenues, changeResourceRestriction } from '../services/api';

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
            setVenues(response.data);
      } catch (err) {
        setError(err.response?.data?.error || 'An error occurred while fetching venues.');
      } finally {
        setLoading(false);
      }
    };

    fetchVenues();
  }, []);

  const handleRestrictionChange = async (venue, newRestriction) => {
    try {
      const response = await changeResourceRestriction(venue, newRestriction);
      if (response.success) {
        setVenues((prevVenues) =>
                prevVenues.map((v) =>
                  v.resourceId === venue.resourceId
                    ? { ...v, restriction: newRestriction }
                    : v
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
              <div key={venue.resourceId} className="venue-item">
                <span className="venue-name">{venue.resourceName}</span>
                <div className="availability-toggle">
                 <select
                    value={venue.restriction}
                    onChange={(e) => handleRestrictionChange(venue, e.target.value)} >
                        <option value="NonRestriction">NonRestriction</option>
                        <option value="ApprovalRequired">ApprovalRequired</option>
                        <option value="Restricted">Restricted</option>
                        <option value="NonBookable">NonBookable</option>
                    </select>
                  </div>
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