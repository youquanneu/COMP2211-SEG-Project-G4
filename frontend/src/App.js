// src/App.js
import React, { useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import UserHome from './pages/UserHome';
import UserBooking from './pages/UserBooking';
import Settings from './pages/Settings';
import UserCalendar from './pages/UserCalendar';
import UserEmergency from './pages/UserEmergency';
import UserProfile from './pages/UserProfile';
import AdminHome from './pages/AdminHome';
import AdminUserManagement from './pages/AdminUserManagement';
import AdminEmergency from './pages/AdminEmergency'; // Added

function App() {
  useEffect(() => {
    const root = document.documentElement;
    const savedDarkTheme = localStorage.getItem('darkTheme') === 'true';
    const savedFontSize = localStorage.getItem('fontSize');

    // Set default font size to Medium (16px) only if not set (first run)
    if (savedFontSize === null) {
      localStorage.setItem('fontSize', 'Medium');
    }

    // Apply font size based on saved value (or default Medium)
    switch (savedFontSize || 'Medium') {
      case 'Small':
        root.style.setProperty('--font-size-base', '12px');
        break;
      case 'Medium':
        root.style.setProperty('--font-size-base', '16px');
        break;
      case 'Large':
        root.style.setProperty('--font-size-base', '20px');
        break;
      default:
        root.style.setProperty('--font-size-base', '16px');
    }

    // Set default theme to light only if not set (first run)
    if (localStorage.getItem('darkTheme') === null) {
      localStorage.setItem('darkTheme', 'false');
    }

    // Apply theme based on saved value
    if (savedDarkTheme) {
      root.style.setProperty('--background-color', '#333');
      root.style.setProperty('--text-color', '#fff');
      root.style.setProperty('--secondary-text-color', '#ccc');
      root.style.setProperty('--button-bg-start', '#0077B6');
      root.style.setProperty('--button-bg-end', '#005888');
      root.style.setProperty('--border-color', '#555');
      root.style.setProperty('--shadow-color', 'rgba(255, 255, 255, 0.1)');
    } else {
      root.style.setProperty('--background-color', '#fff');
      root.style.setProperty('--text-color', '#333');
      root.style.setProperty('--secondary-text-color', '#555');
      root.style.setProperty('--button-bg-start', '#0077B6');
      root.style.setProperty('--button-bg-end', '#005888');
      root.style.setProperty('--border-color', '#ddd');
      root.style.setProperty('--shadow-color', 'rgba(0, 0, 0, 0.1)');
    }
  }, []);

  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/userhome" element={<UserHome />} />
      <Route path="/userbooking" element={<UserBooking />} />
      <Route path="/settings" element={<Settings />} />
      <Route path="/usercalendar" element={<UserCalendar />} />
      <Route path="/emergency" element={<UserEmergency />} />
      <Route path="/userprofile" element={<UserProfile />} />
      <Route path="/adminhome" element={<AdminHome />} />
      <Route path="/usermanagement" element={<AdminUserManagement />} />
      <Route path="/adminemergency" element={<AdminEmergency />} /> {/* Added */}
      {/* Placeholder route for Resource Management */}
      <Route path="/resourcemanagement" element={<div>Resource Management Page (To Be Developed)</div>} />
    </Routes>
  );
}

export default App;