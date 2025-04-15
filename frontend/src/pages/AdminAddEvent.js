// src/pages/AdminAddEvent.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import './AdminAddEvent.css';

function AdminAddEvent() {
  const navigate = useNavigate();
  
  // State for form fields
  const [formData, setFormData] = useState({
    image: null,
    title: '',
    area: '',
    date: '',
    time: '',
    venue: '',
    organizer: '',
    description: '',
  });
  
  // State for success message
  const [showSuccess, setShowSuccess] = useState(false);
  
  // State for stored events (mock data)
  const [events, setEvents] = useState([]);

  // Handle input changes
  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: files ? files[0] : value, // Handle file input for image
    }));
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    
    // Simulate adding the event (in a real app, this would be a backend API call)
    const newEvent = {
      id: events.length + 1,
      ...formData,
      image: formData.image ? URL.createObjectURL(formData.image) : null, // Create a URL for the image preview
    };
    
    setEvents((prev) => [...prev, newEvent]);
    
    // Show success message
    setShowSuccess(true);
    setTimeout(() => setShowSuccess(false), 2000);
    
    // Clear the form
    setFormData({
      image: null,
      title: '',
      area: '',
      date: '',
      time: '',
      venue: '',
      organizer: '',
      description: '',
    });
    
    // Log the event (placeholder for backend integration)
    console.log('Event added:', newEvent);
  };

  // Handle back button
  const handleBack = () => {
    navigate('/admindashboard');
  };

  return (
    <div className="admin-add-event-container">
      <header className="admin-add-event-header">
        <img src={logo} alt="Logo" className="admin-add-event-logo" />
      </header>
      <main className="admin-add-event-content">
        <h1>Add Event</h1>
        {showSuccess && (
          <div className="success-message">
            Event added successfully!
          </div>
        )}
        <form onSubmit={handleSubmit} className="add-event-form">
          <div className="form-group">
            <label htmlFor="image">Image:</label>
            <input
              type="file"
              id="image"
              name="image"
              accept="image/*"
              onChange={handleChange}
            />
          </div>
          <div className="form-group">
            <label htmlFor="title">Title:</label>
            <input
              type="text"
              id="title"
              name="title"
              value={formData.title}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="area">Area:</label>
            <input
              type="text"
              id="area"
              name="area"
              value={formData.area}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="date">Date:</label>
            <input
              type="date"
              id="date"
              name="date"
              value={formData.date}
              onChange={handleChange}
              min="2025-04-15" // Prevent past dates (current date is 2025-04-15)
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="time">Time:</label>
            <input
              type="time"
              id="time"
              name="time"
              value={formData.time}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="venue">Venue:</label>
            <input
              type="text"
              id="venue"
              name="venue"
              value={formData.venue}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="organizer">Organizer:</label>
            <input
              type="text"
              id="organizer"
              name="organizer"
              value={formData.organizer}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="description">Description:</label>
            <textarea
              id="description"
              name="description"
              value={formData.description}
              onChange={handleChange}
              rows="4"
              required
            />
          </div>
          <button type="submit" className="submit-button">
            Add Event
          </button>
        </form>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
      </main>
    </div>
  );
}

export default AdminAddEvent;