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

  // Fetch user-specific events on mount
  useEffect(() => {
    const fetchUserEvents = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          setError('Please log in to view your calendar.');
          return;
        }

        const response = await axios.get('http://localhost:8080/api/users/me/events', {
          headers: { Authorization: `Bearer ${token}` },
        });

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
    fetchUserEvents();
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
    navigate('/userhome');
  };

  return (
    <div className="calendar-container">
      <img src={logo} alt="Logo" className="calendar-logo" />
      <h1>Your Calendar</h1>
      {error && <p className="error-message">{error}</p>}
      <div className="calendar-group">
        <Calendar
          onChange={setDate}
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