import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/Navbar.css'; // Have to make this file later guys

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <Link to="/">Smart Campus</Link>
      </div>
      <ul className="navbar-links">
        <li><Link to="/">Dashboard</Link></li>
        <li><Link to="/map">Map</Link></li>
        <li><Link to="/booking">Bookings</Link></li>
        <li><Link to="/events">Events</Link></li>
        <li><Link to="/emergency">Emergency</Link></li>
        <li><Link to="/login">Login</Link></li>
      </ul>
    </nav>
  );
};

export default Navbar;