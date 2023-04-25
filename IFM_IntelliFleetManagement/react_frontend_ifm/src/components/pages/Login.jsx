import React, { useState } from 'react';
import { Button, Grid, Paper, TextField, Typography, InputAdornment, IconButton } from '@material-ui/core';
import { Visibility, VisibilityOff } from '@material-ui/icons';
import { makeStyles } from '@material-ui/core/styles';
import { Link, Navigate, useNavigate } from 'react-router-dom';
import { AuthService } from '../../utils';


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

const Login = (props) => {
    const classes = useStyles();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handlePasswordToggle = () => {
        setShowPassword(!showPassword);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        AuthService.login(email, password)
            .then((response) => {
                if (response === 'error') {
                    clearPasswordField();
                    console.log(`error Logging in`);
                }
                else {
                    console.log(`Logging in with email: ${email}`);
                    props.setLoggedIn(true);
                    navigate('/settings');
                }
            });
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
                        Log in to your account
                    </Typography>
                    <form onSubmit={handleSubmit} className={classes.form}>
                        <TextField
                            label="Email Address"
                            variant="outlined"
                            margin="normal"
                            fullWidth
                            autoFocus
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
                                Log in
                            </Button>

                        </p>
                        <p className='mt-3' align="center">
                            <Link to="/register" variant="body2" style={{
                                color: '#757575',
                                fontSize: '15px',
                                textDecoration: 'none',
                                transition: 'color 0.3s ease'
                            }}>
                                Don't have an account? <span style={{ color: '#c9c9ce' }}>Register here</span>
                            </Link>
                        </p>
                    </form>
                </div>
            </Grid>
        </Grid>
    );
};

export default Login;
