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
        // console.log('Events Response:', JSON.stringify(eventsResponse.data, null, 2)); // Commented out for production
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
        // Filter and normalize valid events
        eventData = eventData
          .filter(event => 
            event.eventId && 
            event.eventTitle && 
            event.eventStarting && 
            event.eventEnding
          )
          .map(event => ({
            ...event,
            date: new Date(event.eventStarting).toISOString().split('T')[0]
          }));
        setEvents(eventData);
        // console.log('Valid events:', eventData); // Commented out for production

        // Fetch bookings
        // console.log('Email:', userEmail); // Commented out for production
        const bookingsResponse = await axios.post(getAPI_URL('user/reservation/myReservation'), {
          email: userEmail,
        });
        console.log('Bookings Response:', JSON.stringify(bookingsResponse.data, null, 2));
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
        // Filter valid bookings
        bookingData = bookingData.filter(booking => 
          booking.reservationId && 
          booking.reservationStarting && 
          booking.reservationEnding
        );
        
        // Log individual bookings to debug purpose field
        bookingData.forEach((booking, index) => {
          console.log(`Booking ${index}:`, booking);
          console.log(`Purpose for booking ${index}:`, booking.purpose);
        });
        
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
        if (!event.eventStarting) return false;
        const eventDate = new Date(event.eventStarting);
        const normalizedEventDate = new Date(eventDate.getFullYear(), eventDate.getMonth(), eventDate.getDate());
        const selectedDateNormalized = new Date(date.getFullYear(), date.getMonth(), date.getDate());
        // console.log('Event filter:', normalizedEventDate.toISOString().split('T')[0], selectedDateNormalized.toISOString().split('T')[0], 'Title:', event.eventTitle); // Commented out for production
        return normalizedEventDate.getTime() === selectedDateNormalized.getTime();
      })
      .map((event) => ({
        id: event.eventId,
        date: new Date(event.eventStarting).toISOString().split('T')[0],
        topic: event.eventTitle || 'Untitled Event',
        area: event.area || 'N/A',
        organizers: event.organizer?.map((org) => org.username || org.email || 'Unknown').join(', ') || 'N/A',
        description: `${new Date(event.eventStarting).toLocaleString()} - ${new Date(event.eventEnding).toLocaleString()}`,
        type: 'event',
      })),
    ...bookings
      .filter((booking) => {
        if (!booking.reservationStarting) return false;
        const bookingDate = new Date(booking.reservationStarting);
        const normalizedBookingDate = new Date(bookingDate.getFullYear(), bookingDate.getMonth(), bookingDate.getDate());
        const selectedDateNormalized = new Date(date.getFullYear(), date.getMonth(), date.getDate());
        // Fixed: Use normalizedBookingDate instead of normalizedEventDate
        return normalizedBookingDate.getTime() === selectedDateNormalized.getTime();
      })
      .map((booking) => {
        console.log('Mapping booking with ID:', booking.reservationId);
        console.log('Purpose value:', booking.purpose);
        
        return {
          id: booking.reservationId,
          date: new Date(booking.reservationStarting).toISOString().split('T')[0],
          topic: `Booking: ${booking.resourceDTO?.resourceName || 'Unknown'}`,
          // Format the purpose from the purpose enum
          purpose: booking.purpose || 'No purpose specified',
          organizers: booking.userDTO?.email || 'User',
          description: `${new Date(booking.reservationStarting).toLocaleString()} - ${new Date(booking.reservationEnding).toLocaleString()}`,
          type: 'booking',
        };
      }),
  ];

  // Mark dates with events or bookings
  const tileContent = ({ date, view }) => {
    if (view === 'month') {
      const normalizedTileDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
      const dateStr = normalizedTileDate.toISOString().split('T')[0];
      // console.log('Checking tile for date:', dateStr); // Commented out for production

      const hasEvent = events.some((event) => {
        if (!event.eventStarting) return false;
        const eventDate = new Date(event.eventStarting);
        const normalizedEventDate = new Date(eventDate.getFullYear(), eventDate.getMonth(), eventDate.getDate());
        // console.log('Event date:', normalizedEventDate.toISOString().split('T')[0], 'Title:', event.eventTitle); // Commented out for production
        return normalizedEventDate.toISOString().split('T')[0] === dateStr;
      });

      const hasBooking = bookings.some((booking) => {
        if (!booking.reservationStarting) return false;
        const bookingDate = new Date(booking.reservationStarting);
        const normalizedBookingDate = new Date(bookingDate.getFullYear(), bookingDate.getMonth(), bookingDate.getDate());
        // console.log('Booking date:', normalizedBookingDate.toISOString().split('T')[0], 'Original:', booking.reservationStarting, 'Resource:', booking.resourceDTO?.resourceName); // Commented out for production
        return normalizedBookingDate.toISOString().split('T')[0] === dateStr;
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
            const normalizedDate = new Date(selectedDate.getFullYear(), selectedDate.getMonth(), selectedDate.getDate());
            // console.log('Selected Date:', normalizedDate.toISOString().split('T')[0]); // Commented out for production
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
              {item.type === 'booking' && (
                <p>
                  <strong>Purpose:</strong> {item.purpose}
                </p>
              )}
              {item.type === 'event' && (
                <p>
                  <strong>Area/Purpose:</strong> {item.area}
                </p>
              )}
              <p>
                <strong>User/Organizers:</strong> {item.organizers}
              </p>
              <p>
                <strong>Time:</strong> {item.description}
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