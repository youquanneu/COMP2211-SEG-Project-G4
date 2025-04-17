import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from '../assets/logo.png';
import RegisterNewUser from './RegisterNewUser';
import { getStudents, getLecturers } from '../api'; // Import the new functions
import './AdminUserManagement.css';

function AdminUserManagement() {
  const navigate = useNavigate();
  const [filter, setFilter] = useState('');
  const [showRegisterPopup, setShowRegisterPopup] = useState(false);
  const [students, setStudents] = useState([]);
  const [lecturers, setLecturers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUsers = async () => {
      setLoading(true);
      try {
        const [studentsResponse, lecturersResponse] = await Promise.all([
          getStudents(),
          getLecturers(),
        ]);

        if (studentsResponse.success) {
          setStudents(studentsResponse.data);
        } else {
          setError('Failed to fetch students.');
        }

        if (lecturersResponse.success) {
          setLecturers(lecturersResponse.data);
        } else {
          setError('Failed to fetch lecturers.');
        }
      } catch (err) {
        setError(err.response?.data?.error || 'An error occurred while fetching users.');
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);

  const handleFilterChange = (event) => {
    setFilter(event.target.value);
  };

  const handleRegisterNewUser = (event) => {
    event.preventDefault();
    setShowRegisterPopup(true);
  };

  const closeRegisterPopup = () => {
    setShowRegisterPopup(false);
  };

  const handleBack = () => {
    localStorage.setItem('previousPage', '/admindashboard');
    navigate('/admindashboard', { state: { from: '/admindashboard' } });
  };

  const userList = filter === 'students' ? students : filter === 'lecturers' ? lecturers : [];

  return (
    <div className="user-management-container">
      <header className="user-management-header">
        <img src={logo} alt="Logo" className="user-management-logo" />
      </header>
      <main className="user-management-content">
        <h1>User Management</h1>
        {error && (
          <div className="error-message">
            {error}
          </div>
        )}
        <div className="filter-section">
          <label>Filter:</label>
          <select className="filter-dropdown" onChange={handleFilterChange} value={filter}>
            <option value="">Select a filter</option>
            <option value="students">Students</option>
            <option value="lecturers">Lecturers</option>
          </select>
        </div>
        {filter && (
          <div className="info-section">
            <h3>Information:</h3>
            <div className="info-box">
              {loading ? (
                <p>Loading users...</p>
              ) : userList.length > 0 ? (
                userList.map((user) => (
                  <div key={user.id} className="user-item">
                    <p><strong>ID:</strong> {user.id}</p>
                    <p><strong>Name:</strong> {user.name}</p>
                    <p><strong>Email:</strong> {user.email}</p>
                  </div>
                ))
              ) : (
                <p>No users found.</p>
              )}
            </div>
          </div>
        )}
        <div className="register-link">
          <a href="#" className="register-link-text" onClick={handleRegisterNewUser}>
            Register New User
          </a>
        </div>
        <button className="back-button" onClick={handleBack}>
          Back
        </button>
      </main>
      {showRegisterPopup && <RegisterNewUser onClose={closeRegisterPopup} />}
    </div>
  );
}

export default AdminUserManagement;