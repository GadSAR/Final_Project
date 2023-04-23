import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Container, Typography, TextField, Button } from '@material-ui/core';
import { API_URL_RabbitMQ } from "../../constants";

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

const ContactUs = () => {
    const classes = useStyles();
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [subject, setSubject] = useState('');
    const [message, setMessage] = useState('');


    const handleNameChange = (event) => {
        setName(event.target.value);
    };

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    };

    const handleSubjectChange = (event) => {
        setSubject(event.target.value);
    };

    const handleMessageChange = (event) => {
        setMessage(event.target.value);
    };

    const clearFields = () => {
        setName('');
        setEmail('');
        setSubject('');
        setMessage('');
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        fetch(`${API_URL_RabbitMQ}/contact-us`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name, email, subject, message })
        })
            .then((res) => {
                console.log(res);
                clearFields();
            })
            .catch((error) => {
                console.error('Error:', error);
            });
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
                    value={name}
                    onChange={handleNameChange}
                />
                <TextField
                    id="email"
                    label="Email"
                    type="email"
                    variant="outlined"
                    className={classes.textField}
                    required
                    value={email}
                    onChange={handleEmailChange}
                />
                <TextField
                    id="subject"
                    label="Subject"
                    type="text"
                    variant="outlined"
                    className={classes.textField}
                    required
                    value={subject}
                    onChange={handleSubjectChange}
                />
                <TextField
                    id="message"
                    label="Message"
                    variant="outlined"
                    multiline
                    rows={4}
                    className={classes.textField}
                    required
                    value={message}
                    onChange={handleMessageChange}
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
