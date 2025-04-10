import React, { useState } from 'react';
import './Auth.css';

function OTPForm({ otp, onVerify }) {
  const [enteredOTP, setEnteredOTP] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onVerify(enteredOTP);
    setEnteredOTP('');
  };

  return (
    <div>
      <h2>Verify OTP</h2>
      <div className="otp-display">
        <span>Your OTP:</span>
        <div id="generatedOTP">{otp}</div>
      </div>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Enter OTP</label>
          <input
            type="text"
            value={enteredOTP}
            onChange={(e) => setEnteredOTP(e.target.value)}
            placeholder="Enter 6-digit OTP"
          />
        </div>
        <button type="submit">Verify</button>
      </form>
    </div>
  );
}

export default OTPForm;