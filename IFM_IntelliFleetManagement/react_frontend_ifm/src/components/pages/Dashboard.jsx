import React, { useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Container, Paper, Typography } from '@material-ui/core';
import { API_URL_Backend } from '../../constants';

const useStyles = makeStyles((theme) => ({
  root: {
    '& > *': {
      margin: theme.spacing(1),
    },
  },
}));

function DriverDashboard() {

  const classes = useStyles();

  const [driverInfo, setDriverInfo] = useState({});

  useEffect(() => {
    // Fetch driver info from API and update state
    fetch(`${API_URL_Backend}/driver`)
      .then(response => response.json())
      .then(data => {
        setDriverInfo(data);
      })
      .catch(error => console.error(error));
  }, []);

  return (
    <Container className="pt-8" maxWidth="sm" >
      <Typography variant="h3" align="center" gutterBottom >
        Driver Dashboard
      </Typography>
      <Paper className={classes.root} elevation={3}>
        <Typography variant="h5" gutterBottom>
          My Info
        </Typography>
        <Typography variant="body1" gutterBottom>
          Name: {driverInfo.name}
        </Typography>
      </Paper>
      <Paper className={classes.root} elevation={3}>
        <Typography variant="h5" gutterBottom>
          My Trips
        </Typography>
        {driverInfo.trips && driverInfo.trips.map(trip => (
          <div key={trip.id}>
            <Typography variant="body1" gutterBottom>
              Trip ID: {trip.id}
            </Typography>
            <Typography variant="body1" gutterBottom>
              Vehicle: {trip.vehicle}
            </Typography>
            <Typography variant="body1" gutterBottom>
              Start Time: {trip.startTime}
            </Typography>
            <Typography variant="body1" gutterBottom>
              End Time: {trip.endTime}
            </Typography>
          </div>
        ))}
      </Paper>
    </Container>
  );
}

export default DriverDashboard;