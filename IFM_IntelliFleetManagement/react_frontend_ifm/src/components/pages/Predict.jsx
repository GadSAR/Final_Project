import React, { useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { Container, Paper, Button, Typography } from '@material-ui/core';
import { API_URL_Ai } from "../../constants";

const useStyles = makeStyles((theme) => ({
    root: {
        '& > *': {
            margin: theme.spacing(1),
        },
    },
}));

export default function Student() {

    const classes = useStyles();

    const handleClick1 = (e) => {
        e.preventDefault();
        fetch(`${API_URL_Ai}/model1/predict`)
            .then((res) => res.json())
            .then((result) => {
                console.log(result);
            });
    };

    const handleClick2 = (e) => {
        e.preventDefault();
        fetch(`${API_URL_Ai}/model2/predict`)
            .then((res) => res.json())
            .then((result) => {
                console.log(result);
            });
    };

    const handleClick3 = (e) => {
        e.preventDefault();
        fetch(`${API_URL_Ai}/model3/predict`)
            .then((res) => res.json())
            .then((result) => {
                console.log(result);
            });
    };

    const handleClick4 = (e) => {
        e.preventDefault();
        fetch(`${API_URL_Ai}/predict`)
            .then((res) => res.json())
            .then((result) => {
                console.log(result);
            });
    };


    return (
        <Container className="pt-8" maxWidth="sm" >
            <Typography variant="h3" align="center" gutterBottom >
                Predict using Ai
            </Typography>
            <Paper elevation={3} style={classes.Paper}>
                <form className={classes.root} align="center">
                    <div className='pt-8'>
                        <Button variant="contained" color="secondary" onClick={handleClick1}>
                            predict with model1
                        </Button>
                    </div>
                    <div className='pt-5'>
                        <Button variant="contained" color="secondary" onClick={handleClick2}>
                            predict with model2
                        </Button>
                    </div>
                    <div className='pt-5 pb-5'>
                        <Button variant="contained" color="secondary" onClick={handleClick3}>
                            predict with model3
                        </Button>
                    </div>
                    <div className='pt-5 pb-5'>
                        <Button variant="contained" color="secondary" onClick={handleClick4}>
                            predict with all models
                        </Button>
                    </div>
                </form>
            </Paper>
        </Container>
    );
}
