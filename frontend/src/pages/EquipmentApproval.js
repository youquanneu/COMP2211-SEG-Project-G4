// src/pages/EquipmentApproval.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import './EquipmentApproval.css';

function EquipmentApproval() {
  const navigate = useNavigate();
  
  const [requests, setRequests] = useState([
    {
      id: 1,
      userName: 'John Doe',
      equipmentName: 'Projector',
      quantity: 1,
      bookingDate: '2025-04-20',
      status: 'Pending',
    },
    {
      id: 2,
      userName: 'Jane Smith',
      equipmentName: 'Laptop',
      quantity: 2,
      bookingDate: '2025-04-22',
      status: 'Pending',
    },
    {
      id: 3,
      userName: 'Alice Brown',
      equipmentName: 'Microphone',
      quantity: 3,
      bookingDate: '2025-04-25',
      status: 'Pending',
    },
    {
      id: 4,
      userName: 'Bob Wilson',
      equipmentName: 'Camera',
      quantity: 1,
      bookingDate: '2025-04-18',
      status: 'Pending',
    },
  ]);

  const pendingRequests = requests.filter((request) => request.status === 'Pending');

  const handleApprove = (id) => {
    setRequests((prevRequests) =>
      prevRequests.map((request) =>
        request.id === id ? { ...request, status: 'Approved' } : request
      )
    );
    alert('Request approved!');
  };

  const handleDecline = (id) => {
    setRequests((prevRequests) =>
      prevRequests.map((request) =>
        request.id === id ? { ...request, status: 'Declined' } : request
      )
    );
    alert('Request declined!');
  };

  const handleBack = () => {
    navigate('/resourcemanagement');
  };

  return (
    <div className="equipment-approval-container">
      <header className="equipment-approval-header">
        <img src={logo} alt="Logo" className="equipment-approval-logo" />
      </header>
      <main className="equipment-approval-content">
        <h1>Approval of Equipment</h1>
        {pendingRequests.length > 0 ? (
          <div className="request-table">
            <table>
              <thead>
                <tr>
                  <th>User</th>
                  <th>Equipment</th>
                  <th>Qty</th>
                  <th>Date</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {pendingRequests.map((request) => (
                  <tr key={request.id}>
                    <td>{request.userName}</td>
                    <td>{request.equipmentName}</td>
                    <td>{request.quantity}</td>
                    <td>{request.bookingDate}</td>
                    <td>
                      <button
                        className="approve-button"
                        onClick={() => handleApprove(request.id)}
                      >
                        Approve
                      </button>
                      <button
                        className="decline-button"
                        onClick={() => handleDecline(request.id)}
                      >
                        Decline
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : (
          <p>No pending equipment requests.</p>
        )}
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
      </main>
    </div>
  );
}

export default EquipmentApproval;