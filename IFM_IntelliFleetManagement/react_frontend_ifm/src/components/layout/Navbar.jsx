import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import { AuthService } from '../../utils';
import { AppBar, Toolbar, IconButton, Typography, Menu, MenuItem } from "@material-ui/core";
import { Brightness4, Brightness7, Menu as MenuIcon } from '@material-ui/icons';
import { useMediaQuery } from '@material-ui/core';

const Navbar = (props) => {
  const [anchorEl, setAnchorEl] = useState(null);
  const isSmallScreen = useMediaQuery("(max-width: 1000px)");

  const handleMenuClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleLoggedOut = () => {
    AuthService.logout();
    props.setLoggedIn(false);
  };


  const menuItems = props.loggedIn ? (
    [
      !AuthService.isAuthenticated() && (
        <MenuItem key="dashboard" component={NavLink} to="/dashboard" activeclassname="active" onClick={handleMenuClose}>
          Dashboard
        </MenuItem>
      ),
      <MenuItem key="bus-tracks" component={NavLink} to="/bus-tracks" activeclassname="active" onClick={handleMenuClose}>
        Bus Tracks
      </MenuItem>,
      <MenuItem key="predict" component={NavLink} to="/predict" activeclassname="active" onClick={handleMenuClose}>
        Predict Issues
      </MenuItem>,
      AuthService.isAuthenticated() && (
        <MenuItem key="admin" component={NavLink} to="/admin" activeclassname="active" onClick={handleMenuClose}>
          Admin
        </MenuItem>
      ),
      <MenuItem key="settings" component={NavLink} to="/settings" activeclassname="active" onClick={handleMenuClose}>
        Settings
      </MenuItem>,
      <MenuItem key="logout" component={NavLink} to="/" activeclassname="active" onClick={handleLoggedOut}>
        Logout
      </MenuItem>
    ]
  ) : (
    [
      <MenuItem key="home" component={NavLink} to="/" activeclassname="active" onClick={handleMenuClose}>
        Home
      </MenuItem>,
      <MenuItem key="login" component={NavLink} to="/login" activeclassname="active" onClick={handleMenuClose}>
        Login
      </MenuItem>,
      <MenuItem key="register" component={NavLink} to="/register" activeclassname="active" onClick={handleMenuClose}>
        Register
      </MenuItem>
    ]
  );

  return (
    <AppBar>
      <Toolbar>
        <Typography variant="h6" style={{ flexGrow: 1 }}>
          <NavLink style={{ textDecoration: "none", color: "inherit" }} to="/">
            {isSmallScreen ? 'Welcome to I.F.M' : 'Welcome to Intelli Fleet Management'} </NavLink>
        </Typography>
        <IconButton onClick={props.toggleDarkMode}>
          {props.darkMode ? <Brightness7 /> : <Brightness4 />}
        </IconButton>
        {isSmallScreen ? (
          <IconButton edge="end" color="inherit" aria-label="menu" onClick={handleMenuClick}>
            <MenuIcon />
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
        <Menu
          anchorEl={anchorEl}
          keepMounted
          open={Boolean(anchorEl)}
          onClose={handleMenuClose}
          PaperProps={{
            style: {
              width: "200px",
              borderRadius: "8px",
              boxShadow: "0 2px 10px rgba(0, 0, 0, 0.2)",
            },
          }}
          MenuListProps={{
            disablePadding: true,
          }}
        >
          {menuItems}
        </Menu>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;