import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import LoginForm from '../modules/auth/LoginForm';
import ForgotPasswordForm from '../modules/auth/ForgotPasswordForm';
import OTPForm from '../modules/auth/OTPForm';
import ChangePasswordForm from '../modules/auth/ChangePasswordForm';
import ErrorModal from '../components/ErrorModal';
import SuccessModal from '../components/SuccessModal';
import logo from '../assets/logo.png';
import {getAPI_URL} from "../services/api";

function Login() {
  const [currentForm, setCurrentForm] = useState('login');
  const [otp, setOtp] = useState(null);
  const [isForgotFlow, setIsForgotFlow] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [showError, setShowError] = useState(false);
  const [showSuccess, setShowSuccess] = useState(false);
  const [userEmail, setUserEmail] = useState('');
  const [resetToken, setResetToken] = useState(''); 
  const navigate = useNavigate();

  useEffect(() => {
    const root = document.documentElement;
    root.style.setProperty('--background-color', '#fff');
    root.style.setProperty('--text-color', '#333');
    root.style.setProperty('--secondary-text-color', '#555');
    root.style.setProperty('--button-bg-start', '#0077B6');
    root.style.setProperty('--button-bg-end', '#005888');
    root.style.setProperty('--border-color', '#ddd');
    root.style.setProperty('--shadow-color', 'rgba(0, 0, 0, 0.1)');
  }, []);

  // Base API URL (adjust to your backend)
  const API_URL = 'http://localhost:8080/api';

  const handleLogin = async (email, password) => {
    try {
      if (!email || !email.includes('@') || !password || password.length < 6) {
        setErrorMessage('Please enter a valid email and password (minimum 6 characters).');
        setShowError(true);
        return;
      }

      const response = await axios.post(getAPI_URL("auth/login"), { email, password });
      const { token, userDTO } = response.data;

      // Store token and role
      localStorage.setItem('token', token);
      console.log("set token : " + token);
      localStorage.setItem('userRole', userDTO.UserRole);
      console.log("set userRole : " + userDTO.userRole);

      setUserEmail(email);
      setOtp(response.data.otp || null); // If backend sends OTP
      setIsForgotFlow(false);

      const root = document.documentElement;
      localStorage.setItem('darkTheme', 'false');
      root.style.setProperty('--background-color', '#fff');
      root.style.setProperty('--text-color', '#333');
      root.style.setProperty('--secondary-text-color', '#555');
      root.style.setProperty('--button-bg-start', '#0077B6');
      root.style.setProperty('--button-bg-end', '#005888');
      root.style.setProperty('--border-color', '#ddd');
      root.style.setProperty('--shadow-color', 'rgba(0, 0, 0, 0.1)');

      setShowSuccess(true);
      setTimeout(() => {
        setShowSuccess(false);
        navigate(userDTO.userRole === 'AdministrativeStaff' ? '/adminhome' : '/userhome');
      }, 2000);
    } catch (error) {
    console.log(error)
      setErrorMessage(error.response?.data || 'Login failed. Please try again.');
      setShowError(true);
    }
  };

  const handleForgotPassword = async (email) => {
    try {
      if (!email || !email.includes('@')) {
        setErrorMessage('Please enter a valid email.');
        setShowError(true);
        return;
      }

      const response = await axios.post(`${API_URL}/auth/forgot-password`, { email });
      setUserEmail(email);
      setOtp(response.data.otp || null); // Adjust based on backend
      setIsForgotFlow(true);
      setCurrentForm('otp');
    } catch (error) {
      setErrorMessage(error.response?.data?.message || 'Failed to send OTP. Please try again.');
      setShowError(true);
    }
  };

  const handleOTPVerify = async (enteredOTP) => {
    try {
      if (!enteredOTP || enteredOTP.length !== 6) {
        setErrorMessage('Please enter a valid 6-digit OTP.');
        setShowError(true);
        return;
      }

      const response = await axios.post(`${API_URL}/auth/verify-otp`, {
        email: userEmail,
        otp: enteredOTP,
      });

      setOtp(null);
      if (isForgotFlow) {
        setResetToken(response.data.resetToken || ''); // Store reset token
        setCurrentForm('changePassword');
      } else {
        // For login flow, navigate (if backend requires OTP for login)
        const { token, user } = response.data;
        localStorage.setItem('token', token);
        localStorage.setItem('userRole', user.role);

        const root = document.documentElement;
        localStorage.setItem('darkTheme', 'false');
        root.style.setProperty('--background-color', '#fff');
        root.style.setProperty('--text-color', '#333');
        root.style.setProperty('--secondary-text-color', '#555');
        root.style.setProperty('--button-bg-start', '#0077B6');
        root.style.setProperty('--button-bg-end', '#005888');
        root.style.setProperty('--border-color', '#ddd');
        root.style.setProperty('--shadow-color', 'rgba(0, 0, 0, 0.1)');

        setShowSuccess(true);
        setTimeout(() => {
          setShowSuccess(false);
          navigate(user.role === 'admin' ? '/adminhome' : '/userhome');
        }, 2000);
      }
    } catch (error) {
      setErrorMessage(error.response?.data?.message || 'Invalid OTP. Please try again.');
      setShowError(true);
    }
  };

  const handlePasswordUpdate = async (newPassword, confirmPassword) => {
    try {
      if (!newPassword || newPassword.length < 6 || newPassword !== confirmPassword) {
        setErrorMessage('Passwords must match and be at least 6 characters.');
        setShowError(true);
        return;
      }

      await axios.post(`${API_URL}/auth/change-password`, {
        email: userEmail,
        resetToken,
        newPassword,
      });

      setResetToken('');
      setShowSuccess(true);
    } catch (error) {
      setErrorMessage(error.response?.data?.message || 'Failed to update password. Please try again.');
      setShowError(true);
    }
  };

  const closeErrorModal = () => setShowError(false);

  const closeSuccessModal = () => {
    setShowSuccess(false);
    if (isForgotFlow) {
      setCurrentForm('login');
      navigate('/login');
    }
  };

  return (
    <div className="container">
      <img src={logo} alt="Logo" className="login-logo" />
      {currentForm === 'login' && <LoginForm onLogin={handleLogin} onForgot={() => setCurrentForm('forgot')} />}
      {currentForm === 'forgot' && <ForgotPasswordForm onSubmit={handleForgotPassword} onBack={() => setCurrentForm('login')} />}
      {currentForm === 'otp' && <OTPForm otp={otp} onVerify={handleOTPVerify} />}
      {currentForm === 'changePassword' && <ChangePasswordForm onUpdate={handlePasswordUpdate} />}
      {showError && <ErrorModal message={errorMessage} onClose={closeErrorModal} />}
      {showSuccess && (
        <SuccessModal
          onClose={closeSuccessModal}
          message={isForgotFlow ? 'Password Updated Successfully' : 'Login Successfully'}
        />
      )}
    </div>
  );
}

export default Login;