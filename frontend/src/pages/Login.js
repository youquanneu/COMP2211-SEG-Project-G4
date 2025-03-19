import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import LoginForm from '../modules/auth/LoginForm';
import ForgotPasswordForm from '../modules/auth/ForgotPasswordForm';
import OTPForm from '../modules/auth/OTPForm';
import ChangePasswordForm from '../modules/auth/ChangePasswordForm';
import ErrorModal from '../components/ErrorModal';
import SuccessModal from '../components/SuccessModal';
import logo from '../assets/logo.png'; // Adjust path to your logo

function Login() {
  const [currentForm, setCurrentForm] = useState('login');
  const [otp, setOtp] = useState(null);
  const [isForgotFlow, setIsForgotFlow] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [showError, setShowError] = useState(false);
  const [showSuccess, setShowSuccess] = useState(false);
  const navigate = useNavigate();

  const generateOTP = () => Math.floor(100000 + Math.random() * 900000);

  const handleLogin = (email, password) => {
    if (!email || !email.includes('@') || !password || password.length < 6) {
      setErrorMessage('Please enter a valid email and password (minimum 6 characters).');
      setShowError(true);
      return;
    }
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
      // Show success message before redirecting
      setShowSuccess(true);
      setTimeout(() => {
        setShowSuccess(false);
        navigate('/dashboard');
      }, 2000); // 2-second delay to show the message
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
      setCurrentForm('login'); // Back to login after password change
      navigate('/login');
    } else {
      navigate('/dashboard'); // Redirect after login success
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