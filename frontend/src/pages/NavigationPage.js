import React from 'react';
import { Box } from '@mui/material';
import NavigationMap from '../components/NavigationMap';
import logo from '../assets/logo.png';
import './NavigationPage.css';

const NavigationPage = () => {
    return (
        <Box className="navigation-page-container">
            <Box sx={{ textAlign: 'center', mb: 3 }}>
                <img src={logo} alt="Logo" style={{ width: '120px', height: 'auto' }} />
            </Box>
            <NavigationMap />
        </Box>
    );
};

export default NavigationPage; 