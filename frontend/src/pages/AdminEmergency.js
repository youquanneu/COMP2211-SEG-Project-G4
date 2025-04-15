// src/pages/AdminEmergency.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaExclamation } from 'react-icons/fa';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import logo from '../assets/logo.png';
import './AdminEmergency.css';

function AdminEmergency() {
  const navigate = useNavigate();
  const [fromDate, setFromDate] = useState(''); // State for "From" date
  const [toDate, setToDate] = useState(''); // State for "To" date
  const [showPanicPopup, setShowPanicPopup] = useState(false);
  const [panicClickCount, setPanicClickCount] = useState(0);

  // Mock data with varied dates
  const emergencies = [
    { date: '2025-04-14', emergency: 'Fire in Lab', description: 'Fire broke out in the chemistry lab.', status: 'Pending' },
    { date: '2025-04-13', emergency: 'Medical Emergency', description: 'Student fainted in the hallway.', status: 'Solved' },
    { date: '2025-04-10', emergency: 'Power Outage', description: 'Power outage in the main building.', status: 'Solved' },
    { date: '2025-03-20', emergency: 'Flood Alert', description: 'Flooding reported in basement.', status: 'Pending' },
    { date: '2025-03-15', emergency: 'Security Breach', description: 'Unauthorized access in restricted area.', status: 'Solved' },
    { date: '2024-12-25', emergency: 'Fire Alarm', description: 'Fire alarm triggered in building A.', status: 'Solved' },
    { date: '2024-11-30', emergency: 'Medical Emergency', description: 'Staff member had a heart attack.', status: 'Solved' },
    { date: '2024-06-15', emergency: 'Power Outage', description: 'Campus-wide power outage.', status: 'Solved' },
  ];

  // Function to filter emergencies based on the selected date range
  const getFilteredEmergencies = () => {
    if (!fromDate || !toDate) {
      return emergencies; // If no dates are selected, show all emergencies
    }

    const startDate = new Date(fromDate);
    const endDate = new Date(toDate);

    // Ensure endDate is not before startDate
    if (endDate < startDate) {
      return []; // Return empty array if the range is invalid
    }

    return emergencies.filter((emergency) => {
      const emergencyDate = new Date(emergency.date);
      return emergencyDate >= startDate && emergencyDate <= endDate;
    });
  };

  const filteredEmergencies = getFilteredEmergencies();

  const handleFromDateChange = (event) => {
    setFromDate(event.target.value);
  };

  const handleToDateChange = (event) => {
    setToDate(event.target.value);
  };

  const handleExportReport = () => {
    const doc = new jsPDF();
    autoTable(doc, {
      startY: 20,
      head: [['Date', 'Emergency', 'Description', 'Status']],
      body: filteredEmergencies.map((emergency) => [
        emergency.date,
        emergency.emergency,
        emergency.description,
        emergency.status,
      ]),
    });
    doc.text('Emergency Report', 20, 10);
    doc.save('emergency-report.pdf');
  };

  const handleBack = () => {
    navigate('/admindashboard');
  };

  const handlePanicClick = () => {
    setPanicClickCount((prevCount) => {
      const newCount = prevCount + 1;
      if (newCount === 3) {
        setShowPanicPopup(true);
        setTimeout(() => {
          setShowPanicPopup(false);
        }, 2000);
        return 0; // Reset count after showing popup
      }
      return newCount;
    });
  };

  return (
    <div className="admin-emergency-container">
      <header className="admin-emergency-header">
        <img src={logo} alt="Logo" className="admin-emergency-logo" />
      </header>
      <main className="admin-emergency-content">
        <div className="panic-button" onClick={handlePanicClick}>
          <FaExclamation className="panic-icon" />
        </div>
        {showPanicPopup && (
          <div className="panic-popup">
            <p>Emergency sent to all.</p>
          </div>
        )}
        <h1>Emergency Report</h1>
        <div className="filter-group">
          <div className="date-input-group">
            <label htmlFor="from-date">From:</label>
            <input
              type="date"
              id="from-date"
              value={fromDate}
              onChange={handleFromDateChange}
              max={toDate || '2025-04-15'} // Prevent selecting future dates beyond "To" date or current date
            />
          </div>
          <div className="date-input-group">
            <label htmlFor="to-date">To:</label>
            <input
              type="date"
              id="to-date"
              value={toDate}
              onChange={handleToDateChange}
              min={fromDate} // Prevent selecting dates before "From" date
              max="2025-04-15" // Prevent selecting future dates
            />
          </div>
        </div>
        <table className="emergency-table">
          <thead>
            <tr>
              <th>Date</th>
              <th>Emergency</th>
              <th>Description</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {filteredEmergencies.length > 0 ? (
              filteredEmergencies.map((emergency, index) => (
                <tr key={index}>
                  <td>{emergency.date}</td>
                  <td>{emergency.emergency}</td>
                  <td>{emergency.description}</td>
                  <td>{emergency.status}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="4">No emergencies found for this date range.</td>
              </tr>
            )}
          </tbody>
        </table>
        <button className="export-button" onClick={handleExportReport}>
          Export Report
        </button>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
      </main>
    </div>
  );
}

export default AdminEmergency;