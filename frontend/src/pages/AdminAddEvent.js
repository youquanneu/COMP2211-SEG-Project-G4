import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import { addEvent } from '../api'; // Import the addEvent function
import './AdminAddEvent.css';

function AdminAddEvent() {
  const navigate = useNavigate();
  
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
  
  const [showSuccess, setShowSuccess] = useState(false);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: files ? files[0] : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    const formDataToSend = new FormData();
    formDataToSend.append('image', formData.image);
    formDataToSend.append('title', formData.title);
    formDataToSend.append('area', formData.area);
    formDataToSend.append('date', formData.date);
    formDataToSend.append('time', formData.time);
    formDataToSend.append('venue', formData.venue);
    formDataToSend.append('organizer', formData.organizer);
    formDataToSend.append('description', formData.description);

    try {
      const response = await addEvent(formDataToSend);
      if (response.success) {
        setShowSuccess(true);
        setTimeout(() => setShowSuccess(false), 2000);

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
      } else {
        setError('Failed to add event. Please try again.');
      }
    } catch (err) {
      setError(err.response?.data?.error || 'An error occurred while adding the event.');
    } finally {
      setLoading(false);
    }
  };

  const handleBack = () => {
    localStorage.setItem('previousPage', '/admindashboard');
    navigate('/admindashboard', { state: { from: '/admindashboard' } });
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
        {error && (
          <div className="error-message">
            {error}
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
              min="2025-04-15"
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
          <button type="submit" className="submit-button" disabled={loading}>
            {loading ? 'Adding...' : 'Add Event'}
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