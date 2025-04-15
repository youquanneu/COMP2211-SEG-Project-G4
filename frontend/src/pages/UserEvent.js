import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import logo from '../assets/logo.png';
import './UserEvent.css';

function UserEvent() {
  const [events, setEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  // Fetch events on component mount
  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/events');
        console.log('API Response:', response.data); // Debug
        if (Array.isArray(response.data)) {
          setEvents(response.data);
        } else {
          setError('Invalid event data format.');
        }
      } catch (err) {
        setError('Failed to load events. Check if backend is running.');
        console.error('Fetch error:', err.message, err.response?.data);
      }
    };
    fetchEvents();
  }, []);

  const handleEventClick = (event) => {
    setSelectedEvent(event);
  };

  const closePopup = () => {
    setSelectedEvent(null);
  };

  const handleBack = () => {
    navigate('/');
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
            events.map((event) => (
              <div
                key={event.id}
                className="event-card"
                onClick={() => handleEventClick(event)}
              >
                <img
                  src={event.imageUrl || logo}
                  alt={event.topic}
                  className="event-image"
                  onError={(e) => {
                    e.target.src = logo;
                  }}
                />
                <div className="event-details">
                  <h3>{event.topic}</h3>
                  <p>
                    <strong>Area:</strong> {event.area}
                  </p>
                  <p>
                    <strong>Date:</strong>{' '}
                    {new Date(event.date).toLocaleDateString()}
                  </p>
                  <p>
                    <strong>Time:</strong> {event.time}
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
              alt={selectedEvent.topic}
              className="popup-image"
              onError={(e) => {
                e.target.src = logo;
              }}
            />
            <h2>{selectedEvent.topic}</h2>
            <p>
              <strong>Area:</strong> {selectedEvent.area}
            </p>
            <p>
              <strong>Date:</strong>{' '}
              {new Date(selectedEvent.date).toLocaleDateString()}
            </p>
            <p>
              <strong>Time:</strong> {selectedEvent.time}
            </p>
            <p>
              <strong>Venue:</strong> {selectedEvent.venue}
            </p>
            <p>
              <strong>Organizer:</strong> {selectedEvent.organizer}
            </p>
            <p>
              <strong>Description:</strong> {selectedEvent.description}
            </p>
          </div>
        </div>
      )}
    </div>
  );
}

export default UserEvent;