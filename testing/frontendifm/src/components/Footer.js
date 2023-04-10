import React from "react";
import { Typography } from "@material-ui/core";
import {NavLink} from "react-router-dom";

const Footer = (props) => {
    return (
        <footer style={{ position: 'fixed', bottom: 0, width: '100%', backgroundColor: props.darkMode ? '#252628' : '#eaeaea', padding: '1.5rem' }}>
            <Typography variant="body2" color="textSecondary" align="center">
                <NavLink style={{ textDecoration: "none", color: "inherit" }} to="/contact-us" activeClassName="active">Contact Us</NavLink>
                {" "}
                |{" "}
                <NavLink style={{ textDecoration: "none", color: "inherit" }} to="/about-us" activeClassName="active">About Us</NavLink>
            </Typography>
        </footer>
    );
};

export default Footer;
