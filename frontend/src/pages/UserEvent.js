import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import event1 from '../assets/event1.jpg'; // Add to src/assets
import event2 from '../assets/event2.jpeg';
import event3 from '../assets/event3.jpg';
import './UserEvent.css';

// Mock event data with imported images
const mockEvents = [
  {
    id: 1,
    imageUrl: event1,
    date: '2025-04-15',
    area: 'Computer Science',
    topic: 'Python Workshop',
    organizers: 'AI Club, Jane Doe',
    description: 'Learn Python basics for AI applications in this hands-on workshop.'
  },
  {
    id: 2,
    imageUrl: event2,
    date: '2025-04-20',
    area: 'Library',
    topic: 'Research Seminar',
    organizers: 'Academic Council',
    description: 'Explore cutting-edge research in engineering and technology.'
  },
  {
    id: 3,
    imageUrl: event3,
    date: '2025-04-25',
    area: 'Student Union',
    topic: 'Cultural Night',
    organizers: 'Cultural Club',
    description: 'Celebrate diversity with food, music, and performances.'
  }
];

function UserEvent() {
  const [selectedEvent, setSelectedEvent] = useState(null);
  const navigate = useNavigate();

  const handleEventClick = (event) => {
    setSelectedEvent(event);
  };

  const closePopup = () => {
    setSelectedEvent(null);
  };

  const handleBack = () => {
    navigate('/userhome');
  };

  return (
    <div className="events-container">
      <header className="events-header">
        <img src={logo} alt="Logo" className="events-logo" />
        <h1>University Events</h1>
      </header>
      <main className="events-content">
        <div className="events-list">
          {mockEvents.map(event => (
            <div key={event.id} className="event-card" onClick={() => handleEventClick(event)}>
              <img src={event.imageUrl} alt={event.topic} className="event-image" onError={(e) => { e.target.src = logo; }} />
              <div className="event-details">
                <h3>{event.topic}</h3>
                <p><strong>Date:</strong> {new Date(event.date).toLocaleDateString()}</p>
                <p><strong>Area:</strong> {event.area}</p>
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
          <div className="popup-content" onClick={e => e.stopPropagation()}>
            <button className="close-button" onClick={closePopup}>×</button>
            <img src={selectedEvent.imageUrl} alt={selectedEvent.topic} className="popup-image" onError={(e) => { e.target.src = logo; }} />
            <h2>{selectedEvent.topic}</h2>
            <p><strong>Date:</strong> {new Date(selectedEvent.date).toLocaleDateString()}</p>
            <p><strong>Area:</strong> {selectedEvent.area}</p>
            <p><strong>Organizers:</strong> {selectedEvent.organizers}</p>
            <p><strong>Description:</strong> {selectedEvent.description}</p>
          </div>
        </div>
      )}
    </div>
  );
}

export default UserEvent;