import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './UserEmergency.css';
import { getAPI_URL } from "../services/api";

function UserEmergency() {
  const navigate = useNavigate();
  const [location, setLocation] = useState(null);
  const [description, setDescription] = useState('');
  const [venues, setVenues] = useState([]);
  const [showPopup, setShowPopup] = useState(false);
  const [error, setError] = useState('');
  const [emergencyCase, setEmergencyCase] = useState('');

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
        console.log('Venues Response:', response.data); // Debug
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

        // Check for missing id/venueId
//        venueData.forEach((venue, index) => {
//          if (!venue.id && !venue.venueId) {
//            console.warn(`Venue at index ${index} missing id/venueId:`, venue);
//          }
//          if (!venue.name && !venue.venueName) {
//            console.warn(`Venue at index ${index} missing name/venueName:`, venue);
//          }
//        });

        setVenues(venueData);
        if (venueData.length > 0) {
          setLocation(venueData[0]);
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
    if (!location || !emergencyCase) {
      setError('Please select a venue and enter an emergency case.');
      await sendLog('submit_emergency_error', 'Missing venue or description');
      return;
    }

    try {
      const reportData = { location, emergencyCase, description };
      console.log(reportData)
      await axios.post(getAPI_URL('emergency/reportEmergency'), reportData);
      await sendLog('submit_emergency', `Location: ${location}, Description: ${description}`);
      setShowPopup(true);
      setError('');
      setLocation(venues.length > 0 ? (venues[0].name || venues[0].venueName || '') : '');
      setEmergencyCase('');
      setDescription('');
      setTimeout(() => {
        setShowPopup(false);
      }, 2000);
    } catch (err) {
      setError('Failed to submit emergency. Please try again.');
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
      navigate(-1);
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
            value={location ? location.resourceId : ''}
            onChange={(e) => {
              const locationSelected = venues.find(venue => venue.resourceId === parseInt(e.target.value))
              setLocation(locationSelected);
              sendLog('select_location', `Selected: ${e.target.value}`);
            }}
            className="location-dropdown"
          >
            {venues.length > 0 ? (
              venues.map((venue, index) => (
                <option
                  key={venue.resourceId || index}
                  value={venue.resourceId}
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
           <label>Emergency Case:</label>
           <textarea
           value={emergencyCase}
           onChange={(e) => setEmergencyCase(e.target.value)}
           placeholder=""
           rows="2"
          />
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