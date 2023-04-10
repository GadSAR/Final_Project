import React, {useState} from 'react';
import {Button, Grid, Paper, TextField, Typography} from '@material-ui/core';
import {makeStyles} from '@material-ui/core/styles';
import {Link} from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
    root: {
        minHeight: `calc(100vh - 64px - ${theme.spacing(8)}px)`,
        marginTop: theme.spacing(8),
    },
    image: {
        backgroundImage: 'url(https://source.unsplash.com/random)',
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
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleNameChange = (event) => {
        setName(event.target.value);
    };

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        // Handle registration logic here
        console.log(`Registering with name: ${name}, email: ${email}, and password: ${password}`);
        clear();
    };

    function clear(){
        setPassword('');
    }

    return (
        <Grid container component="main" className={classes.root}>
            <Grid item xs={false} sm={4} md={7} className={classes.image}/>
            <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
                <div className={classes.paper}>
                    <Typography variant="h4" align="center" gutterBottom>
                        Register a new account
                    </Typography>
                    <form onSubmit={handleSubmit} className={classes.form}>
                        <TextField
                            label="Name"
                            variant="outlined"
                            margin="normal"
                            fullWidth
                            autoFocus
                            required
                            type="text"
                            value={name}
                            onChange={handleNameChange}
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
                            variant="outlined"
                            margin="normal"
                            fullWidth
                            required
                            type="password"
                            value={password}
                            onChange={handlePasswordChange}
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
