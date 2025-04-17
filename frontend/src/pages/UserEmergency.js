import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './UserEmergency.css';
import { getAPI_URL } from "../services/api";

function UserEmergency() {
  const navigate = useNavigate();
  const [location, setLocation] = useState('');
  const [description, setDescription] = useState('');
  const [venues, setVenues] = useState([]);
  const [showPopup, setShowPopup] = useState(false);
  const [error, setError] = useState('');

  // Send log to backend
  const sendLog = async (action, value) => {
    try {
      await axios.post(getAPI_URL('api/logs'), { action, value });
    } catch (err) {
      console.error('Log error:', err.message);
    }
  };

  // Fetch venues on component mount
  useEffect(() => {
    const fetchVenues = async () => {
      await sendLog('fetch_venues', 'Fetched venues for emergency');
      try {
        const response = await axios.get(getAPI_URL('user/venue/getAllVenue'));
        console.log('Venues Response:', response.data);
        let venueData = response.data;

        // Handle various response structures
        if (!Array.isArray(venueData)) {
          if (venueData.venues && Array.isArray(venueData.venues)) {
            venueData = venueData.venues;
          } else if (venueData.data && Array.isArray(venueData.data)) {
            venueData = venueData.data;
          } else if (venueData.result && Array.isArray(venueData.result)) {
            venueData = venueData.result;
          } else {
            setError('Invalid venue data format. Expected an array.');
            console.error('Venue structure:', JSON.stringify(response.data, null, 2));
            return;
          }
        }

        // Log venue count and first venue's keys
        console.log('Venue count:', venueData.length);
        if (venueData.length > 0) {
          console.log('First venue keys:', Object.keys(venueData[0]));
          console.log('First venue values:', venueData[0]);
        }

        // Check for missing resourceId/resourceName
        venueData.forEach((venue, index) => {
          if (!venue.resourceId) {
            console.warn(`Venue at index ${index} missing resourceId:`, venue);
          }
          if (!venue.resourceName) {
            console.warn(`Venue at index ${index} missing resourceName:`, venue);
          }
        });

        setVenues(venueData);
        if (venueData.length > 0) {
          setLocation(venueData[0].resourceName || '');
        }
      } catch (err) {
        setError('Failed to load venues. Check if backend is running.');
        console.error('Fetch venues error:', err.message, err.response?.data);
        await sendLog('fetch_venues_error', `Failed: ${err.message}`);
      }
    };
    fetchVenues();
  }, []);

  const handleSubmit = async () => {
    if (!location || !description) {
      setError('Please select a venue and enter a description.');
      await sendLog('submit_emergency_error', 'Missing venue or description');
      return;
    }

    try {
      const userEmail = localStorage.getItem('userEmail') || 'Anonymous';
      const token = localStorage.getItem('token'); // Adjust based on your auth key
      const reportData = {
        location: location, // Send resourceName as string
        description,
        userEmail // Include userEmail for backend
      };
      console.log('Submitting reportData:', reportData);
      console.log('Authorization Token:', token || 'None');

      const config = {
        headers: {
          'Content-Type': 'application/json',
          ...(token && { Authorization: `Bearer ${token}` }) // Add token if exists
        }
      };

      const response = await axios.post(
        getAPI_URL('emergency/reportEmergency'),
        reportData,
        config
      );
      console.log('Submit Response:', response.data);
      await sendLog('submit_emergency', `Location: ${location}, Description: ${description}`);
      setShowPopup(true);
      setError('');
      setLocation(venues.length > 0 ? (venues[0].resourceName || '') : '');
      setDescription('');
      setTimeout(() => {
        setShowPopup(false);
      }, 2000);
    } catch (err) {
      setError('Failed to submit emergency. Authentication may be required.');
      console.error('Submit error:', err.message, err.response?.data);
      await sendLog('submit_emergency_error', `Failed: ${err.message}`);
    }
  };

  const handleBack = () => {
    sendLog('back', 'Clicked back');
    const isLoggedIn = !!localStorage.getItem('userRole');
    console.log('isLoggedIn:', isLoggedIn);
    if (isLoggedIn) {
      console.log('Navigating to /userhome');
      navigate('/userhome');
    } else {
      console.log('Navigating to /');
      navigate('/');
    }
  };

  return (
    <div className="emergency-container">
      <main className="emergency-content">
        <h1>Report an Emergency</h1>
        {error && <p className="error-message">{error}</p>}
        <div className="form-group">
          <label>Location:</label>
          <select
            value={location}
            onChange={(e) => {
              setLocation(e.target.value);
              sendLog('select_location', `Selected: ${e.target.value}`);
            }}
            className="location-dropdown"
          >
            {venues.length > 0 ? (
              venues.map((venue, index) => (
                <option
                  key={venue.resourceId || index}
                  value={venue.resourceName || ''}
                >
                  {venue.resourceName || 'Unknown Venue'}
                </option>
              ))
            ) : (
              <option value="" disabled>
                No venues available
              </option>
            )}
          </select>
        </div>
        <div className="form-group">
          <label>Description:</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder=""
            rows="6"
          />
        </div>
        <button className="submit-button" onClick={handleSubmit}>
          Submit
        </button>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
        {showPopup && (
          <div className="popup">
            <p>Emergency sent to admin</p>
          </div>
        )}
      </main>
    </div>
  );
}

export default UserEmergency;