// src/pages/RegisterNewUser.js
import React, { useState } from 'react';
import './RegisterNewUser.css';

function RegisterNewUser({ onClose }) {
    const [role, setRole] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [faculty, setFaculty] = useState('');
    const [showSuccessPopup, setShowSuccessPopup] = useState(false);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!role || !firstName || !lastName || !email || !faculty) {
            alert('Please fill in all fields.');
            return;
        }
        // In a real app, you'd send this data to a backend API here
        console.log('New user registered:', { role, firstName, lastName, email, faculty });

        // Show success pop-up
        setShowSuccessPopup(true);
        setTimeout(() => {
            setShowSuccessPopup(false);
            onClose(); // Close the modal after showing the success message
        }, 2000); // Show success message for 2 seconds

        // Reset form
        setRole('');
        setFirstName('');
        setLastName('');
        setEmail('');
        setFaculty('');
    };

    return (
        <div className="register-modal-overlay">
            <div className="register-modal">
                <h2>User Registration</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Role:</label>
                        <select
                            value={role}
                            onChange={(e) => setRole(e.target.value)}
                            className="role-dropdown"
                        >
                            <option value="">Select a role</option>
                            <option value="student">Student</option>
                            <option value="lecturer">Lecturer</option>
                        </select>
                    </div>
                    <div className="form-group">
                        <label>First Name:</label>
                        <input
                            type="text"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            placeholder=""
                        />
                    </div>
                    <div className="form-group">
                        <label>Last Name:</label>
                        <input
                            type="text"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            placeholder=""
                        />
                    </div>
                    <div className="form-group">
                        <label>Email:</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder=""
                        />
                    </div>
                    <div className="form-group">
                        <label>Faculty:</label>
                        <input
                            type="text"
                            value={faculty}
                            onChange={(e) => setFaculty(e.target.value)}
                            placeholder=""
                        />
                    </div>
                    <button type="submit" className="register-button">
                        Register
                    </button>
                </form>
                <button className="close-button" onClick={onClose}>
                    Close
                </button>
            </div>
            {showSuccessPopup && (
                <div className="success-popup">
                    <p>New user registered</p>
                </div>
            )}
        </div>
    );
}

export default RegisterNewUser;