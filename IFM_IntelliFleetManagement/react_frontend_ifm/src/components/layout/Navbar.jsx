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
        <NavLink key="dashboard" to="/dashboard" activeclassname="active" style={{ textDecoration: 'none', color: 'inherit' }}>
          <MenuItem onClick={handleMenuClose}>
            Dashboard
          </MenuItem>
        </NavLink>
      ),
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
        <MenuItem onClick={handleLoggedOut}>
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