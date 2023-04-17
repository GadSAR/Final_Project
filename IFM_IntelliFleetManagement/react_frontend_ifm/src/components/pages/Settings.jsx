import React, { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Container, Paper, Grid, TextField, Button } from '@material-ui/core';
import SaveIcon from '@material-ui/icons/Save';
import { AuthService } from '../../utils';

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        padding: theme.spacing(3),
    },
    paper: {
        padding: theme.spacing(3),
    },
    button: {
        marginTop: theme.spacing(2),
    },
}));

const Settings = () => {
    const classes = useStyles();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [username, setUsername] = useState('');
    const [company, setCompany] = useState('');

    useEffect(() => {
        const user = AuthService.getCurrentUser();
        if (user) {
            const role = user.roles && user.roles.length > 0 ? user.roles[0] : '';
            const company = user.companies && user.companies.length > 0 ? user.companies[0] : '';
            const email = user.email || '';
            const password = user.password || '';
            setEmail(email);
            setPassword(password);
            setUsername(user.username || '');
            setCompany(company);
        }
    }, []);




    const handleSubmit = (event) => {
        event.preventDefault();
        // TODO: Add form submission logic
    };

    return (
        <Container maxWidth="md" className={classes.root}>
            <Paper className={classes.paper}>
                <form onSubmit={handleSubmit}>
                    <Grid container spacing={3}>
                        <Grid item xs={12}>
                            <TextField
                                label="Email Address"
                                type="email"
                                fullWidth
                                required
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                label="Password"
                                type="password"
                                fullWidth
                                required
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                label="Username"
                                fullWidth
                                required
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                label="Company"
                                fullWidth
                                required
                                value={company}
                                onChange={(e) => setCompany(e.target.value)}
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
