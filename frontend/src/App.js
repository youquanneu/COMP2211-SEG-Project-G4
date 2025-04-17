// src/App.js
import React, { useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Event from './pages/Event';
import Login from './pages/Login';
import UserHome from './pages/UserHome';
import UserEvent from './pages/UserEvent';
import UserBooking from './pages/UserBooking';
import Settings from './pages/Settings';
import UserCalendar from './pages/UserCalendar';
import UserEmergency from './pages/UserEmergency';
import UserProfile from './pages/UserProfile';
import AdminDashboard from './pages/AdminDashboard';
import AdminUserManagement from './pages/AdminUserManagement';
import AdminEmergency from './pages/AdminEmergency';
import AdminAddEvent from './pages/AdminAddEvent';
import AdminResourceManagement from './pages/AdminResourceManagement';
import AdminVenueManagement from './pages/AdminVenueManagement'; // New import
import AdminEquipmentManagement from './pages/AdminEquipmentManagement'; // New import
import EquipmentApproval from './pages/EquipmentApproval'; // Import the new page (to be created)
import NavigationPage from './pages/NavigationPage';

function App() {
  useEffect(() => {
    const root = document.documentElement;
    const savedDarkTheme = localStorage.getItem('darkTheme') === 'true';
    const savedFontSize = localStorage.getItem('fontSize');

    if (savedFontSize === null) {
      localStorage.setItem('fontSize', 'Medium');
    }

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


    if (localStorage.getItem('darkTheme') === null) {
      localStorage.setItem('darkTheme', 'false');
    }

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
      <Route path="/event" element={<Event />} />
      <Route path="/login" element={<Login />} />
      <Route path="/userhome" element={<UserHome />} />
      <Route path="/userevent" element={<UserEvent />} />
      <Route path="/userbooking" element={<UserBooking />} />
      <Route path="/settings" element={<Settings />} />
      <Route path="/calendar" element={<UserCalendar />} />
      <Route path="/emergency" element={<UserEmergency />} />
      <Route path="/userprofile" element={<UserProfile />} />
      <Route path="/admindashboard" element={<AdminDashboard />} />
      <Route path="/adminaddevent" element={<AdminAddEvent />} />
      <Route path="/usermanagement" element={<AdminUserManagement />} />
      <Route path="/adminemergency" element={<AdminEmergency />} />
      <Route path="/resourcemanagement" element={<AdminResourceManagement />} />
      <Route path="/adminvenuemanagement" element={<AdminVenueManagement />} /> {/* New route */}
      <Route path="/adminequipmentmanagement" element={<AdminEquipmentManagement />} /> {/* New route */}
      <Route path="/equipmentapproval" element={<EquipmentApproval />} />
      <Route path="/navigation" element={<NavigationPage />} />
    </Routes>
  );
}

export default App;