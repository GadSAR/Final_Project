import React, { useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';
import { Container, Paper, Button, Typography, MenuItem } from '@material-ui/core';
import { API_URL_Ai } from "../../constants";
import { AuthService } from '../../utils';

const useStyles = makeStyles((theme) => ({
    root: {
        '& > *': {
            margin: theme.spacing(1),
        },
    },
}));

export default function Predict() {

    const classes = useStyles();

    const [carId, setCarId] = useState('');
    const [issue, setIssue] = useState(-1);
    const [codeIssue, setCodeIssue] = useState('');
    const [nextIssue, setNextIssue] = useState('');

    const handleClick = (e) => {
        e.preventDefault();
        if (!carId) {
            setSnackbarOpen(true);
            setSnackbarSeverity('error');
            setSnackbarMessage('Please select a car');
            return;
        }
        setSnackbarOpen(true);
        setSnackbarSeverity('success');
        setSnackbarMessage('Predicting...');
        fetch(`${API_URL_Ai}/predict_car?carId=${carId}`)
            .then((res) => res.json())
            .then((result) => {
                console.log(result);
                setIssue(result.is_issue);
                setCodeIssue(result.trouble_code);
                setNextIssue(result.next_issue);
                setSnackbarOpen(true);
                setSnackbarSeverity('success');
                setSnackbarMessage('Predicted Successfully');
            })
            .catch((error) => {
                console.error('Error:', error);
                setSnackbarOpen(true);
                setSnackbarSeverity('error');
                setSnackbarMessage('Error in Predicting');
            });

    };

    const handleCopyResults = () => {
        const textToCopy = `CarID: ${carId}\nIssue?: ${issue === 1 ? 'yes' : 'no'}\nCode Issue?: ${codeIssue}\nNext Issue?: ${nextIssue}`;
        navigator.clipboard.writeText(textToCopy);
        setSnackbarOpen(true);
        setSnackbarSeverity('success');
        setSnackbarMessage('Results Copied to Clipboard');
    };

    const Alert = (props) => {
        return <MuiAlert elevation={6} variant="filled" {...props} />;
    };
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarSeverity, setSnackbarSeverity] = useState('success');
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const handleSnackbarClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        setSnackbarOpen(false);
    };

    useEffect(() => {
        AuthService.getCars()
            .then((data) => {
                setCars(data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }, []);
    const [cars, setCars] = useState([]);


    return (
        <Container className="pt-8" maxWidth="md" >
            <Typography variant="h3" align="center" gutterBottom >
                Predict using Ai
            </Typography>
            <Paper elevation={3} style={classes.Paper}>
                <form className={classes.root} align="center">
                    <div className='pt-8'>
                        <TextField
                            select
                            label="Car ID"
                            value={carId}
                            onChange={(e) => setCarId(e.target.value)}
                            style={{ width: "300px" }}
                        >
                            {cars.map((car) => (
                                <MenuItem key={car} value={car}>
                                    {car}
                                </MenuItem>
                            ))}
                        </TextField>
                    </div>
                    <div className='pt-5 pb-5'>
                        <Button variant="contained" color="secondary" onClick={handleClick}>
                            predict
                        </Button>
                    </div>
                    {issue != -1 && (
                        <div className='pt-5 pb-3'>
                            <TextField label="Issue?" value={issue === 1 ? 'yes' : 'no'} disabled />
                        </div>
                    )}
                    {issue === 1 && (
                        <div className='pt-3 pb-3'>
                            <TextField label="Which issue?" value={codeIssue} disabled />
                        </div>
                    )}
                    {issue === 1 && (
                        <div className='pt-3 pb-5'>
                            <TextField label="Next issue?" value={nextIssue} disabled />
                        </div>
                    )}
                    {issue != -1 && (
                        <Button variant="contained" color="primary" onClick={handleCopyResults}>
                            Copy Results
                        </Button>
                    )}
                </form>
            </Paper>

            <Snackbar open={snackbarOpen} autoHideDuration={3000} onClose={handleSnackbarClose}>
                <Alert severity={snackbarSeverity}>{snackbarMessage}</Alert>
            </Snackbar>
        </Container >
    );
}