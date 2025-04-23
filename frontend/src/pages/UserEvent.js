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

  // Toggle for mock data (set to false to use API)
  const useMockData = false;

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
      if (useMockData) {
        const mockEvents = [
          {
            eventId: 1,
            eventTitle: 'AI Seminar',
            area: 'Lecture Hall 1',
            eventStarting: '2025-04-15T09:00:00',
            eventEnding: '2025-04-15T10:00:00',
            venues: [{ venueName: 'Room A' }],
            organizer: [{ userId: 1, username: 'john', email: 'john@example.com', userRole: 'student' }],
            eventDescription: 'Learn about AI advancements.',
            imageUrl: 'http://example.com/image.jpg',
          },
        ];
        setEvents(mockEvents);
        await sendLog('fetch_events', 'Fetched mock events');
        return;
      }

      try {
        setError(''); // Clear any previous errors
        await sendLog('fetch_events', 'Fetching events from API');
        
        // First check if events need to be initialized
        try {
          console.log('Checking if events need initialization...');
          const initResponse = await axios.get(getAPI_URL('setup/reinitialize'));
          console.log('Init response:', initResponse.data);
        } catch (initErr) {
          console.log('Event initialization not available or failed:', initErr.message);
        }
        
        // Now fetch events
        const response = await axios.get(getAPI_URL('user/event/getAllEvent'));
        console.log('API Response:', response.data);

        let eventData = response.data;

        // Handle various response structures
        if (!Array.isArray(eventData)) {
          if (eventData.events && Array.isArray(eventData.events)) {
            eventData = eventData.events;
          } else if (eventData.data && Array.isArray(eventData.data)) {
            eventData = eventData.data;
          } else if (eventData.result && Array.isArray(eventData.result)) {
            eventData = eventData.result;
          } else {
            console.error('Unexpected response format:', eventData);
            setError('Invalid event data format. Expected an array.');
            return;
          }
        }

        // Check if we have valid event data
        if (eventData && eventData.length > 0) {
          setEvents(eventData);
          console.log('Successfully loaded', eventData.length, 'events');
        } else {
          console.warn('No events available in response');
          setError('No events available. Please try again later.');
        }

      } catch (err) {
        console.error('Fetch error details:', err);
        if (err.response) {
          // The request was made and the server responded with a status code outside the range of 2xx
          console.error('Error response:', err.response.data);
          console.error('Error status:', err.response.status);
          setError(`Server error: ${err.response.status} - ${err.response.data.message || err.response.data || 'Unknown error'}`);
        } else if (err.request) {
          // The request was made but no response was received
          console.error('No response received');
          setError('Could not connect to server. Check if backend is running.');
        } else {
          // Something happened in setting up the request
          console.error('Error message:', err.message);
          setError(`Error: ${err.message}`);
        }
      }
    };
    fetchEvents();
  }, []);

  const handleEventClick = (event) => {
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

  // Format date and time
  const formatDateTime = (dateStr) => {
    try {
      const date = new Date(dateStr);
      if (isNaN(date.getTime())) {
        return 'Invalid Date';
      }
      return date.toLocaleString('en-US', {
        month: 'long',
        day: 'numeric',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
      });
    } catch {
      return 'Invalid Date';
    }
  };

  // Generate Google Calendar link
  const generateCalendarLink = (event) => {
    const startTime = new Date(event.eventStarting).toISOString();
    const endTime = new Date(event.eventEnding).toISOString();
    const calendarLink = `https://www.google.com/calendar/render?action=TEMPLATE&text=${encodeURIComponent(event.eventTitle)}&dates=${startTime.replace(/[-:]/g, '')}/${endTime.replace(/[-:]/g, '')}&details=${encodeURIComponent(event.eventDescription)}&location=${encodeURIComponent(event.area)}&sf=true&output=xml`;

    return calendarLink;
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
                key={event.eventId || event.id || index}
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
                    <strong>Start:</strong> {formatDateTime(event.eventStarting)}
                  </p>
                  <p>
                    <strong>End:</strong> {formatDateTime(event.eventEnding)}
                  </p>
                  <a href={generateCalendarLink(event)} target="_blank" rel="noopener noreferrer">
                    <button className="add-to-calendar-btn">Add to Calendar</button>
                  </a>
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
              alt={selectedEvent.eventTitle || 'Event'}
              className="popup-image"
              onError={(e) => {
                e.target.src = logo;
              }}
            />
            <h2>{selectedEvent.eventTitle || 'Untitled Event'}</h2>
            <p>
              <strong>Area:</strong> {selectedEvent.area || 'N/A'}
            </p>
            <p>
              <strong>Start:</strong> {formatDateTime(selectedEvent.eventStarting)}
            </p>
            <p>
              <strong>End:</strong> {formatDateTime(selectedEvent.eventEnding)}
            </p>
            <div>
              <strong>Venues:</strong>
              {selectedEvent.venues && selectedEvent.venues.length > 0 ? (
                <ul>
                  {selectedEvent.venues.map((venue, index) => (
                    <li key={index}>{venue.venueName || venue.name || 'Unnamed Venue'}</li>
                  ))}
                </ul>
              ) : (
                ' N/A'
              )}
            </div>
            <div>
              <strong>Organizers:</strong>
              {selectedEvent.organizer && selectedEvent.organizer.length > 0 ? (
                <ul>
                  {selectedEvent.organizer.map((org, index) => (
                    <li key={index}>{org.username || org.name || org.email || 'Unnamed Organizer'}</li>
                  ))}
                </ul>
              ) : (
                ' N/A'
              )}
            </div>
            <p>
              <strong>Description:</strong> {selectedEvent.eventDescription || 'No description available.'}
            </p>
            <a href={generateCalendarLink(selectedEvent)} target="_blank" rel="noopener noreferrer">
              <button className="add-to-calendar-btn">Add to Calendar</button>
            </a>
          </div>
        </div>
      )}
    </div>
  );
}

export default UserEvent;
