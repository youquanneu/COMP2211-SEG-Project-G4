import React from 'react';
import './Modal.css';

function SuccessModal({ onClose, message = 'Password Updated' }) {
  return (
    <div className="modal">
      <div className="modal-content success-modal">
        <h3>Success</h3>
        <p>{message}</p> {/* Use custom message */}
        <button onClick={onClose}>OK</button>
      </div>
    </div>
  );
}

export default SuccessModal;