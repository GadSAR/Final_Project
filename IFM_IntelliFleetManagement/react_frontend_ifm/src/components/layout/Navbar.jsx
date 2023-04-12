import React, { useState } from "react";
import {AppBar, Toolbar, IconButton, Typography, Menu, MenuItem, Switch, Avatar, useMediaQuery} from "@material-ui/core";
import {NavLink} from "react-router-dom";
import AuthService from 'C:/Users/sarga/Desktop/College/Second_year/Final_Project/IFM_IntelliFleetManagement/react_frontend_ifm/src/utils/AuthService.jsx';


const Navbar = (props) => {
  const [anchorEl, setAnchorEl] = useState(null);
  const isSmallScreen = useMediaQuery("(max-width: 600px)");

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

  const menuItems = props.loggedIn ? (
    [
      <NavLink key="dashboard" to="/dashboard" activeclassname="active" style={{ textDecoration: 'none', color: 'inherit' }}>
        <MenuItem onClick={handleMenuClose}>
          Dashboard
        </MenuItem>
      </NavLink>,
      <NavLink key="bus-tracks" to="/bus-tracks" activeclassname="active" style={{ textDecoration: 'none', color: 'inherit' }}>
        <MenuItem onClick={handleMenuClose}>
          Bus Tracks
        </MenuItem>
      </NavLink>,
      <NavLink key="predict" to="/predict" activeclassname="active" style={{ textDecoration: 'none', color: 'inherit' }}>
        <MenuItem onClick={handleMenuClose}>
          Predict Issues
        </MenuItem>
      </NavLink>,
      AuthService.isAuthenticated() && (
        <NavLink key="admin" to="/admin" activeclassname="active" style={{ textDecoration: 'none', color: 'inherit' }}>
          <MenuItem onClick={handleMenuClose}>
            Admin
          </MenuItem>
        </NavLink>
      ),
      <NavLink key="settings" to="/settings" activeclassname="active" style={{ textDecoration: 'none', color: 'inherit' }}>
        <MenuItem onClick={handleMenuClose}>
          Settings
        </MenuItem>
      </NavLink>,
      <NavLink key="logout" to="/" activeclassname="active" style={{ textDecoration: 'none', color: 'inherit' }}>
        <MenuItem onClick={logAction}>
          Logout
        </MenuItem>
      </NavLink>
    ]
  ) : (
    [
      <NavLink key="home" to="/" activeclassname="active" style={{ textDecoration: 'none', color: 'inherit' }}>
        <MenuItem onClick={handleMenuClose}>
          Home
        </MenuItem>
      </NavLink>,
      <NavLink key="login" to="/login" activeclassname="active" style={{ textDecoration: 'none', color: 'inherit' }}>
        <MenuItem onClick={handleMenuClose}>
          Login
        </MenuItem>
      </NavLink>,
      <NavLink key="register" to="/register" activeclassname="active" style={{ textDecoration: 'none', color: 'inherit' }}>
        <MenuItem onClick={handleMenuClose}>
          Register
        </MenuItem>
      </NavLink>
    ]
  );

  return (
    <AppBar>
      <Toolbar>
        <Typography variant="h6" style={{ flexGrow: 1 }}>
          <NavLink style={{ textDecoration: "none", color: "inherit" }} to="/">Welcome to I.F.M</NavLink>
        </Typography>
        <Switch checked={props.darkMode} onChange={props.toggleDarkMode} />
        {isSmallScreen ? (
          <IconButton edge="end" color="inherit" aria-label="menu" onClick={handleMenuClick}>
            <Avatar />
          </IconButton>
        ) : (
          <div style={{ display: "flex" }}>
            {menuItems.map((item) => (
              <div key={item.key} style={{ margin: "0 4px" }}>
                {item}
              </div>
            ))}
          </div>
        )}
        <Menu anchorEl={anchorEl} keepMounted open={Boolean(anchorEl)} onClose={handleMenuClose} PaperProps={{
          style: {
            width: "200px",
            borderRadius: "8px",
            boxShadow: "0 2px 10px rgba(0, 0, 0, 0.2)",
          },
        }}>
          {menuItems}
        </Menu>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;