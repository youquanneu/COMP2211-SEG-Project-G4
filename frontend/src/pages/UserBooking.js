import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import DatePicker from 'react-datepicker';
import axios from 'axios';
import 'react-datepicker/dist/react-datepicker.css';
import './UserBooking.css';

function UserBooking() {
  const [resource, setResource] = useState('');
  const [purpose, setPurpose] = useState('');
  const [date, setDate] = useState(null);
  const [time, setTime] = useState('');
  const [bookingDetails, setBookingDetails] = useState(null);
  const [error, setError] = useState('');
  const [resources, setResources] = useState([]); // Store fetched resources
  const navigate = useNavigate();

  // Fetch resources on mount
  useEffect(() => {
    const fetchResources = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/resources');
        console.log('API Response:', response.data); // Debug: Inspect the data
        // Ensure response.data is an array
        if (Array.isArray(response.data)) {
          setResources(response.data);
        } else {
          setError('Invalid resource data format.');
          console.error('Expected an array, got:', response.data);
        }
      } catch (err) {
        setError('Failed to load resources. Check if backend is running.');
        console.error('Fetch error:', err.message, err.response?.data);
      }
    };
    fetchResources();
  }, []);

  const handleBook = async () => {
    if (resource && purpose && date && time) {
      const formattedDate = date.toLocaleDateString('en-US', {
        month: 'long',
        day: 'numeric',
        year: 'numeric',
      });
      const bookingData = { resource, purpose, date: formattedDate, time };

      try {
        const response = await axios.post('http://localhost:8080/api/bookings', bookingData);
        setBookingDetails(response.data);
        setError('');
      } catch (err) {
        setError('Failed to save booking. Please try again.');
        console.error('Booking error:', err.message, err.response?.data);
      }
    } else {
      setError('Please select all options before booking.');
    }
  };

  const handleBack = () => {
    navigate('/userhome');
  };

  return (
    <div className="booking-container">
      <h1>Book a Resource</h1>
      {error && <p className="error-message">{error}</p>}
      <div className="form-group">
        <label>Resources</label>
        <select value={resource} onChange={(e) => setResource(e.target.value)}>
          <option value="">Select Resource</option>
          {resources.map((res) => {
            // Debug: Log each resource object
            console.log('Rendering resource:', res);
            // Try multiple possible property names with fallback
            const resourceName =
              res.name ||
              res.resourceName ||
              res.title ||
              `Resource ID ${res.id}` ||
              'Unknown Resource';
            return (
              <option key={res.id} value={resourceName}>
                {resourceName}
              </option>
            );
          })}
        </select>
      </div>
      <div className="form-group">
        <label>Purpose</label>
        <select value={purpose} onChange={(e) => setPurpose(e.target.value)}>
          <option value="">Select Purpose</option>
          <option value="Meeting">Meeting</option>
          <option value="Presentation">Presentation</option>
          <option value="Workshop">Workshop</option>
          <option value="Study">Study</option>
        </select>
      </div>
      <div className="form-group">
        <label>Date</label>
        <DatePicker
          selected={date}
          onChange={(selectedDate) => setDate(selectedDate)}
          dateFormat="MMMM d, yyyy"
          placeholderText="Select a date"
          minDate={new Date()}
          showMonthDropdown
          showYearDropdown
          dropdownMode="select"
          className="date-picker"
        />
      </div>
      <div className="form-group">
        <label>Time</label>
        <select value={time} onChange={(e) => setTime(e.target.value)}>
          <option value="">Select Time</option>
          <option value="09:00-10:00">09:00 - 10:00</option>
          <option value="10:00-11:00">10:00 - 11:00</option>
          <option value="13:00-14:00">13:00 - 14:00</option>
        </select>
      </div>
      <button onClick={handleBook}>Book</button>
      <button onClick={handleBack} className="back-button">
        Back
      </button>
      {bookingDetails && (
        <div className="booking-details">
          <h2>Your Booking</h2>
          <p>Resource: {bookingDetails.resource}</p>
          <p>Purpose: {bookingDetails.purpose}</p>
          <p>Date: {bookingDetails.date}</p>
          <p>Time: {bookingDetails.time}</p>
        </div>
      )}
    </div>
  );
}

export default UserBooking;