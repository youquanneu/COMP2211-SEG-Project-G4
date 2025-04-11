// src/pages/AdminEmergency.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaExclamation } from 'react-icons/fa';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable'; // Import autoTable directly
import './AdminEmergency.css';

function AdminEmergency() {
  const navigate = useNavigate();
  const [selectedDate, setSelectedDate] = useState('');
  const [showPanicPopup, setShowPanicPopup] = useState(false);
  const [panicClickCount, setPanicClickCount] = useState(0);

  // Mock data for emergencies
  const emergencies = [
    { date: '2025-04-10', emergency: 'Fire in Lab', description: 'Fire broke out in the chemistry lab.', status: 'Pending' },
    { date: '2025-04-10', emergency: 'Medical Emergency', description: 'Student fainted in the hallway.', status: 'Solved' },
    { date: '2025-04-09', emergency: 'Power Outage', description: 'Power outage in the main building.', status: 'Solved' },
  ];

  // Filter emergencies based on selected date
  const filteredEmergencies = selectedDate
    ? emergencies.filter((emergency) => emergency.date === selectedDate)
    : emergencies;

  const handleDateChange = (event) => {
    setSelectedDate(event.target.value);
  };

  const handleExportReport = () => {
    const doc = new jsPDF();
    // Apply autoTable to jsPDF instance
    autoTable(doc, {
      startY: 20,
      head: [['Emergency', 'Description', 'Status']],
      body: filteredEmergencies.map((emergency) => [
        emergency.emergency,
        emergency.description,
        emergency.status,
      ]),
    });
    doc.text('Emergency Report', 20, 10);
    doc.save('emergency-report.pdf');
  };

  const handleBack = () => {
    navigate('/adminhome');
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
        <label htmlFor="date-filter">Filter by Date:</label>
        <input
          type="date"
          id="date-filter"
          value={selectedDate}
          onChange={handleDateChange}
        />
      </div>
      <table className="emergency-table">
        <thead>
          <tr>
            <th>Emergency</th>
            <th>Description</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {filteredEmergencies.length > 0 ? (
            filteredEmergencies.map((emergency, index) => (
              <tr key={index}>
                <td>{emergency.emergency}</td>
                <td>{emergency.description}</td>
                <td>{emergency.status}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="3">No emergencies found for this date.</td>
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
    </div>
  );
}

export default AdminEmergency;