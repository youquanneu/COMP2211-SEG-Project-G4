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
  const [bookings, setBookings] = useState([]);
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

  // Fetch events and bookings on mount
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Fetch events
        const eventsResponse = await axios.get('http://localhost:8080/api/events');
        if (Array.isArray(eventsResponse.data)) {
          setEvents(eventsResponse.data);
        } else {
          setError('Invalid event data format.');
          console.error('Expected an array, got:', eventsResponse.data);
        }

        // Fetch bookings
        const userEmail = localStorage.getItem('userEmail') || 'Anonymous';
        const bookingsResponse = await axios.get('http://localhost:8080/api/bookings', {
          params: { email: userEmail },
        });
        if (Array.isArray(bookingsResponse.data)) {
          setBookings(bookingsResponse.data);
        } else {
          setError('Invalid booking data format.');
          console.error('Expected an array, got:', bookingsResponse.data);
        }
      } catch (err) {
        setError('Failed to load data. Please try again later.');
        console.error('Fetch error:', err.message, err.response?.data);
      }
    };
    fetchData();
  }, []);

  // Combine events and bookings for selected date
  const selectedDateItems = [
    ...events
      .filter((event) => event.date === date.toISOString().split('T')[0])
      .map((event) => ({ ...event, type: 'event' })),
    ...bookings
      .filter((booking) => {
        // Convert booking date (e.g., "April 15, 2025") to ISO format
        const bookingDate = new Date(booking.date).toISOString().split('T')[0];
        return bookingDate === date.toISOString().split('T')[0];
      })
      .map((booking) => ({
        id: booking.id,
        date: new Date(booking.date).toISOString().split('T')[0],
        topic: `Booking: ${booking.resource}`,
        area: booking.purpose,
        organizers: booking.userEmail || 'User',
        description: booking.time,
        type: 'booking',
      })),
  ];

  // Mark dates with events or bookings
  const tileContent = ({ date, view }) => {
    if (view === 'month') {
      const dateStr = date.toISOString().split('T')[0];
      const hasEvent = events.some((event) => event.date === dateStr);
      const hasBooking = bookings.some((booking) => {
        const bookingDate = new Date(booking.date).toISOString().split('T')[0];
        return bookingDate === dateStr;
      });
      return hasEvent || hasBooking ? <span className="event-dot"></span> : null;
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
      {selectedDateItems.length > 0 && (
        <div className="event-details">
          <h2>Events and Bookings on {date.toLocaleDateString()}</h2>
          {selectedDateItems.map((item) => (
            <div
              key={`${item.type}-${item.id}`}
              className="event-item"
              onClick={() => {
                if (item.type === 'booking') {
                  sendLog('select_booking', `Booking: ${item.topic}, ${item.description}`);
                } else {
                  sendLog('select_event', `Event: ${item.topic}`);
                }
              }}
            >
              <p>
                <strong>{item.type === 'booking' ? 'Booking' : 'Event'}:</strong> {item.topic}
              </p>
              <p>
                <strong>Purpose/Area:</strong> {item.area}
              </p>
              <p>
                <strong>User/Organizers:</strong> {item.organizers}
              </p>
              <p>
                <strong>Time/Description:</strong> {item.description}
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