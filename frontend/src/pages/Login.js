import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
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
  const [otpPrefix, setOtpPrefix] = useState(null);
  const [isForgotFlow, setIsForgotFlow] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [showError, setShowError] = useState(false);
  const [showSuccess, setShowSuccess] = useState(false);
  const navigate = useNavigate();
  const emailRef = useRef(null);

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

   const generateOTP = () => Math.floor(100000 + Math.random() * 900000);

  const handleLogin = async (email, password) => {
    if (!email || !email.includes('@') || !password || password.length < 6) {
      setErrorMessage('Please enter a valid email and password (minimum 6 characters).');
      setShowError(true);
      return;
    }   // Check user input of email and password
    try {
      const response = await fetch(getAPI_URL("user/login"), {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });   // Post data to backend
      if (response.ok) {
        const data = await response.json();         // Get object json data
        console.log('Account matches successful : ', data)
        const otpPrefix = await sendOTP(data.email) // Send OTP through email
        console.log(otpPrefix)
        setOtpPrefix(otpPrefix)
        setCurrentForm('otp')
      }
      else {
        const error = await response.text();
        console.error('Login failed:', error);
        setErrorMessage(error || 'Login failed. Please try again.');
        setShowError(true);
      }
    }
    catch (error) {
      console.error('Error during login:', error);
      setErrorMessage('Failed to connect to the server.');
      setShowError(true);
    }
  };
  async function sendOTP (email){
      const otpResponse = await fetch(getAPI_URL("user/getOtp"),{
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email }),
      })
      if(!otpResponse.ok){
        const error = await otpResponse.text();
      }
      if(emailRef) {
        emailRef.current.value = email
      }
      return otpResponse.text()
  }
  const handleOTPVerify = (email,enteredOTP) => {
    console.log(enteredOTP)
    if (!enteredOTP || enteredOTP.length !== 6) {
      setErrorMessage('Please enter a valid 6-digit OTP.');
      setShowError(true);
      return;
    }
    //verifying...
    const otpMatching= matchOTP(emailRef.current.value,enteredOTP)
    if(otpMatching) {
      setOtpPrefix(null);
      if (isForgotFlow) {
        setCurrentForm('changePassword');
      }
      else {
        // Reset to light theme on successful login
        const root = document.documentElement;
        localStorage.setItem('darkTheme', 'false'); // Reset to light in storage
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
          navigate('/userhome');
        }, 2000); // 2-second delay to show the message
      }
    }
  };
  async function matchOTP (email,enteredOTP) {
      const response = await fetch(getAPI_URL("user/matchOtp"),{
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({email,enteredOTP}),
      })
      if(!response.ok){
        const error = await response.text();
      }
      return response.text
    }

  const handleForgotPassword = (email) => {
    if (!email || !email.includes('@')) {
      setErrorMessage('Please enter a valid email.');
      setShowError(true);
      return;
    }
    setOtpPrefix(generateOTP());
    setIsForgotFlow(true);
    setCurrentForm('otp');
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
      setCurrentForm('login'); // Back to login after password change
      navigate('/login');
    } else {
      navigate('/userhome'); // Already handled in handleOTPVerify
    }
  };

  return (
    <div className="container">
      <img src={logo} alt="Logo" className="login-logo" />
      <input type="hidden" ref={emailRef}/>
      {currentForm === 'login' && <LoginForm onLogin={handleLogin} onForgot={() => setCurrentForm('forgot')} />}
      {currentForm === 'forgot' && <ForgotPasswordForm onSubmit={handleForgotPassword} onBack={() => setCurrentForm('login')} />}
      {currentForm === 'otp' && <OTPForm otp={otpPrefix} onVerify={handleOTPVerify} />}
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