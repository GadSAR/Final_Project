import React, { useState } from "react";
import {AppBar, Toolbar, IconButton, Typography, Menu, MenuItem, Switch, Avatar} from "@material-ui/core";
import {NavLink} from "react-router-dom";

const Navbar = (props) => {
    const [anchorEl, setAnchorEl] = useState(null);

    const handleMenuClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    function logAction() {
        props.handleLoggedOut();
        handleMenuClose();
    }

    return (
        <AppBar>
            <Toolbar>
                <Typography variant="h6" style={{ flexGrow: 1 }}>
                    <NavLink style={{ textDecoration: "none", color: "inherit" }} to="/">My Fleet Management System</NavLink>
                </Typography>
                <Switch checked={props.darkMode} onChange={props.toggleDarkMode} />
                <IconButton edge="end" color="inherit" aria-label="menu" onClick={handleMenuClick}>
                    <Avatar />
                </IconButton>
                <Menu anchorEl={anchorEl} keepMounted open={Boolean(anchorEl)} onClose={handleMenuClose} PaperProps={{
                    style: {
                        width: "200px",
                        borderRadius: "8px",
                        boxShadow: "0 2px 10px rgba(0, 0, 0, 0.2)",
                    },
                }} >
                    {props.loggedIn ? (
                        <>
                            <NavLink to="/dashboard" activeClassName="active" style={{ textDecoration: 'none', color: 'inherit' }}>
                                <MenuItem onClick={handleMenuClose}>
                                    Dashboard
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/bus-tracks" activeClassName="active" style={{ textDecoration: 'none', color: 'inherit' }}>
                                <MenuItem onClick={handleMenuClose}>
                                    Bus Tracks
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/predict" activeClassName="active" style={{ textDecoration: 'none', color: 'inherit' }}>
                                <MenuItem onClick={handleMenuClose}>
                                    Predict Issues
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/admin" activeClassName="active" style={{ textDecoration: 'none', color: 'inherit' }}>
                                <MenuItem onClick={handleMenuClose}>
                                    Admin
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/settings" activeClassName="active" style={{ textDecoration: 'none', color: 'inherit' }}>
                                <MenuItem onClick={handleMenuClose}>
                                    Settings
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/" activeClassName="active" style={{ textDecoration: 'none', color: 'inherit' }}>
                                <MenuItem onClick={logAction}>
                                    Logout
                                </MenuItem>
                            </NavLink>
                        </>
                    ) : (
                        <>
                            <NavLink to="/" activeClassName="active" style={{ textDecoration: 'none', color: 'inherit' }}>
                                <MenuItem onClick={handleMenuClose}>
                                    Home
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/login" activeClassName="active" style={{ textDecoration: 'none', color: 'inherit' }}>
                                <MenuItem onClick={handleMenuClose}>
                                    Login
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/register" activeClassName="active" style={{ textDecoration: 'none', color: 'inherit' }}>
                                <MenuItem onClick={handleMenuClose}>
                                    Register
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/contact-us" activeClassName="active" style={{ textDecoration: 'none', color: 'inherit' }}>
                                <MenuItem onClick={handleMenuClose}>
                                    Contact Us
                                </MenuItem>
                            </NavLink>
                            <NavLink to="/about-us" activeClassName="active" style={{ textDecoration: 'none', color: 'inherit' }}>
                                <MenuItem onClick={handleMenuClose}>
                                    About Us
                                </MenuItem>
                            </NavLink>
                        </>
                    )}
                </Menu>
            </Toolbar>
        </AppBar>
    );
};

export default Navbar;
