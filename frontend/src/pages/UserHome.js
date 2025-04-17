import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaUser, FaCog } from 'react-icons/fa';
import logo from '../assets/logo.png';
import campusPhoto from '../assets/campus.jpg';
import './UserHome.css';

function UserHome() {
  const navigate = useNavigate();
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const handleNavigation = (event) => {
    const selectedPage = event.target.value;
    if (selectedPage === 'userbooking') {
      navigate('/userbooking');
    } else if (selectedPage === 'userevent') {
      navigate('/userevent');
    } else if (selectedPage === 'calendar') {
      navigate('/calendar');
    } else if (selectedPage === 'emergency') {
      navigate('/emergency');
    } else if (selectedPage === 'home') {
      navigate('/userhome');
    }else if (selectedPage === 'directory') {
      navigate('/navigation');
    }
  };

  const handleLogout = () => {
    setIsDropdownOpen(false);
    localStorage.removeItem('userRole');
    localStorage.removeItem('userEmail');
    navigate('/');
  };

  const handleProfile = () => {
    setIsDropdownOpen(false);
    navigate('/userprofile');
  };

  const handleSettings = () => {
    navigate('/settings');
  };

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <img src={logo} alt="Logo" className="dashboard-logo" />
        <div className="nav-wrapper">
          <select className="nav-dropdown" onChange={handleNavigation}>
            <option value="home">Home</option>
            <option value="userevent">Events</option>
            <option value="directory">Directory</option>
            <option value="emergency">Emergency</option>
            <option value="userbooking">Booking</option>
            <option value="calendar">Calendar</option>
          </select>
        </div>
        <div className="icon-wrapper">
          <FaCog className="settings-icon" onClick={handleSettings} />
          <div className="user-menu">
            <FaUser className="user-icon" onClick={toggleDropdown} />
            {isDropdownOpen && (
              <div className="dropdown-menu">
                <div className="dropdown-item" onClick={handleProfile}>
                  My Profile
                </div>
                <div className="dropdown-item" onClick={handleLogout}>
                  Logout
                </div>
              </div>
            )}
          </div>
        </div>
      </header>
      <main className="dashboard-content">
        <h1>Welcome to University of Southampton Malaysia (UoSM)</h1>
        <p>
          The University of Southampton Malaysia (UoSM) is a branch campus of the University of Southampton, UK, a world-leading, research-intensive university and founding member of the prestigious Russell Group. Established in October 2012 at the invitation of the Malaysian Ministry of Higher Education, UoSM initially opened within the EduCity development in Iskandar, Johor. In October 2021, it relocated to a new, state-of-the-art campus in Eco Galleria, Iskandar Puteri, Johor, designed to accommodate up to 2,000 students.
        </p>
        <img src={campusPhoto} alt="UoSM Campus" className="dashboard-photo" />
        <p>
          UoSM offers a range of foundation, undergraduate, and postgraduate programs, with a strong emphasis on engineering, business, and computer science. Its split-campus programs allow students to study the first two years in Malaysia and the final two in the UK, providing a globally recognized education at a reduced cost—up to 75% savings compared to studying fully in the UK. The university is ranked 80th in the QS World University Rankings 2025, making it the top UK university in Malaysia.
        </p>
        <p>
          The Eco Galleria campus spans 150,000 square feet and features cutting-edge facilities, including a Bloomberg Trading Suite, Aerospace Lab with a wind tunnel, and multiple engineering and computer science labs. With a high employability rate—100% of graduates secure work or further study within 15 months (DiscoverUni)—UoSM prepares students for successful careers worldwide. Use the navigation above to explore more options, including booking resources!
        </p>
      </main>
    </div>
  );
}

export default UserHome;