import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import './Settings.css';

function Settings() {
  const navigate = useNavigate();

  const [fontSize, setFontSize] = useState(() => {
    const savedFontSize = localStorage.getItem('fontSize');
    return savedFontSize || 'Medium';
  });

  const [darkTheme, setDarkTheme] = useState(() => {
    const savedDarkTheme = localStorage.getItem('darkTheme');
    return savedDarkTheme === 'true'; // Defaults to false (light) if null or 'false'
  });

  const handleBack = () => {
    navigate(-1);
  };

  const toggleTheme = () => {
    setDarkTheme(!darkTheme);
    localStorage.setItem('darkTheme', !darkTheme);
    const root = document.documentElement;
    if (!darkTheme) {
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
  };

  const handleFontSizeChange = (event) => {
    const newSize = event.target.value;
    setFontSize(newSize);
    localStorage.setItem('fontSize', newSize);
    const root = document.documentElement;
    switch (newSize) {
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
  };

  return (
    <div className="settings-container">
      <header className="settings-header">
        <img src={logo} alt="Logo" className="settings-logo" />
        <h1>Settings</h1>
      </header>
      <main className="settings-content">
        <div className="setting-item">
          <label>Font Size</label>
          <select
            value={fontSize}
            onChange={handleFontSizeChange}
            className="font-size-dropdown"
          >
            <option value="Small">Small</option>
            <option value="Medium">Medium</option>
            <option value="Large">Large</option>
          </select>
        </div>
        <div className="setting-item">
          <label>Dark Theme</label>
          <input
            type="checkbox"
            checked={darkTheme}
            onChange={toggleTheme}
          />
        </div>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
      </main>
    </div>
  );
}

export default Settings;