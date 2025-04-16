import React, { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { getStoredUser, logoutUser } from '../../services/api';
import '../../styles/Navbar.css';
import { Avatar, Menu, MenuItem, IconButton, Badge, Tooltip } from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import ExploreIcon from '@mui/icons-material/Explore';
import EventIcon from '@mui/icons-material/Event';
import BookIcon from '@mui/icons-material/Book';
import EmergencyIcon from '@mui/icons-material/LocalHospital';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import LogoutIcon from '@mui/icons-material/Logout';
import Brightness4Icon from '@mui/icons-material/Brightness4';
import Brightness7Icon from '@mui/icons-material/Brightness7';
import NotificationsIcon from '@mui/icons-material/Notifications';

const Navbar = () => {
  const location = useLocation();
  const [user, setUser] = useState(null);
  const [anchorEl, setAnchorEl] = useState(null);
  const [isDarkMode, setIsDarkMode] = useState(localStorage.getItem('darkTheme') === 'true');
  
  useEffect(() => {
    const storedUser = getStoredUser();
    if (storedUser) {
      setUser(storedUser);
    }
  }, []);
  
  const handleProfileClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  
  const handleCloseMenu = () => {
    setAnchorEl(null);
  };
  
  const handleLogout = () => {
    logoutUser();
    window.location.href = '/login';
  };
  
  const toggleDarkMode = () => {
    const newDarkMode = !isDarkMode;
    localStorage.setItem('darkTheme', newDarkMode.toString());
    setIsDarkMode(newDarkMode);
    
    const root = document.documentElement;
    if (newDarkMode) {
      root.style.setProperty('--background-color', '#333');
      root.style.setProperty('--text-color', '#fff');
      root.style.setProperty('--secondary-text-color', '#ccc');
      root.style.setProperty('--button-bg-start', '#0077B6');
      root.style.setProperty('--button-bg-end', '#005888');
      root.style.setProperty('--border-color', '#555');
      root.style.setProperty('--shadow-color', 'rgba(255, 255, 255, 0.1)');
    } else {
      root.style.setProperty('--background-color', '#fff');
      root.style.setProperty('--text-color', '#333');
      root.style.setProperty('--secondary-text-color', '#555');
      root.style.setProperty('--button-bg-start', '#0077B6');
      root.style.setProperty('--button-bg-end', '#005888');
      root.style.setProperty('--border-color', '#ddd');
      root.style.setProperty('--shadow-color', 'rgba(0, 0, 0, 0.1)');
    }
  };
  
  const isActive = (path) => {
    return location.pathname === path;
  };

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <Link to="/">Smart Campus</Link>
      </div>
      
      <ul className="navbar-links">
        <li className={isActive('/') || isActive('/userhome') ? 'active' : ''}>
          <Link to="/" title="Dashboard">
            <DashboardIcon /> <span className="link-text">Dashboard</span>
          </Link>
        </li>
        <li className={isActive('/navigation') ? 'active' : ''}>
          <Link to="/navigation" title="Navigation">
            <ExploreIcon /> <span className="link-text">Navigation</span>
          </Link>
        </li>
        <li className={isActive('/booking') || isActive('/userbooking') ? 'active' : ''}>
          <Link to="/booking" title="Bookings">
            <BookIcon /> <span className="link-text">Bookings</span>
          </Link>
        </li>
        <li className={isActive('/events') || isActive('/calendar') ? 'active' : ''}>
          <Link to="/events" title="Events">
            <EventIcon /> <span className="link-text">Events</span>
          </Link>
        </li>
        <li className={isActive('/emergency') ? 'active' : ''}>
          <Link to="/emergency" title="Emergency">
            <EmergencyIcon /> <span className="link-text">Emergency</span>
          </Link>
        </li>
      </ul>
      
      <div className="navbar-actions">
        <Tooltip title={isDarkMode ? "Switch to Light Mode" : "Switch to Dark Mode"}>
          <IconButton onClick={toggleDarkMode} color="inherit" className="theme-toggle">
            {isDarkMode ? <Brightness7Icon /> : <Brightness4Icon />}
          </IconButton>
        </Tooltip>
        
        <Tooltip title="Notifications">
          <IconButton color="inherit">
            <Badge badgeContent={3} color="error">
              <NotificationsIcon />
            </Badge>
          </IconButton>
        </Tooltip>
        
        {user ? (
          <>
            <Tooltip title="Account">
              <IconButton 
                onClick={handleProfileClick}
                className="profile-button"
              >
                {user.profileImage ? (
                  <Avatar src={user.profileImage} alt={user.username} />
                ) : (
                  <Avatar>{user.username?.charAt(0).toUpperCase() || 'U'}</Avatar>
                )}
              </IconButton>
            </Tooltip>
            
            <Menu
              anchorEl={anchorEl}
              open={Boolean(anchorEl)}
              onClose={handleCloseMenu}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'right',
              }}
              transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
            >
              <MenuItem onClick={() => { handleCloseMenu(); window.location.href = '/settings'; }}>
                <AccountCircleIcon fontSize="small" style={{ marginRight: '8px' }} />
                My Profile
              </MenuItem>
              <MenuItem onClick={handleLogout}>
                <LogoutIcon fontSize="small" style={{ marginRight: '8px' }} />
                Logout
              </MenuItem>
            </Menu>
          </>
        ) : (
          <Link to="/login" className="login-button">
            Login
          </Link>
        )}
      </div>
    </nav>
  );
};

export default Navbar;