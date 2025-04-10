// src/pages/UserBooking.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import './UserBooking.css';

function UserBooking() {
  const [resource, setResource] = useState('');
  const [purpose, setPurpose] = useState('');
  const [date, setDate] = useState(null);
  const [time, setTime] = useState('');
  const [bookingDetails, setBookingDetails] = useState(null);
  const navigate = useNavigate();

  const handleBook = () => {
    if (resource && purpose && date && time) {
      setBookingDetails({
        resource,
        purpose,
        date: date.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' }),
        time,
      });
    } else {
      alert('Please select all options before booking.');
    }
  };

  const handleBack = () => {
    navigate('/userhome'); // Matches route
  };

  return (
    <div className="booking-container">
      <h1>Book a Resource</h1>
      <div className="form-group">
        <label>Resources</label>
        <select value={resource} onChange={(e) => setResource(e.target.value)}>
          <option value="">Select Resource</option>
          <option value="Lecture Room, 3R001">Lecture Room, 3R001</option>
          <option value="Lecture Room, 3R002">Lecture Room, 3R002</option>
          <option value="Lecture Room, 3R003">Lecture Room, 3R003</option>
          <option value="Computer Lab, 3R101">Computer Lab, 3R101</option>
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
      <button onClick={handleBack} className="back-button">Back</button>
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