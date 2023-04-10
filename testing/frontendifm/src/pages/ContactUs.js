import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Container, Typography, TextField, Button } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        padding: theme.spacing(3),
        textAlign: 'center',
    },
    title: {
        marginBottom: theme.spacing(4),
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    textField: {
        margin: theme.spacing(1),
        width: '100%',
    },
    button: {
        margin: theme.spacing(2),
    },
}));

const ContactUs = (props) => {
    const classes = useStyles(props.darkMode);

    const handleSubmit = (event) => {
        event.preventDefault();
        // Code to handle form submission
    };

    return (
        <Container maxWidth="md" className={classes.root}>
            <Typography variant="h2" component="h1" className={classes.title}>
                Contact Us
            </Typography>
            <form onSubmit={handleSubmit} className={classes.form}>
                <TextField
                    id="name"
                    label="Name"
                    variant="outlined"
                    className={classes.textField}
                    required
                />
                <TextField
                    id="email"
                    label="Email"
                    type="email"
                    variant="outlined"
                    className={classes.textField}
                    required
                />
                <TextField
                    id="message"
                    label="Message"
                    variant="outlined"
                    multiline
                    rows={4}
                    className={classes.textField}
                    required
                />
                <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    className={classes.button}
                >
                    Send Message
                </Button>
            </form>
        </Container>
    );
};

export default ContactUs;
