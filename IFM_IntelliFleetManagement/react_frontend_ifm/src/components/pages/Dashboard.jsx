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

export default function Dashboard() {

  const classes = useStyles();


  return (
    <Container className="pt-8" maxWidth="sm" >
      <Typography variant="h3" align="center" gutterBottom >
        My Dashboard
      </Typography>
      <Paper elevation={3} style={classes.Paper}>
        <form className={classes.root} align="center">
        </form>
      </Paper>
    </Container>
  );
}
