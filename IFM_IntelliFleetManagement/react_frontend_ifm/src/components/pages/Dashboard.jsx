import React, { useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import {
  Button, Container, Paper, Typography, Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField
} from '@material-ui/core';
import { API_URL_Backend, API_URL_RabbitMQ } from '../../constants';
import { AuthService } from '../../utils';
import { DataGrid } from '@material-ui/data-grid';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    paddingTop: theme.spacing(4),
  },
  paper: {
    padding: theme.spacing(5),
  },
  button: {
    paddingTop: theme.spacing(2),
    paddingBottom: theme.spacing(2),
  },
}));

function DriverDashboard() {

  const classes = useStyles();

  const [driverName, setDriverName] = useState('');
  const [lastDrive, setLastDrive] = useState([]);
  const [drivermail, setDriverMail] = useState('');
  const [managermail, setManagerMail] = useState('');
  const [mailSubject, setMailSubject] = useState('');
  const [mailMessage, setMailMessage] = useState('');
  const [open, setOpen] = useState(false);

  const handleClose = () => {
    setOpen(false);
    setMailSubject('');
    setMailMessage('');
  }

  const handleMail = () => {
    event.preventDefault();

    if (!mailSubject || !mailMessage) {
      alert("Please fill in all required fields.");
      return;
    }

    fetch(`${API_URL_RabbitMQ}/mailToAdmin`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        name: driverName, sender: drivermail, recipient: managermail,
        subject: mailSubject, message: mailMessage
      })
    })
      .then((res) => {
        console.log(res);
        handleClose()
        setSnackbarOpen(true);
        setSnackbarSeverity('success');
        setSnackbarMessage('Message sent successfully!');
      })
      .catch((error) => {
        console.error('Error:', error);
        setSnackbarOpen(true);
        setSnackbarSeverity('error');
        setSnackbarMessage('Failed to send message.');
      });
  };

  useEffect(() => {
    const user = AuthService.getCurrentUser();
    if (user) {
      setDriverName(user.sub);
      setDriverMail(user.email);
    }

    fetch(`${API_URL_Backend}/get_user_lastdrive?email=${user.email}`)
      .then((response) => response.json())
      .then((data) => {
        setLastDrive(data);
      })
      .catch((error) => {
        console.error('Error:', error);
      });

    fetch(`${API_URL_Backend}/get_user_managermail?email=${user.email}`)
      .then((response) => response.json())
      .then((data) => {
        setManagerMail(data);
      })
      .catch((error) => {
        console.error('Error:', error);
      });

  }, []);

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

  const columns = [
    { field: 'vehicle_ID', headerName: 'Vehicle Id', width: 150 },
    { field: 'speed', headerName: 'Speed', width: 175 },
    { field: 'throttle_POS', headerName: 'Throttle Pos', width: 175 },
    { field: 'engine_RPM', headerName: 'Engine Rpm', width: 175 },
    { field: 'engine_LOAD', headerName: 'Engine Load', width: 175 },
    { field: 'engine_COOLANT_TEMP', headerName: 'Engine Temp', width: 175 },
    { field: 'maf', headerName: 'Maf', width: 175 },
    { field: 'fuel_LEVEL', headerName: 'Fuel Level', width: 175 },
    { field: 'fuel_PRESSURE', headerName: 'Fuel Pressure', width: 175 },
    { field: 'timing_ADVANCE', headerName: 'Timing Advance', width: 175 },
    { field: 'time', headerName: 'Time', width: 250 },
  ];

  return (
    <Container className={classes.root} maxWidth="md" >
      <Typography variant="h3" align="center" gutterBottom >
        Dashboard
      </Typography>
      <Paper className="pt-5 pl-4" align="center" elevation={3}>
        <Typography variant="h5" gutterBottom>
          {driverName}'s Last Info:
        </Typography>
        <div className='pt-3' style={{ height: 200, width: '100%' }}>
          <DataGrid rows={lastDrive} columns={columns} pageSize={1} />
        </div>
        <div className={classes.button}>
          <Button onClick={() => setOpen(true)}>
            Mail To Admin
          </Button >
        </div>
      </Paper>

      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="form-dialog-title"
      >
        <DialogTitle id="form-dialog-title">
          Send Mail
        </DialogTitle>
        <DialogContent className={classes.dialog}>
          <TextField
            margin="dense"
            name="subject"
            label="Subject"
            type="text"
            required
            value={mailSubject}
            onChange={(event) => setMailSubject(event.target.value)}
            fullWidth
          />
          <TextField
            margin="dense"
            name="message"
            label="Message"
            type="text"
            multiline
            minRows={5}
            required
            value={mailMessage}
            onChange={(event) => setMailMessage(event.target.value)}
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancel
          </Button>
          <Button onClick={handleMail} color="primary">
            Send
          </Button>
        </DialogActions>
      </Dialog>

      <Snackbar open={snackbarOpen} autoHideDuration={3000} onClose={handleSnackbarClose}>
        <Alert severity={snackbarSeverity}>{snackbarMessage}</Alert>
      </Snackbar>
    </Container>
  );
}

export default DriverDashboard;