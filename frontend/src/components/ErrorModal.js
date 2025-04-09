import React from 'react';
import './Modal.css';

function ErrorModal({ message, onClose }) {
  return (
    <div className="modal">
      <div className="modal-content">
        <h3>Invalid Input</h3>
        <p>{message}</p>
        <button onClick={onClose}>Try Again</button>
      </div>
    </div>
  );
}

export default ErrorModal;