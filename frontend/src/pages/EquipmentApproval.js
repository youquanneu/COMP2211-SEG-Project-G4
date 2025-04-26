// src/pages/EquipmentApproval.js
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import './EquipmentApproval.css';
import axios from 'axios';
import { getAPI_URL } from "../services/api";

function EquipmentApproval() {
  const navigate = useNavigate();
  const getPendingList = async () => {
    try {
        const response = await axios.get(getAPI_URL('admin/reservationManagement/getPendingReservation'));
        setRequests(response.data)
        console.log(response.data)
    } catch (error) {
      console.error('Error fetching :', error);
      alert (error.message);
    }
  };
  useEffect(() => {
      getPendingList();
    }, []);
  const [requests, setRequests] = useState([]);

  const handleApprove = async (request) => {
  try {
    const response = await axios.post(getAPI_URL('admin/reservationManagement/approveReservation'),request);
    console.log(response)
  }catch (error) {
         console.error('Error during approve :', error);
         alert (error.message);
       }
  };

  const handleDecline = async (request) => {
    try {
      const response = await axios.post(getAPI_URL('admin/reservationManagement/rejectReservation'),request);
      console.log(response)
      }
      catch (error) {
             console.error('Error during approve :', error);
             alert(error.message);
           }
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
        {requests.length > 0 ? (
          <div className="request-table">
            <table>
              <thead>
                <tr>
                  <th>User</th>
                  <th>Resource</th>
                  <th>Purpose</th>
                  <th>Period</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {requests.map((request) => (
                  <tr key={request.reservationId}>
                    <td>{request.userDTO.username}</td>
                    <td>{request.resourceDTO.resourceName}</td>
                    <td>{request.purpose}</td>
                    <td>
                    {request.reservationStarting} to {request.reservationEnding}
                    </td>
                    <td>
                      <button
                        className="approve-button"
                        onClick={() => handleApprove(request)}
                      >
                        Approve
                      </button>
                      <button
                        className="decline-button"
                        onClick={() => handleDecline(request)}
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
          <p>No pending reservation requests.</p>
        )}
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
      </main>
    </div>
  );
}

export default EquipmentApproval;