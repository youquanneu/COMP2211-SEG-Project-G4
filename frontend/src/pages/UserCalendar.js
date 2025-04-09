import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css'; // Default styles
import logo from '../assets/logo.png';
import './UserCalendar.css';

function UserCalendar() {
  const [date, setDate] = useState(new Date()); // Default to today’s date
  const navigate = useNavigate();

  const handleBack = () => {
    navigate('/userhome'); // Back to UserHome
  };

  return (
    <div className="calendar-container">
      <img src={logo} alt="Logo" className="calendar-logo" />
      <h1>Your Calendar</h1>
      <div className="calendar-group">
        <Calendar
          onChange={setDate} // Update state when date is clicked
          value={date} // Selected date
          className="custom-calendar"
        />
      </div>
      <button className="back-button" onClick={handleBack}>
        Back
      </button>
    </div>
  );
}

export default UserCalendar;