// src/pages/Login.js
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import LoginForm from '../modules/auth/LoginForm';
import ForgotPasswordForm from '../modules/auth/ForgotPasswordForm';
import OTPForm from '../modules/auth/OTPForm';
import ChangePasswordForm from '../modules/auth/ChangePasswordForm';
import ErrorModal from '../components/ErrorModal';
import SuccessModal from '../components/SuccessModal';
import logo from '../assets/logo.png';

function Login() {
  const [currentForm, setCurrentForm] = useState('login');
  const [otp, setOtp] = useState(null);
  const [isForgotFlow, setIsForgotFlow] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [showError, setShowError] = useState(false);
  const [showSuccess, setShowSuccess] = useState(false);
  const [userEmail, setUserEmail] = useState('');
  const navigate = useNavigate();

  // Force light theme on login page mount
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

  // Simulated admin emails (in a real app, this would come from a backend)
  const adminEmails = ['admin@example.com', 'admin2@example.com'];

  const generateOTP = () => Math.floor(100000 + Math.random() * 900000);

  const handleLogin = (email, password) => {
    if (!email || !email.includes('@') || !password || password.length < 6) {
      setErrorMessage('Please enter a valid email and password (minimum 6 characters).');
      setShowError(true);
      return;
    }
    setUserEmail(email);
    setOtp(generateOTP());
    setIsForgotFlow(false);
    setCurrentForm('otp');
  };

  const handleForgotPassword = (email) => {
    if (!email || !email.includes('@')) {
      setErrorMessage('Please enter a valid email.');
      setShowError(true);
      return;
    }
    setUserEmail(email);
    setOtp(generateOTP());
    setIsForgotFlow(true);
    setCurrentForm('otp');
  };

  const handleOTPVerify = (enteredOTP) => {
    if (!enteredOTP || enteredOTP.length !== 6 || parseInt(enteredOTP) !== otp) {
      setErrorMessage('Please enter a valid 6-digit OTP.');
      setShowError(true);
      return;
    }
    setOtp(null);
    if (isForgotFlow) {
      setCurrentForm('changePassword');
    } else {
      // Determine user role
      const userRole = adminEmails.includes(userEmail.toLowerCase()) ? 'admin' : 'user';

      // Store the role in localStorage
      localStorage.setItem('userRole', userRole);

      // Reset to light theme on successful login
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
        if (userRole === 'admin') {
          navigate('/adminhome');
        } else {
          navigate('/userhome');
        }
      }, 2000);
    }
  };

  const handlePasswordUpdate = (newPassword, confirmPassword) => {
    if (!newPassword || newPassword.length < 6 || newPassword !== confirmPassword) {
      setErrorMessage('Passwords must match and be at least 6 characters.');
      setShowError(true);
      return;
    }
    setShowSuccess(true);
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