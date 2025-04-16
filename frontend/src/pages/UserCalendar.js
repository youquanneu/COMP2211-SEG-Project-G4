import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Calendar from 'react-calendar';
import axios from 'axios';
import 'react-calendar/dist/Calendar.css';
import logo from '../assets/logo.png';
import './UserCalendar.css';

function UserCalendar() {
  // Normalize date to local midnight
  const initialDate = new Date();
  initialDate.setHours(0, 0, 0, 0);
  const [date, setDate] = useState(initialDate);
  const [events, setEvents] = useState([]);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  // Send log to backend
  const sendLog = async (action, value) => {
    try {
      await axios.post('http://localhost:8080/api/logs', { action, value });
    } catch (err) {
      console.error('Log error:', err.message);
    }
  };

  // Fetch all events on mount
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

  // Filter events for selected date
  const selectedDateEvents = events.filter(
    (event) => event.date === date.toISOString().split('T')[0]
  );

  // Mark dates with event
  const tileContent = ({ date, view }) => {
    if (view === 'month') {
      const dateStr = date.toISOString().split('T')[0];
      const hasEvent = events.some((event) => event.date === dateStr);
      return hasEvent ? <span className="event-dot"></span> : null;
    }
    return null;
  };

  const handleBack = () => {
    sendLog('back', 'Clicked back');
    navigate('/userhome');
  };

  return (
    <div className="calendar-container">
      <img src={logo} alt="Logo" className="calendar-logo" />
      <h1>Your Calendar</h1>
      {error && <p className="error-message">{error}</p>}
      <div className="calendar-group">
        <Calendar
          onChange={(selectedDate) => {
            setDate(selectedDate);
            sendLog('select_date', selectedDate.toISOString().split('T')[0]);
          }}
          value={date}
          className="custom-calendar"
          tileContent={tileContent}
        />
      </div>
      {selectedDateEvents.length > 0 && (
        <div className="event-details">
          <h2>Events on {date.toLocaleDateString()}</h2>
          {selectedDateEvents.map((event) => (
            <div key={event.id} className="event-item">
              <p>
                <strong>Topic:</strong> {event.topic}
              </p>
              <p>
                <strong>Area:</strong> {event.area}
              </p>
              <p>
                <strong>Organizers:</strong> {event.organizers}
              </p>
              <p>
                <strong>Description:</strong> {event.description}
              </p>
            </div>
          ))}
        </div>
      )}
      <button className="back-button" onClick={handleBack}>
        Back
      </button>
    </div>
  );
}

export default UserCalendar;