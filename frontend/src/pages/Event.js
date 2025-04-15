import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import event1 from '../assets/event1.jpg';
import event2 from '../assets/event2.jpeg';
import event3 from '../assets/event3.jpg';
import './Event.css';

// Mock event data with imported images, updated with time and venue
const mockEvents = [
  {
    id: 1,
    imageUrl: event1,
    date: '2025-04-15',
    time: '14:00-16:00',
    area: 'Computer Science',
    topic: 'Python Workshop',
    venue: 'Lecture Hall A',
    organizer: 'Jane Doe',
    description: 'Learn Python basics for AI applications in this hands-on workshop.',
  },
  {
    id: 2,
    imageUrl: event2,
    date: '2025-04-20',
    time: '10:00-12:00',
    area: 'Library',
    topic: 'Research Seminar',
    venue: 'Main Library Room 101',
    organizer: 'Academic Council',
    description: 'Explore cutting-edge research in engineering and technology.',
  },
  {
    id: 3,
    imageUrl: event3,
    date: '2025-04-25',
    time: '18:00-21:00',
    area: 'Student Union',
    topic: 'Cultural Night',
    venue: 'Student Union Hall',
    organizer: 'Cultural Club',
    description: 'Celebrate diversity with food, music, and performances.',
  },
];

function Event() {
  const [selectedEvent, setSelectedEvent] = useState(null);
  const navigate = useNavigate();

  const handleEventClick = (event) => {
    setSelectedEvent(event);
  };

  const closePopup = () => {
    setSelectedEvent(null);
  };

  const handleBack = () => {
    navigate('/');
  };

  return (
    <div className="events-container">
      <header className="events-header">
        <img src={logo} alt="Logo" className="events-logo" />
        <h1>University Events</h1>
      </header>
      <main className="events-content">
        <div className="events-list">
          {mockEvents.map((event) => (
            <div
              key={event.id}
              className="event-card"
              onClick={() => handleEventClick(event)}
            >
              <img
                src={event.imageUrl}
                alt={event.topic}
                className="event-image"
                onError={(e) => {
                  e.target.src = logo;
                }}
              />
              <div className="event-details">
                <h3>{event.topic}</h3>
                <p>
                  <strong>Area:</strong> {event.area}
                </p>
                <p>
                  <strong>Date:</strong>{' '}
                  {new Date(event.date).toLocaleDateString()}
                </p>
                <p>
                  <strong>Time:</strong> {event.time}
                </p>
              </div>
            </div>
          ))}
        </div>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
      </main>

      {selectedEvent && (
        <div className="event-popup" onClick={closePopup}>
          <div className="popup-content" onClick={(e) => e.stopPropagation()}>
            <button className="close-button" onClick={closePopup}>
              ×
            </button>
            <img
              src={selectedEvent.imageUrl}
              alt={selectedEvent.topic}
              className="popup-image"
              onError={(e) => {
                e.target.src = logo;
              }}
            />
            <h2>{selectedEvent.topic}</h2>
            <p>
              <strong>Area:</strong> {selectedEvent.area}
            </p>
            <p>
              <strong>Date:</strong>{' '}
              {new Date(selectedEvent.date).toLocaleDateString()}
            </p>
            <p>
              <strong>Time:</strong> {selectedEvent.time}
            </p>
            <p>
              <strong>Venue:</strong> {selectedEvent.venue}
            </p>
            <p>
              <strong>Organizer:</strong> {selectedEvent.organizer}
            </p>
            <p>
              <strong>Description:</strong> {selectedEvent.description}
            </p>
          </div>
        </div>
      )}
    </div>
  );
}

export default Event;