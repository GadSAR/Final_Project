import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { createTheme, ThemeProvider } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import { AuthService } from './utils';
import { AboutUs, ContactUs, Home, Admin, Login, Register, Settings, Navbar, Footer, Predict, BusTracks, Dashboard, Error } from './components';

const App = () => {
  const [darkMode, setDarkMode] = useState(true);
  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    const checkCurrentUser = async () => {
      try {
        const user = await AuthService.getCurrentUser();
        if (user) {
          setLoggedIn(true);
        } else {
          setLoggedIn(false);
        }
      } catch (error) {
        console.error('Error checking current user:', error);
      }
    };
    const refreshToken = async () => {
      try {
        const user = await AuthService.refreshToken();
        if (user) {
          setLoggedIn(true);
        } else {
          setLoggedIn(false);
        }
      } catch (error) {
        console.error('Error checking current user:', error);
      }
    };

    checkCurrentUser();

    const intervalId = setInterval(() => {
      checkCurrentUser();
    }, 30000);

    return () => clearInterval(intervalId);
  }, []);


  const toggleDarkMode = () => {
    setDarkMode(!darkMode);
  };

  const theme = createTheme({
    palette: {
      type: darkMode ? 'dark' : 'light',
      background: {
        default: darkMode ? '#1a1c1e' : '#F7F9FB',
        paper: darkMode ? '#181a1c' : '#f5f5f5',
      },
    }
  });

  function RoutesOnly() {
    return (
      <Routes>
        <Route exact path='/' element={<Home />} />
        <Route exact path='/login' element={<Login setLoggedIn={setLoggedIn} />} />
        <Route exact path='/register' element={<Register />} />
        <Route exact path='/contact-us' element={<ContactUs />} />
        <Route exact path='/about-us' element={<AboutUs />} />
        <Route exact path='/settings' element={AuthService.getCurrentUser() ? <Settings /> : <Navigate to="/" />} />
        <Route exact path='/predict' element={AuthService.getCurrentUser() ? <Predict /> : <Navigate to="/" />} />
        <Route exact path='/bus-tracks' element={AuthService.getCurrentUser() ? <BusTracks /> : <Navigate to="/" />} />
        <Route exact path='/dashboard' element={!AuthService.isAuthenticated() ? <Dashboard /> : <Navigate to="/error" />} />
        <Route exact path="/admin" element={AuthService.isAuthenticated() ? <Admin /> : <Navigate to="/error" />} />
        <Route exact path='/error' element={<Error />} />
        <Route path="*" element={<Navigate to="/error" />} />
      </Routes>
    );
  }

  return (
    <ThemeProvider theme={theme}>

      <CssBaseline />

      <Router>

        <Navbar darkMode={darkMode} toggleDarkMode={toggleDarkMode} loggedIn={loggedIn} setLoggedIn={setLoggedIn} />

        <div className="mt-8 pb-8" style={{
          minHeight: `calc(100vh - 64px - ${theme.spacing(8)}px)`,
          marginTop: theme.spacing(8),
          paddingBottom: theme.spacing(8),
        }}>
          <RoutesOnly />
        </div>

        <Footer darkMode={darkMode} />

      </Router>

    </ThemeProvider>
  );
}

export default App
