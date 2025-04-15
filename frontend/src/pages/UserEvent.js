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

  // Fetch events from backend on mount
  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/events');
        if (Array.isArray(response.data)) {
          setEvents(response.data);
        } else {
          setError('Invalid event data format.');
          console.error('Expected an array, got:', response.data);
        }
      } catch (err) {
        setError('Failed to load events. Please try again later.');
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
    navigate('/userhome');
  };

  const handleAddToCalendar = (event) => {
    const eventToSave = { ...event };
    console.log('Saving event date:', eventToSave.date); // Debug
    const storedEvents = JSON.parse(localStorage.getItem('calendarEvents')) || [];
    const exists = storedEvents.some((e) => e.id === event.id);
    if (!exists) {
      storedEvents.push(eventToSave);
      localStorage.setItem('calendarEvents', JSON.stringify(storedEvents));
      alert(`${event.topic} added to your calendar!`);
    } else {
      alert(`${event.topic} is already in your calendar.`);
    }
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
              <div key={event.id} className="event-card" onClick={() => handleEventClick(event)}>
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
                    <strong>Date:</strong>{' '}
                    {event.date
                      ? new Date(event.date).toLocaleDateString()
                      : 'Date not available'}
                  </p>
                  <p>
                    <strong>Area:</strong> {event.area || 'N/A'}
                  </p>
                  <button
                    className="add-to-calendar-button"
                    onClick={(e) => {
                      e.stopPropagation();
                      handleAddToCalendar(event);
                    }}
                  >
                    Add to Calendar
                  </button>
                </div>
              </div>
            ))
          ) : (
            <p>No events available.</p>
          )}
        </div>
      </main>
      <button className="back-button" onClick={handleBack}>
        Back
      </button>
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
              <strong>Date:</strong>{' '}
              {selectedEvent.date
                ? new Date(selectedEvent.date).toLocaleDateString()
                : 'Date not available'}
            </p>
            <p>
              <strong>Area:</strong> {selectedEvent.area || 'N/A'}
            </p>
            <p>
              <strong>Organizers:</strong> {selectedEvent.organizers || 'N/A'}
            </p>
            <p>
              <strong>Description:</strong> {selectedEvent.description || 'No description'}
            </p>
          </div>
        </div>
      )}
    </div>
  );
}

export default UserEvent;