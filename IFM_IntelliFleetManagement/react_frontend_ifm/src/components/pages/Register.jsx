import React, { useState } from 'react';
import { Button, Grid, Paper, TextField, Typography, InputAdornment, IconButton } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import { AuthService } from '../../utils';
import { Visibility, VisibilityOff } from '@material-ui/icons';

const useStyles = makeStyles((theme) => ({
    root: {
        minHeight: `calc(100vh - 64px - ${theme.spacing(8)}px)`,
        marginTop: theme.spacing(8),
    },
    image: {
        backgroundImage: 'url(https://i.pinimg.com/originals/72/f4/12/72f412e1bb418ed6a93507b3c0825100.gif)',
        backgroundRepeat: 'no-repeat',
        backgroundColor: theme.palette.grey[50],
        backgroundSize: 'cover',
        backgroundPosition: 'center',
    },
    paper: {
        margin: theme.spacing(4, 5),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
        backgroundColor: '#4caf50',
        color: 'white',
        '&:hover': {
            backgroundColor: '#388e3c',
        },
    },
}));

const Register = () => {
    const classes = useStyles();
    const [username, setUsername] = useState('');
    const [company, setCompany] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();

    const handleNameChange = (event) => {
        setUsername(event.target.value);
    };

    const handleCompanyChange = (event) => {
        setCompany(event.target.value);
    };

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handlePasswordToggle = () => {
        setShowPassword(!showPassword);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        AuthService.login(username, password)
            .then(
                (response) => {
                    setLoggedIn(true);
                    window.location.href = '/dashboard';
                    console.log('Response data:', response.data);
                },
                (error) => {
                    console.error('Login error:', error);
                }
            );

        await AuthService.registerAdmin(username, company, email, password)
            .then(
                (response) => {
                    console.log('Registration successful');
                    navigate('/login');
                    console.log('Response data:', response.data);
                },
                (error) => {
                    console.log('Registration failed', error);
                    clearPasswordField();
                }
            );

    };

    function clearPasswordField() {
        setPassword('');
    }

    return (
        <Grid container component="main" className={classes.root}>
            <Grid item xs={false} sm={4} md={7} className={classes.image} />
            <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
                <div className={classes.paper}>
                    <Typography variant="h4" align="center" gutterBottom>
                        Register a new account
                    </Typography>
                    <form onSubmit={handleSubmit} className={classes.form}>
                        <TextField
                            label="Username"
                            variant="outlined"
                            margin="normal"
                            fullWidth
                            autoFocus
                            required
                            type="text"
                            value={username}
                            onChange={handleNameChange}
                        />
                        <TextField
                            label="Company Name"
                            variant="outlined"
                            margin="normal"
                            fullWidth
                            required
                            type="text"
                            value={company}
                            onChange={handleCompanyChange}
                        />
                        <TextField
                            label="Email Address"
                            variant="outlined"
                            margin="normal"
                            fullWidth
                            required
                            type="email"
                            value={email}
                            onChange={handleEmailChange}
                        />
                        <TextField
                            label="Password"
                            type={showPassword ? 'text' : 'password'}
                            fullWidth
                            required
                            variant="outlined"
                            margin="normal"
                            aria-invalid="false"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            InputProps={{
                                endAdornment: (
                                    <InputAdornment position="end">
                                        <IconButton onClick={handlePasswordToggle}>
                                            {showPassword ? <Visibility /> : <VisibilityOff />}
                                        </IconButton>
                                    </InputAdornment>
                                ),
                            }}
                        />
                        <p className='mt-5'>
                            <Button
                                type="submit"
                                variant="contained"
                                color="primary"
                                fullWidth
                                className={classes.submitButton}
                            >
                                Register
                            </Button>
                        </p>
                        <p className='mt-3' align="center">
                            <Link to="/login" variant="body2" style={{ color: '#757575', fontSize: '15px', textDecoration: 'none', transition: 'color 0.3s ease' }}>
                                Already have an account?  <span style={{ color: '#c9c9ce' }}>Log in here</span>
                            </Link>
                        </p>
                    </form>
                </div>
            </Grid>
        </Grid>
    );
};

export default Register;
