import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import logo from '../assets/logo.png';
import './UserEvent.css';
import { getAPI_URL } from "../services/api";

function UserEvent() {
  const [events, setEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  // Send log to backend
  const sendLog = async (action, value) => {
    try {
      await axios.post(getAPI_URL('api/logs'), { action, value });
    } catch (err) {
      console.error('Log error:', err.message);
    }
  };

  // Fetch events on component mount
  useEffect(() => {
    const fetchEvents = async () => {
      await sendLog('fetch_events', 'Fetched events');
      try {
        const response = await axios.get(getAPI_URL('user/event/getAllEvent'));
        console.log('API Response:', response.data); // Debug
        let eventData = response.data;
        if (!Array.isArray(eventData) && eventData.events) {
          eventData = eventData.events;
        }
        if (Array.isArray(eventData)) {
          setEvents(eventData);
          if (eventData.length === 0) {
            setError('No events found in the database.');
          }
        } else {
          setError('Invalid event data format. Expected an array.');
        }
      } catch (err) {
        setError('Failed to load events. Check if backend is running.');
        console.error('Fetch error:', err.message, err.response?.data);
      }
    };
    fetchEvents();
  }, []);

  const handleEventClick = (event) => {
    console.log('Selected event:', event); // Debug
    setSelectedEvent(event);
    sendLog('select_event', event.eventTitle || 'Unknown');
  };

  const closePopup = () => {
    setSelectedEvent(null);
    sendLog('close_popup', 'Closed event popup');
  };

  const handleBack = () => {
    navigate('/userhome');
    sendLog('back', 'Clicked back to userhome');
  };

  // Format date safely
  const formatDate = (dateStr) => {
    try {
      const date = new Date(dateStr);
      if (isNaN(date.getTime())) {
        return 'Invalid Date';
      }
      return date.toLocaleDateString('en-US', {
        month: 'long',
        day: 'numeric',
        year: 'numeric',
      });
    } catch {
      return 'Invalid Date';
    }
  };

  // Format organizer safely
  const formatOrganizer = (organizer) => {
    if (typeof organizer === 'object' && organizer) {
      return organizer.username || organizer.email || 'N/A';
    }
    return organizer || 'N/A';
  };

  return (
    <div className="events-container">
      <header className="events-header">
        <img src={logo} alt="Logo" className="events-logo" />
        <h1>University Events</h1>
      </header>
      <main className="events-content">
        {error && <p className="error-message">{error}</p>}
        <div className="events-list">
          {events.length > 0 ? (
            events.map((event, index) => (
              <div
                key={event.id || index}
                className="event-card"
                onClick={() => handleEventClick(event)}
              >
                <img
                  src={event.imageUrl || logo}
                  alt={event.eventTitle || 'Event'}
                  className="event-image"
                  onError={(e) => {
                    e.target.src = logo;
                  }}
                />
                <div className="event-details">
                  <h3>{event.eventTitle || 'Untitled Event'}</h3>
                  <p>
                    <strong>Area:</strong> {event.area || 'N/A'}
                  </p>
                  <p>
                    <strong>Date:</strong> {formatDate(event.date)}
                  </p>
                  <p>
                    <strong>Time:</strong> {event.time || 'N/A'}
                  </p>
                </div>
              </div>
            ))
          ) : (
            <p>No events available.</p>
          )}
        </div>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
      </main>

      {selectedEvent && (
        <div className="event-popup" onClick={closePopup}>
          <div className="popup-content" onClick={(e) => e.stopPropagation()}>
            <button className="close-button" onClick={closePopup}>
              ×
            </button>
            <img
              src={selectedEvent.imageUrl || logo}
              alt={selectedEvent.topic || 'Event'}
              className="popup-image"
              onError={(e) => {
                e.target.src = logo;
              }}
            />
            <h2>{selectedEvent.topic || 'Untitled Event'}</h2>
            <p>
              <strong>Area:</strong> {selectedEvent.area || 'N/A'}
            </p>
            <p>
              <strong>Date:</strong> {formatDate(selectedEvent.date)}
            </p>
            <p>
              <strong>Time:</strong> {selectedEvent.time || 'N/A'}
            </p>
            <p>
              <strong>Venue:</strong> {selectedEvent.venue || 'N/A'}
            </p>
            <p>
              <strong>Organizer:</strong> {formatOrganizer(selectedEvent.organizer)}
            </p>
            <p>
              <strong>Description:</strong> {selectedEvent.description || 'No description available.'}
            </p>
          </div>
        </div>
      )}
    </div>
  );
}

export default UserEvent;