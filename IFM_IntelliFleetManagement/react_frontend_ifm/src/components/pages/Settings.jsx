import React, { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Container, Paper, Grid, TextField, Button, IconButton, InputAdornment } from '@material-ui/core';
import SaveIcon from '@material-ui/icons/Save';
import { Visibility, VisibilityOff } from '@material-ui/icons';
import { AuthService } from '../../utils';

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        padding: theme.spacing(3),
    },
    paper: {
        padding: theme.spacing(6),
    },
    button: {
        marginTop: theme.spacing(2),
    },
}));

const Settings = () => {
    const classes = useStyles();
    const [c_password, setC_password] = useState('');
    const [n_password, setN_password] = useState('');
    const [username, setUsername] = useState('');
    const [company, setCompany] = useState('');
    const [showC_password, setShowC_password] = useState(false);
    const [showN_password, setShowN_password] = useState(false);


    useEffect(() => {
        const user = AuthService.getCurrentUser();
        if (user) {
            setUsername(user.sub);
            setCompany(user.companies[0]);
        }
    }, []);


    const handleC_passwordToggle = () => {
        setShowC_password(!showC_password);
    };

    const handleN_passwordToggle = () => {
        setShowN_password(!showN_password);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        const user = AuthService.getCurrentUser();
        if (user !== null) {
            AuthService.updateUser(user.email, username, company, c_password, n_password)
                .then((response) => {
                    console.log('success update');
                    console.log(response.data);
                })
                .catch((error) => {
                    console.log(error);
                });
            setC_password('');
            setN_password('');
        }
    };

    return (
        <Container maxWidth="md" className={classes.root}>
            <Paper className={classes.paper}>
                <form onSubmit={handleSubmit}>
                    <Grid container spacing={3}>
                        <Grid item xs={12}>
                            <TextField
                                label="Username"
                                type="text"
                                fullWidth
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                autoComplete="off"
                            />
                        </Grid>
                        {AuthService.isAuthenticated() ? (
                            <Grid item xs={12}>
                                <TextField
                                    label="Company"
                                    type="text"
                                    fullWidth
                                    value={company}
                                    onChange={(e) => setCompany(e.target.value)}
                                    autoComplete="off"
                                />
                            </Grid>) : null}
                        <Grid item xs={12}>
                            <TextField
                                label="Current Password"
                                type={showC_password ? 'text' : 'password'}
                                fullWidth
                                value={c_password}
                                onChange={(e) => setC_password(e.target.value)}
                                InputProps={{
                                    endAdornment: (
                                        <InputAdornment position="end">
                                            <IconButton onClick={handleC_passwordToggle}>
                                                {showC_password ? <Visibility /> : <VisibilityOff />}
                                            </IconButton>
                                        </InputAdornment>
                                    ),
                                }}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                label="New Password"
                                type={showN_password ? 'text' : 'password'}
                                fullWidth
                                value={n_password}
                                onChange={(e) => setN_password(e.target.value)}
                                autoComplete="off"
                                InputProps={{
                                    endAdornment: (
                                        <InputAdornment position="end">
                                            <IconButton onClick={handleN_passwordToggle}>
                                                {showN_password ? <Visibility /> : <VisibilityOff />}
                                            </IconButton>
                                        </InputAdornment>
                                    ),
                                }}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Button
                                type="submit"
                                variant="contained"
                                color="primary"
                                className={classes.button}
                                startIcon={<SaveIcon />}
                            >
                                Save
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </Paper>
        </Container>
    );
};

export default Settings;
