import React from 'react';
import './Loader.css';

const Loader = ({ size = 'medium', overlay = false }) => {
  const sizeClass = `loader-${size}`;
  
  if (overlay) {
    return (
      <div className="loader-overlay">
        <div className={`loader ${sizeClass}`}>
          <div></div>
          <div></div>
          <div></div>
        </div>
      </div>
    );
  }
  
  return (
    <div className={`loader ${sizeClass}`}>
      <div></div>
      <div></div>
      <div></div>
    </div>
  );
};

export default Loader; 