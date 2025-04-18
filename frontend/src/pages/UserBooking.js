import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import DatePicker from 'react-datepicker';
import axios from 'axios';
import 'react-datepicker/dist/react-datepicker.css';
import './UserBooking.css';
import { getAPI_URL } from "../services/api";

function UserBooking() {
  const [resource, setResource] = useState(null);
  const [purpose, setPurpose] = useState('');
  const [date, setDate] = useState(null);
  const [time, setTime] = useState(null);
  const [availableTimes, setAvailableTimes] = useState([]);
  const [showDropdown, setShowDropdown] = useState(false);
  const [bookingDetails, setBookingDetails] = useState(null);
  const [error, setError] = useState('');
  const [resources, setResources] = useState([]);
  const dropdownRef = useRef(null);
  const navigate = useNavigate();

  const sendLog = async (action, value) => {
    try {
      await axios.post(getAPI_URL('api/logs'), { action, value });
    } catch (err) {
      console.error('Log error:', err.message);
    }
  };

  useEffect(() => {
    const fetchResources = async () => {
      try {
        const response = await axios.get(getAPI_URL('user/resource/getAllResource'));
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

  const handleSearchTimes = async () => {
    await sendLog('search_times', 'Searched times');
    if (!resource || !purpose || !date) {
      setError('Please select Resource, Purpose, and Date first.');
      return;
    }
    try {
      const formattedDate = date.toISOString().split('T')[0];
      const response = await axios.post(getAPI_URL('user/reservation/getAvailableTimeSlot'), {
        resourceDTO: resource,
        formattedDate
      });
      if (Array.isArray(response.data)) {
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
    setTime(slot);
    setShowDropdown(false);
    sendLog('select_time', `${slot.startingTime}-${slot.endingTime}`);
  };

  const handleBook = async () => {
    await sendLog('book', 'Submitted booking');
    if (resource && purpose && date && time) {
      const formattedDate = date.toISOString().split('T')[0];
      const userEmail = localStorage.getItem('userEmail') || 'Anonymous';

      const bookingData = {
        userEmail,
        resourceDTO: resource,
        purpose,
        reservationDate: formattedDate,
        timeSlotDTO: time
      };

      try {
        const response = await axios.post(getAPI_URL('user/reservation/makeReservation'), bookingData);
        const fullBookingDetails = {
          ...bookingData,          // Keep what we already know
          ...response.data         // Override/add anything returned from backend
        };
        setBookingDetails(fullBookingDetails);
        console.log('Full Booking Details:', fullBookingDetails);
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
          value={resource ? resource.resourceName : ""}
          onChange={(e) => {
            const selectedResource = resources.find((res) => res.resourceName === e.target.value);
            setResource(selectedResource || null);
            sendLog('select_resource', selectedResource ? selectedResource.resourceName : 'None');
          }}
        >
          <option value="">Select Resource</option>
          {resources.map((res) => (
            <option key={res.resourceId} value={res.resourceName}>
              {res.resourceName || `Resource ID ${res.resourceId}`}
            </option>
          ))}
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
            value={time ? `${time.startingTime} - ${time.endingTime}` : 'Select Time'}
            readOnly
            className="time-input"
            onClick={handleSearchTimes}
          />
          <span className="search-icon" onClick={handleSearchTimes}>
            🔍
          </span>
          {showDropdown && availableTimes.length > 0 && (
            <ul className="time-dropdown">
              {availableTimes.map((slot, index) => (
                <li
                  key={index}
                  className="time-option"
                  onClick={() => handleSelectTime(slot)}
                >
                  {`${slot.startingTime} - ${slot.endingTime}`}
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
          <p>Resource: {bookingDetails.resourceDTO?.resourceName}</p>
          <p>Purpose: {bookingDetails.purpose}</p>
          <p>Date: {bookingDetails.reservationDate}</p>
          <p>Time: {bookingDetails.timeSlotDTO?.startingTime} - {bookingDetails.timeSlotDTO?.endingTime}</p>
        </div>
      )}
    </div>
  );
}

export default UserBooking;
