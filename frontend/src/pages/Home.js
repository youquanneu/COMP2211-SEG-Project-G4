// src/pages/Home.js
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaCog } from 'react-icons/fa';
import logo from '../assets/logo.png';
import campusPhoto from '../assets/campus.jpg';
import './Home.css';

function Home() {
  const navigate = useNavigate();

  // Force light theme on homepage load
  useEffect(() => {
    const root = document.documentElement;
    localStorage.setItem('darkTheme', 'false');
    root.style.setProperty('--background-color', '#fff');
    root.style.setProperty('--text-color', '#333');
    root.style.setProperty('--secondary-text-color', '#555');
    root.style.setProperty('--button-bg-start', '#0077B6');
    root.style.setProperty('--button-bg-end', '#005888');
    root.style.setProperty('--border-color', '#ddd');
    root.style.setProperty('--shadow-color', 'rgba(0, 0, 0, 0.1)');
  }, []);

  const handleNavigation = (event) => {
    const selectedPage = event.target.value;
    if (selectedPage === 'login') {
      navigate('/login');
    } else if (selectedPage === 'emergency') {
      navigate('/emergency'); // Navigate to the emergency page
    }
  };

  const handleSignIn = () => {
    navigate('/login');
  };

  const handleSettings = () => {
    navigate('/settings');
  };

  return (
    <div className="home-container">
      <header className="home-header">
        <img src={logo} alt="Logo" className="home-logo" />
        <div className="nav-wrapper">
          <select className="nav-dropdown" onChange={handleNavigation}>
            <option value="home">Home</option>
            <option value="events">Events</option>
            <option value="directory">Directory</option>
            <option value="emergency">Emergency</option>
          </select>
        </div>
        <div className="auth-wrapper">
          <button className="sign-in-button" onClick={handleSignIn}>
            Sign In
          </button>
          <FaCog className="settings-icon home-settings-icon" onClick={handleSettings} />
        </div>
      </header>
      <main className="home-content">
        <h1>Welcome to University of Southampton Malaysia (UoSM)</h1>
        <p>
          The University of Southampton Malaysia (UoSM) is a branch campus of the University of Southampton, UK, a world-leading, research-intensive university and founding member of the prestigious Russell Group. Established in October 2012 at the invitation of the Malaysian Ministry of Higher Education, UoSM initially opened within the EduCity development in Iskandar, Johor. In October 2021, it relocated to a new, state-of-the-art campus in Eco Galleria, Iskandar Puteri, Johor, designed to accommodate up to 2,000 students.
        </p>
        <img src={campusPhoto} alt="UoSM Campus" className="home-photo" />
        <p>
          UoSM offers a range of foundation, undergraduate, and postgraduate programs, with a strong emphasis on engineering, business, and computer science. Its split-campus programs allow students to study the first two years in Malaysia and the final two in the UK, providing a globally recognized education at a reduced cost—up to 75% savings compared to studying fully in the UK. The university is ranked 80th in the QS World University Rankings 2025, making it the top UK university in Malaysia.
        </p>
        <p>
          The Eco Galleria campus spans 150,000 square feet and features cutting-edge facilities, including a Bloomberg Trading Suite, Aerospace Lab with a wind tunnel, and multiple engineering and computer science labs. With a high employability rate—100% of graduates secure work or further study within 15 months (DiscoverUni)—UoSM prepares students for successful careers worldwide. Use the navigation above to explore more options!
        </p>
      </main>
    </div>
  );
}

export default Home;