import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Container, Typography, Grid, Card, CardContent } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        padding: theme.spacing(3),
        textAlign: 'center',
    },
    title: {
        marginBottom: theme.spacing(4),
    },
    card: {
        display: 'flex',
        flexDirection: 'column',
        height: '100%',
        padding: theme.spacing(2),
        backgroundColor: theme.palette.background.paper,
    },
    cardContent: {
        flexGrow: 1,
        textAlign: 'justify',
    },
}));

const teamMembers = [
    {
        name: 'Gad Sarusi',
        role: 'Developer',
        bio:
            'A passionate developer from Israel. Always looking for new challenges and opportunities to learn and grow.',
    },
];

const AboutUs = () => {
    const classes = useStyles();

    return (
        <Container maxWidth="md" className={classes.root}>
            <Typography variant="h2" component="h1" className={classes.title}>
                About Us
            </Typography>
            <Grid container spacing={3}>
                {teamMembers.map((teamMember, index) => (
                    <Grid item xs={12} sm={6} md={12} key={index}>
                        <Card className={classes.card}>
                            <CardContent className={classes.cardContent}>
                                <Typography variant="h5" component="h2" gutterBottom>
                                    {teamMember.name}
                                </Typography>
                                <Typography variant="subtitle1" component="p" gutterBottom>
                                    {teamMember.role}
                                </Typography>
                                <Typography variant="body1" component="p">
                                    {teamMember.bio}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Container>
    );
};

export default AboutUs;
