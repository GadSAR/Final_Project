import React, {useState, useEffect, memo} from 'react';
import {BrowserRouter as Router, redirect, Route, Routes} from 'react-router-dom';
import {createMuiTheme, ThemeProvider} from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import ContactUs from './pages/ContactUs';
import AboutUs from './pages/AboutUs';
import Predict from './pages/Predict';
import Settings from './pages/Settings';
import Admin from './pages/Admin';


function App() {
    const [darkMode, setDarkMode] = useState(false);
    const [loggedIn, setLoggedIn] = useState(false);

    useEffect(() => {
        const isDarkMode = localStorage.getItem('darkMode') === 'true';
        setDarkMode(isDarkMode);
    }, []);

    const handleLoggedIn = () => {
        setLoggedIn(true);
        redirect('/settings');
    };

    const handleLoggedOut = () => {
        setLoggedIn(false);
    };

    const toggleDarkMode = () => {
        const newDarkMode = !darkMode;
        localStorage.setItem('darkMode', newDarkMode);
        setDarkMode(newDarkMode)
    };

    const theme = createMuiTheme({
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
                <Route exact path='/' element={<Home />}/>
                <Route exact path='/login' element={<Login handleLoggedIn={handleLoggedIn}/>}/>
                <Route exact path='/register' element={<Register />}/>
                <Route exact path='/contact-us' element={<ContactUs />}/>
                <Route exact path='/about-us' element={<AboutUs />}/>
                <Route exact path='/predict' element={<Predict />}/>
                <Route exact path='/settings' element={<Settings />}/>
                <Route exact path='/admin' element={<Admin />}/>
            </Routes>
        );
    }

    return (
        <ThemeProvider theme={theme}>

            <CssBaseline/>

            <Router>

                <Navbar darkMode={darkMode} toggleDarkMode={toggleDarkMode} loggedIn={loggedIn} handleLoggedOut={handleLoggedOut}/>

                <div style={{
                    minHeight: `calc(100vh - 64px - ${theme.spacing(8)}px)`,
                    marginTop: theme.spacing(8),
                    paddingBottom: theme.spacing(8),
                }}>
                    <RoutesOnly/>
                </div>

                <Footer darkMode={darkMode}/>

            </Router>

        </ThemeProvider>
    );
}

export default App;
