import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './UserEmergency.css';
import { getAPI_URL } from "../services/api";

function UserEmergency() {
  const navigate = useNavigate();
  const [location, setLocation] = useState('');
  const [description, setDescription] = useState('');
  const [resources, setResources] = useState([]);
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

  // Fetch resources on component mount
  useEffect(() => {
    const fetchResources = async () => {
      await sendLog('fetch_resources', 'Fetched resources for emergency');
      try {
        const response = await axios.get(getAPI_URL('user/venue/getAllVenue'));
        console.log('Resources Response:', response.data); // Debug
        let resourceData = response.data;

        // Handle various response structures
        if (!Array.isArray(resourceData)) {
          if (resourceData.resources && Array.isArray(resourceData.resources)) {
            resourceData = resourceData.resources;
          } else if (resourceData.data && Array.isArray(resourceData.data)) {
            resourceData = resourceData.data;
          } else if (resourceData.result && Array.isArray(resourceData.result)) {
            resourceData = resourceData.result;
          } else {
            setError('Invalid resource data format. Expected an array.');
            console.error('Resource structure:', JSON.stringify(response.data, null, 2));
            return;
          }
        }

        // Log resource count and first resource's keys
        console.log('Resource count:', resourceData.length);
        if (resourceData.length > 0) {
          console.log('First resource keys:', Object.keys(resourceData[0]));
        }

        // Check for missing id/resourceId
        resourceData.forEach((resource, index) => {
          if (!resource.id && !resource.resourceId) {
            console.warn(`Resource at index ${index} missing id/resourceId:`, resource);
          }
        });

        setResources(resourceData);
        if (resourceData.length > 0) {
          setLocation(resourceData[0].name || resourceData[0].resourceName || '');
        }
      } catch (err) {
        setError('Failed to load resources. Please try again.');
        console.error('Fetch resources error:', err.message, err.response?.data);
        await sendLog('fetch_resources_error', `Failed: ${err.message}`);
      }
    };
    fetchResources();
  }, []);

  const handleSubmit = async () => {
    if (!location || !description) {
      setError('Please select a location and enter a description.');
      await sendLog('submit_emergency_error', 'Missing location or description');
      return;
    }

    try {
      const reportData = { location, description };
      await axios.post(getAPI_URL('api/emergencies'), reportData);
      await sendLog('submit_emergency', `Location: ${location}, Description: ${description}`);
      setShowPopup(true);
      setError('');
      setLocation(resources.length > 0 ? (resources[0].name || resources[0].resourceName || '') : '');
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
            {resources.length > 0 ? (
              resources.map((resource, index) => (
                <option
                  key={resource.id || resource.resourceId || index}
                  value={resource.name || resource.resourceName}
                >
                  {resource.name || resource.resourceName || 'Unknown Resource'}
                </option>
              ))
            ) : (
              <option value="" disabled>
                No resources available
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