import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaExclamation } from 'react-icons/fa';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import logo from '../assets/logo.png';
import { getAPI_URL } from "../services/api";
import { getEmergencies, sendPanicAlert } from '../services/api';
import './AdminEmergency.css';

function AdminEmergency() {
  const navigate = useNavigate();
  const [fromDate, setFromDate] = useState('');
  const [toDate, setToDate] = useState('');
  const [showPanicPopup, setShowPanicPopup] = useState(false);
  const [panicClickCount, setPanicClickCount] = useState(0);
  const [emergencies, setEmergencies] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchEmergencies = async () => {
      setLoading(true);
      try {
        const response = await getEmergencies();
        if (response.success) {
          setEmergencies(response.data);
        } else {
          setError('Failed to fetch emergencies.');
        }
      } catch (err) {
        setError(err.response?.data?.error || 'An error occurred while fetching emergencies.');
      } finally {
        setLoading(false);
      }
    };

    fetchEmergencies();
  }, []);

  const getFilteredEmergencies = () => {
    if (!fromDate || !toDate) {
      return emergencies;
    }

    const startDate = new Date(fromDate);
    const endDate = new Date(toDate);

    if (endDate < startDate) {
      return [];
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
      head: [['Date', 'Location', 'Description', 'Status']], // Updated "Emergency" to "Location"
      body: filteredEmergencies.map((emergency) => [
        emergency.date,
        emergency.location, // Updated to use location
        emergency.description,
        emergency.status,
      ]),
    });
    doc.text('Emergency Report', 20, 10);
    doc.save('emergency-report.pdf');
  };

  const handleBack = () => {
    localStorage.setItem('previousPage', '/admindashboard');
    navigate('/admindashboard', { state: { from: '/admindashboard' } });
  };

  const handlePanicClick = async () => {
    setPanicClickCount((prevCount) => {
      const newCount = prevCount + 1;
      if (newCount === 3) {
        sendPanicAlert()
          .then((response) => {
            if (response.success) {
              setShowPanicPopup(true);
              setTimeout(() => {
                setShowPanicPopup(false);
              }, 2000);
            } else {
              setError('Failed to send panic alert.');
            }
          })
          .catch((err) => {
            setError(err.response?.data?.error || 'An error occurred while sending the panic alert.');
          });

        return 0;
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
        {error && (
          <div className="error-message">
            {error}
          </div>
        )}
        <div className="filter-group">
          <div className="date-input-group">
            <label htmlFor="from-date">From:</label>
            <input
              type="date"
              id="from-date"
              value={fromDate}
              onChange={handleFromDateChange}
              max={toDate || '2025-04-15'}
            />
          </div>
          <div className="date-input-group">
            <label htmlFor="to-date">To:</label>
            <input
              type="date"
              id="to-date"
              value={toDate}
              onChange={handleToDateChange}
              min={fromDate}
              max="2025-04-15"
            />
          </div>
        </div>
        {loading ? (
          <p>Loading emergencies...</p>
        ) : (
          <table className="emergency-table">
            <thead>
              <tr>
                <th>Date</th>
                <th>Location</th> {/* Updated "Emergency" to "Location" */}
                <th>Description</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {filteredEmergencies.length > 0 ? (
                filteredEmergencies.map((emergency, index) => (
                  <tr key={index}>
                    <td>{emergency.date}</td>
                    <td>{emergency.location}</td> {/* Updated to use location */}
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
        )}
        <button className="export-button" onClick={handleExportReport} disabled={loading}>
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