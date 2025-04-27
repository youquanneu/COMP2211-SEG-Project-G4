import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import './AdminAddEvent.css';
import { getAPI_URL } from "../services/api";
import axios from 'axios';
import Select from 'react-select';

function AdminAddEvent() {
  const navigate = useNavigate();
  const [venueOptions, setVenueOptions] = useState([]);
  const [organizerOptions, setOrganizerOptions] = useState([]);
  const [today, setToday] = useState('');
  const [formData, setFormData] = useState({
    image: null,
    title: '',
    area: '',
    startDate: '',
    endDate: '',
    startTime: '',
    endTime: '',
    venue: [],
    organizer: [],
    description: '',
  });

  useEffect(() => {
    const today = new Date().toISOString().split('T')[0];
    setToday(today);
    const fetchOptions = async () => {
      try {
        const venueResponse = await axios.get(getAPI_URL('user/venue/getAllVenue'));
        setVenueOptions(venueResponse.data.map(v => ({
          label: v.resourceName,
          value: v.resourceId,
          original: v
        })));

        const userResponse = await axios.get(getAPI_URL('admin/userManagement/getAllUser'));
        setOrganizerOptions(userResponse.data.map(o => ({
          label: o.username,
          value: o.userId,
          original: o
        })));
      } catch (err) {
        console.error("Error fetching options:", err);
      }
    };

    fetchOptions();
  }, []);


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

  const handleVenueChange = (e) => {
      const selectedVenues = Array.from(e.target.selectedOptions).map(option => JSON.parse(option.value));
      setFormData(prev => ({ ...prev, venue: selectedVenues }));
      console.log("Selected Venues:", selectedVenues);
    };

  const handleOrganizerChange = (e) => {
    const selectedOrganizers = Array.from(e.target.selectedOptions).map(option => JSON.parse(option.value));
    setFormData(prev => ({ ...prev, organizer: selectedOrganizers }));
    console.log("Selected Organizers:", selectedOrganizers);
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);


    // Validation: Ensure start date/time is before end date/time
    if (new Date(formData.startDate + 'T' + formData.startTime) >= new Date(formData.endDate + 'T' + formData.endTime)) {
        setError("Start time must be before end time.");
        setLoading(false);
        return;
      }

    // Ensure at least one venue and one organizer are selected
    if (formData.venue.length === 0 || formData.organizer.length === 0) {
            setError("At least one venue and one organizer must be selected.");
            setLoading(false);
            return;
          }

    const eventRequest = {
        title: formData.title,
        area: formData.area,
        startingTime: formData.startDate + 'T' + formData.startTime,
        endingTime: formData.endDate + 'T' + formData.endTime,
        venue: formData.venue,
        organizer: formData.organizer,
        description: formData.description,
      };
    try {
      const response = await axios.post(getAPI_URL('admin/event/newEvent'),eventRequest);
      console.log("success : ", response.data)
        setShowSuccess(true);
        setTimeout(() => setShowSuccess(false), 2000);
        setFormData({
          image: null,
          title: '',
          area: '',
          startDate: '',
          endDate: '',
          startTime: '',
          endTime: '',
          venue: [],
          organizer: [],
          description: '',
        });
//      } else {
//        setError('Failed to add event. Please try again.');
//      }
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
            <label htmlFor="startDate">Start Date:</label>
            <input
              type="date"
              id="startDate"
              name="startDate"
              value={formData.startDate}
              onChange={handleChange}
              min={today}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="startTime">Start Time:</label>
            <input
              type="time"
              id="startTime"
              name="startTime"
              value={formData.startTime}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="endDate">End Date:</label>
            <input
              type="date"
              id="endDate"
              name="endDate"
              value={formData.endDate}
              onChange={handleChange}
              min={today}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="endTime">End Time:</label>
            <input
              type="time"
              id="endTime"
              name="endTime"
              value={formData.endTime}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Venue:</label>
            <Select
              options={venueOptions}
              isMulti
              value={venueOptions.filter(option =>
                formData.venue.some(v => v.resourceId === option.value)
              )}
              onChange={(selected) => {
                const venues = selected.map(option => option.original);
                setFormData(prev => ({ ...prev, venue: venues }));
              }}
            />
          </div>
          <div className="form-group">
            <label>Organizer:</label>
            <Select
              options={organizerOptions}
              isMulti
              value={organizerOptions.filter(option =>
                formData.organizer.some(o => o.userId === option.value)
              )}
              onChange={(selected) => {
                const organizers = selected.map(option => option.original);
                setFormData(prev => ({ ...prev, organizer: organizers }));
              }}
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