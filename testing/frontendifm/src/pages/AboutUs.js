import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import {Container, Typography, Grid, Card, CardContent} from '@material-ui/core';

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
        name: 'John Doe',
        role: 'Founder and CEO',
        bio:
            'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vitae justo molestie, gravida nisi et, eleifend urna. Phasellus porttitor, velit ac malesuada ultrices, sapien arcu bibendum sapien, quis porttitor neque magna quis velit.',
    },
    {
        name: 'Jane Smith',
        role: 'Chief Operating Officer',
        bio:
            'Sed vehicula fringilla lectus ac pharetra. Maecenas sollicitudin elit a lacinia iaculis. Nam vitae posuere velit. Praesent varius, augue vel tempus faucibus, mi tellus malesuada felis, vel vestibulum metus leo nec nisi.',
    },
    {
        name: 'Bob Johnson',
        role: 'Chief Financial Officer',
        bio:
            'Duis at dolor imperdiet, consectetur est vitae, tristique mauris. Ut ultricies odio nec erat tempor interdum. Nam at eleifend purus, a euismod quam. Aliquam et ullamcorper nibh. Donec quis mauris nec eros venenatis lobortis a sed mauris.',
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
                    <Grid item xs={12} sm={6} md={4} key={index}>
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
