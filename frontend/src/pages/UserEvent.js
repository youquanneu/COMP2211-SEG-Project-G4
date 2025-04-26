import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import logo from '../assets/logo.png';
import './UserEvent.css';
import { getAPI_URL } from '../services/api';

function UserEvent() {
  const [events, setEvents] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [error, setError] = useState('');
  const [showSuccessPopup, setShowSuccessPopup] = useState(false);
  const navigate = useNavigate();
  const userEmail = localStorage.getItem('userEmail');
  const useMockData = false;

  const sendLog = async (action, value) => {
    try {
      await axios.post(getAPI_URL('api/logs'), { action, value });
    } catch (err) {
      console.error('Log error:', err.message);
    }
  };

  // Clean up malformed events in localStorage
  const cleanLocalStorage = () => {
    const storedEvents = JSON.parse(localStorage.getItem('calendarEvents')) || [];
    const validEvents = storedEvents.filter(event => 
      event.eventId && 
      event.date && 
      event.topic && 
      event.eventStarting && 
      event.eventEnding
    );
    localStorage.setItem('calendarEvents', JSON.stringify(validEvents));
    console.log('Cleaned localStorage, retained:', validEvents.length, 'valid events');
  };

  useEffect(() => {
    cleanLocalStorage(); // Run cleanup on mount
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
        setError('');
        await sendLog('fetch_events', 'Fetching events from API');
        try {
          console.log('Checking if events need initialization...');
          const initResponse = await axios.get(getAPI_URL('setup/reinitialize'));
          console.log('Init response:', initResponse.data);
        } catch (initErr) {
          console.log('Event initialization not available or failed:', initErr.message);
        }
        const response = await axios.get(getAPI_URL('user/event/getAllEvent'));
        console.log('API Response:', response.data);

        let eventData = response.data;
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

        // Filter out invalid events
        eventData = eventData.filter(event => 
          event.eventId && 
          event.eventTitle && 
          event.eventStarting && 
          event.eventEnding
        );

        if (eventData && eventData.length > 0) {
          setEvents(eventData);
          console.log('Successfully loaded', eventData.length, 'valid events');
        } else {
          console.warn('No valid events available in response');
          setError('No valid events available. Please try again later.');
        }
      } catch (err) {
        console.error('Fetch error details:', err);
        if (err.response) {
          console.error('Error response:', err.response.data);
          console.error('Error status:', err.response.status);
          setError(`Server error: ${err.response.status} - ${err.response.data.message || err.response.data || 'Unknown error'}`);
        } else if (err.request) {
          console.error('No response received');
          setError('Could not connect to server. Check if backend is running.');
        } else {
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

  const handleAddToCalendar = async (event) => {
    if (!event.eventId || !event.eventStarting || !event.eventTitle) {
      console.error('Invalid event data for calendar:', event);
      return;
    }
    const storedEvents = JSON.parse(localStorage.getItem('calendarEvents')) || [];
    const eventExists = storedEvents.some((e) => e.eventId === event.eventId);
    console.log(event)
    console.log(userEmail)
    try{
        const response = await axios.post(getAPI_URL('user/event/registerForEvent'),{email:userEmail, eventDTO: event})
        console.log(response.data)
        setShowSuccessPopup(true);
        sendLog('add_to_calendar', `Added ${event.eventTitle} to calendar`);
        setTimeout(() => setShowSuccessPopup(false), 2000);
    }catch (err){
        console.error(err);
        const errorMessage = err.response?.data || 'Failed to register for event';
        // Alert generated
        alert(`Error: ${errorMessage}`);
        // Here need to popup error message
    }
//    if (!eventExists) {
//      const eventDate = new Date(event.eventStarting);
//      const normalizedDate = new Date(eventDate.getFullYear(), eventDate.getMonth(), eventDate.getDate())
//        .toISOString()
//        .split('T')[0];
//
//      console.log('Adding event with date:', normalizedDate, 'Original:', event.eventStarting);
//      const eventToSave = {
//        eventId: event.eventId,
//        date: normalizedDate,
//        topic: event.eventTitle || 'Untitled Event',
//        area: event.area || 'N/A',
//        organizers: event.organizer?.map((org) => org.username || org.email || 'Unknown').join(', ') || 'N/A',
//        description: `${new Date(event.eventStarting).toLocaleString()} - ${new Date(event.eventEnding).toLocaleString()}`,
//        eventStarting: event.eventStarting,
//        eventEnding: event.eventEnding,
//        type: 'event',
//      };
//      storedEvents.push(eventToSave);
//      localStorage.setItem('calendarEvents', JSON.stringify(storedEvents));
//    }
  };

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
                    <li key={index}>{venue.resourceName || venue.name || '3R002'}</li>
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
            <button
              className="add-to-calendar-button"
              onClick={() => handleAddToCalendar(selectedEvent)}
            >
              Add to Calendar
            </button>
          </div>
        </div>
      )}

      {showSuccessPopup && (
        <div className="success-popup">
          <div className="success-popup-content">
            <p>Event successfully added to the calendar.</p>
            <button onClick={() => setShowSuccessPopup(false)}>Close</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default UserEvent;