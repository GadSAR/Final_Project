import React, {useState, useEffect} from 'react';
import {BrowserRouter as Router, Route, Routes, Navigate, useNavigate} from 'react-router-dom';
import {createTheme, ThemeProvider} from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import {AuthService} from './utils';
import { AboutUs, ContactUs, Home, Admin, Login, Register, Settings, Predict, Navbar, Footer } from './components';

const App = () => {
  const [darkMode, setDarkMode] = useState(false);
  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
      const isDarkMode = localStorage.getItem('darkMode') === 'true';
      setDarkMode(isDarkMode);
  }, []);

  const handleLoggedIn = (username, password) => {
      
    AuthService.login(username, password)
      .then(
        (response) => {
          setLoggedIn(true);
          console.log('Response data:', response.data);
        },
        (error) => {
          console.error('Login error:', error);
        }
      );
  };

  const handleLoggedOut = () => {
      setLoggedIn(false);
  };

  const toggleDarkMode = () => {
      const newDarkMode = !darkMode;
      localStorage.setItem('darkMode', newDarkMode);
      setDarkMode(newDarkMode)
  };

  const theme = createTheme({
      palette: {
          type: darkMode ? 'dark' : 'light',
          background: {
              default: darkMode ? '#1a1c1e' : '#f5f5f5',
              paper: darkMode ? '#1a1c1e' : '#ffffff',
          },
      }
  });

  function RoutesOnly() {
    return (
      <Routes>
        <Route exact path='/' element={<Home />} />
        <Route exact path='/login' element={<Login handleLoggedIn={handleLoggedIn} />} />
        <Route exact path='/register' element={<Register />} />
        <Route exact path='/contact-us' element={<ContactUs />} />
        <Route exact path='/about-us' element={<AboutUs />} />
        <Route exact path='/settings' element={<Settings />} />
        <Route path="/admin" element={AuthService.isAuthenticated() ? <Admin /> : <Navigate to="/" />} />
      </Routes>
    );
  }

  return (
      <ThemeProvider theme={theme}>

          <CssBaseline/>

          <Router>

              <Navbar darkMode={darkMode} toggleDarkMode={toggleDarkMode} loggedIn={loggedIn} handleLoggedOut={handleLoggedOut}/>

              <div className="mt-8 pb-8" style={{
                  minHeight: `calc(100vh - 64px - ${theme.spacing(8)}px)`,
                  marginTop: theme.spacing(8),
                  paddingBottom: theme.spacing(8),
              }}>
                  <RoutesOnly />
              </div>

              <Footer darkMode={darkMode}/>

          </Router>

      </ThemeProvider>
  );
}

export default App
