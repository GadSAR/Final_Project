import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Container, Typography, Button } from '@material-ui/core';
import { Link } from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        padding: theme.spacing(3),
        textAlign: 'center',
    },
    title: {
        marginBottom: theme.spacing(4),
    },
    button: {
        marginTop: theme.spacing(4),
    },
}));

const Home = () => {
    const classes = useStyles();

    return (
        <Container maxWidth="md" className={classes.root}>
            <Typography variant="h2" component="h1" className={classes.title}>
                Welcome to Fleet Management
            </Typography>
            <Typography variant="h6" component="p">
                Manage your fleet with ease using our advanced fleet management system.
            </Typography>
            <Button
                variant="contained"
                color="primary"
                component={Link}
                to="/login"
                className={classes.button}
            >
                Get Started
            </Button>
        </Container>
    );
};

export default Home;
