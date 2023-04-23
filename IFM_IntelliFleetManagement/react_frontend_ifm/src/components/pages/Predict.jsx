import React, { useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { Container, Paper, Button } from '@material-ui/core';
import { API_URL_Ai } from "../../constants";

const useStyles = makeStyles((theme) => ({
    root: {
        '& > *': {
            margin: theme.spacing(1),
        },
    },
}));

export default function Student() {
    const paperStyle = { margin: 'auto', padding: '50px 20px', width: '50%' };
    const [id, setId] = useState('');
    const [model, setModel] = useState('');
    const classes = useStyles();

    const handleClick = (e) => {
        e.preventDefault();
        fetch(`${API_URL_Ai}/model1/predict`)
            .then((res) => res.json())
            .then((result) => {
                console.log(result);
            });
    };

    useEffect(() => {

    }, []);

    return (
        <Container>
            <Paper elevation={3} style={paperStyle}>
                <h1 style={{ color: 'blue' }}>
                    <u>Choose a models properties</u>
                </h1>

                <form className={classes.root} noValidate autoComplete="off">
                    <TextField
                        id="outlined-basic"
                        label="Model id"
                        variant="outlined"
                        fullWidth
                        value={model}
                        onChange={(e) => setModel(e.target.value)}
                    />
                    <Button variant="contained" color="secondary" onClick={handleClick}>
                        predict with model_{model}
                    </Button>
                </form>
            </Paper>
        </Container>
    );
}
