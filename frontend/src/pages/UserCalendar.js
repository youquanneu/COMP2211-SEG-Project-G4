import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Calendar from 'react-calendar';
import axios from 'axios';
import 'react-calendar/dist/Calendar.css';
import logo from '../assets/logo.png';
import './UserCalendar.css';
import { getAPI_URL } from "../services/api";

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
      await axios.post(getAPI_URL('api/logs'), { action, value });
    } catch (err) {
      console.error('Log error:', err.message);
    }
  };

  // Fetch events and bookings on mount
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Fetch events
        const eventsResponse = await axios.get(getAPI_URL('user/event/getAllEvent'));
        const userEmail = localStorage.getItem('userEmail') || 'Anonymous';
        console.log('Events Response:', eventsResponse.data);
        let eventData = eventsResponse.data;
        if (!Array.isArray(eventData)) {
          if (eventData.events && Array.isArray(eventData.events)) {
            eventData = eventData.events;
          } else if (eventData.data && Array.isArray(eventData.data)) {
            eventData = eventData.data;
          } else if (eventData.result && Array.isArray(eventData.result)) {
            eventData = eventData.result;
          } else {
            setError('Invalid event data format. Expected an array.');
            console.error('Event structure:', JSON.stringify(eventsResponse.data, null, 2));
            return;
          }
        }
        setEvents(eventData);

        // Fetch bookings
        console.log('Email:', userEmail);
        const bookingsResponse = await axios.post(getAPI_URL('user/reservation/myReservation'), {
          email: userEmail,
        });
        console.log('Bookings Response:', bookingsResponse.data);
        let bookingData = bookingsResponse.data;
        if (!Array.isArray(bookingData)) {
          if (bookingData.bookings && Array.isArray(bookingData.bookings)) {
            bookingData = bookingData.bookings;
          } else if (bookingData.data && Array.isArray(bookingData.data)) {
            bookingData = bookingData.data;
          } else if (bookingData.result && Array.isArray(bookingData.result)) {
            bookingData = bookingData.result;
          } else {
            setError('Invalid booking data format. Expected an array.');
            console.error('Booking structure:', JSON.stringify(bookingsResponse.data, null, 2));
            return;
          }
        }
        setBookings(bookingData);
      } catch (err) {
        setError('Failed to load data. Check if backend is running.');
        console.error('Fetch error:', err.message, err.response?.data);
      }
    };
    fetchData();
  }, []);

  // Combine events and bookings for selected date
  const selectedDateItems = [
    ...events
      .filter((event) => {
        const eventDate = new Date(event.date);
        eventDate.setHours(0, 0, 0, 0); // Normalize event date to midnight
        const selectedDateNormalized = new Date(date);
        selectedDateNormalized.setHours(0, 0, 0, 0);
        return eventDate.getTime() === selectedDateNormalized.getTime();
      })
      .map((event) => ({ ...event, type: 'event' })),
    ...bookings
      .filter((booking) => {
        const bookingDate = new Date(booking.reservationStarting);
        bookingDate.setHours(0, 0, 0, 0); // Normalize booking date to midnight
        const selectedDateNormalized = new Date(date);
        selectedDateNormalized.setHours(0, 0, 0, 0);
        return bookingDate.getTime() === selectedDateNormalized.getTime();
      })
      .map((booking) => ({
        id: booking.reservationId,
        date: new Date(booking.reservationStarting).toISOString().split('T')[0],
        topic: `Booking: ${booking.resourceDTO?.resourceName || 'Unknown'}`,
        area: booking.purpose || 'No purpose specified', // Ensure purpose is shown, even if empty
        organizers: booking.userDTO?.email || 'User',
        description: `${new Date(booking.reservationStarting).toLocaleString()} - ${new Date(booking.reservationEnding).toLocaleString()}`, // Formatting the time
        type: 'booking',
      })),
  ];

  // Mark dates with events or bookings
  const tileContent = ({ date, view }) => {
    if (view === 'month') {
      const dateStr = date.toISOString().split('T')[0];
      const hasEvent = events.some((event) => event.date === dateStr);
      const hasBooking = bookings.some((booking) => {
        const bookingDate = new Date(booking.reservationStarting).toISOString().split('T')[0];
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
            const normalizedDate = new Date(selectedDate);
            normalizedDate.setHours(0, 0, 0, 0);
            console.log('Selected Date:', normalizedDate.toISOString().split('T')[0]); // Debug
            setDate(normalizedDate);
            sendLog('select_date', normalizedDate.toISOString().split('T')[0]);
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
