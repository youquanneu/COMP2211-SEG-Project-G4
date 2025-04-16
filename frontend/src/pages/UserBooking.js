import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import DatePicker from 'react-datepicker';
import axios from 'axios';
import 'react-datepicker/dist/react-datepicker.css';
import './UserBooking.css';
import { getAPI_URL } from "../services/api";

function UserBooking() {
  const [resource, setResource] = useState('');
  const [purpose, setPurpose] = useState('');
  const [date, setDate] = useState(null);
  const [time, setTime] = useState('');
  const [availableTimes, setAvailableTimes] = useState([]);
  const [showDropdown, setShowDropdown] = useState(false);
  const [bookingDetails, setBookingDetails] = useState(null);
  const [error, setError] = useState('');
  const [resources, setResources] = useState([]);
  const dropdownRef = useRef(null);
  const navigate = useNavigate();

  // Send log to backend
  const sendLog = async (action, value) => {
    try {
      await axios.post(getAPI_URL('api/logs'), { action, value });
    } catch (err) {
      console.error('Log error:', err.message);
    }
  };

  // Fetch resources on mount
  useEffect(() => {
    const fetchResources = async () => {
      try {
        const response = await axios.get(getAPI_URL('user/resource/getAllResource'));
        console.log('API Response:', response.data);
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

  // Close dropdown on outside click
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setShowDropdown(false);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);
  // Fetch available time slots
  const handleSearchTimes = async () => {
    await sendLog('search_times', 'Searched times');
    if (!resource || !purpose || !date) {
      setError('Please select Resource, Purpose, and Date first.');
      return;
    }
    try {
      const formattedDate = date.toISOString().split('T')[0];
      console.log("Search the resource : " + resource + "date: " + formattedDate)
      const response = await axios.get(getAPI_URL('user/reservation/getAvailableTimeSlot'), {
        params: { resource, date: formattedDate },
      });
      if (Array.isArray(response.data)) {
        console.log(response.data)
        setAvailableTimes(response.data);
        setShowDropdown(true);
        setError('');
      } else {
        setError('Invalid time slot data format.');
        console.error('Expected an array, got:', response.data);
      }
    } catch (err) {
      setError('Failed to load available times. Please try again.');
      console.error('Fetch error:', err.message, err.response?.data);
    }
  };
  const handleSelectTime = (slot) => {
  console.log("Error reach Here")
    setTime(slot);
    setShowDropdown(false);
    sendLog('select_time', slot);
  };

  const handleBook = async () => {
    await sendLog('book', 'Submitted booking');
    if (resource && purpose && date && time) {
      const formattedDate = date.toLocaleDateString('en-US', {
        month: 'long',
        day: 'numeric',
        year: 'numeric',
      });
      const userEmail = localStorage.getItem('userEmail') || 'Anonymous';
      const bookingData = { resource, purpose, date: formattedDate, time, userEmail };
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
    sendLog('back', 'Clicked back');
    navigate('/userhome');
  };

  return (
    <div className="booking-container">
      <h1>Book a Resource</h1>
      {error && <p className="error-message">{error}</p>}
      <div className="form-group">
        <label>Resources</label>
        <select
          value={resource}
          onChange={(e) => {
            setResource(e.target.value);
            sendLog('select_resource', e.target.value || 'None');
          }}
        >
          <option value="">Select Resource</option>
          {resources.map((res) => {
            console.log('Rendering resource:', res);
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
        <select
          value={purpose}
          onChange={(e) => {
            setPurpose(e.target.value);
            sendLog('select_purpose', e.target.value || 'None');
          }}
        >
          <option value="">Select Purpose</option>
          <option value="Meeting">Meeting</option>
-dot-comma
          <option value="Presentation">Presentation</option>
          <option value="Workshop">Workshop</option>
          <option value="Study">Study</option>
        </select>
      </div>
      <div className="form-group">
        <label>Date</label>
        <DatePicker
          selected={date}
          onChange={(selectedDate) => {
            setDate(selectedDate);
            sendLog('select_date', selectedDate ? selectedDate.toISOString().split('T')[0] : 'None');
          }}
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
        <div className="time-input-wrapper" ref={dropdownRef}>
          <input
            type="text"
            value={time || 'Select Time'}
            readOnly
            className="time-input"
            onClick={handleSearchTimes}
          />
          <span className="search-icon" onClick={handleSearchTimes}>
            🔍
          </span>
          {showDropdown && availableTimes.length > 0 && (
            <ul className="time-dropdown">
              {availableTimes.map((slot) => (
                <li
                  key={slot}
                  className="time-option"
                  onClick={() => handleSelectTime(slot)}
                >
                  {slot}
                </li>
              ))}
            </ul>
          )}
        </div>
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