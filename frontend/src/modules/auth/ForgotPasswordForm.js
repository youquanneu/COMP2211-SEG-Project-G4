import React, { useState } from 'react';
import './Auth.css';

function ForgotPasswordForm({ onSubmit, onBack }) {
  const [email, setEmail] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(email);
    setEmail('');
  };

  return (
    <div>
      <h2>Forgot Password</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Email</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Enter your email"
          />
        </div>
        <button type="submit">Send Reset OTP</button>
      </form>
      <p className="back-link" onClick={onBack}>Back to Login</p>
    </div>
  );
}

export default ForgotPasswordForm;