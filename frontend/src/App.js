import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <router>
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <p>
            Edit <code>src/App.js</code> and save to reload.
          </p>
          <a
            className="App-link"
            href="https://reactjs.org"
            target="_blank"
            rel="noopener noreferrer"
          >
            Learn React
          </a>
        </header>
        
        <main className="container">
            <Routes>
              <Route path="/" element={<Dashboard />} />
              <Route path="/login" element={<Login />} />
              <Route path="/map" element={<CampusMap />} />
              <Route path="/booking" element={<ResourceBooking />} />
              <Route path="/events" element={<EventCalendar />} />
              <Route path="/emergency" element={<EmergencyAlert />} />
            </Routes>
          </main>
      </div>
    </router>
  );
}

export default App;
