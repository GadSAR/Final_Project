import React, { useState, useEffect } from 'react';
import {
    Container, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper,
    IconButton, TextField, Button, Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions,
} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { Delete, Edit } from '@material-ui/icons';

const API_URL = 'http://localhost:8080/ifm_api';

const useStyles = makeStyles((theme) => ({
    root: {
        minHeight: `calc(100vh - 64px - ${theme.spacing(8)}px)`,
        marginTop: theme.spacing(8),
    },
    table: {
        minWidth: 650,
    },
    addButton: {
        marginTop: theme.spacing(2),
        marginRight: theme.spacing(2),
    },
    editButton: {
        marginRight: theme.spacing(1),
    },
    deleteButton: {
        color: theme.palette.error.main,
    },
    dialog: {
        minWidth: 300,
    },
}));

const initialUser = {
    id: '',
    name: '',
    email: '',
    role: '',
};

const Admin = () => {
    const classes = useStyles();
    const [users, setUsers] = useState([]);
    const [user, setUser] = useState(initialUser);
    const [open, setOpen] = useState(false);
    const [editing, setEditing] = useState(false);

    const handleClose = () => {
        setOpen(false);
        setEditing(false);
        setUser(initialUser);
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setUser({ ...user, [name]: value });
    };

    const handleEdit = (selectedUser) => {
        setUser(selectedUser);
        setEditing(true);
        setOpen(true);
    };

    const handleDelete = (id) => {

        // send a DELETE request to the server to delete the user with the given id
        fetch(`${API_URL}/users/${id}`, {
            method: 'DELETE',
        })
            .then(() => {
                console.log(`User ${id} deleted`);
                // update the list of users by fetching the latest data from the server
                fetch('${API_URL}/users')
                    .then((res) => res.json())
                    .then((result) => {
                        setUsers(result);
                    });
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    const handleSave = () => {
        if (editing) {
            // send a PUT request to the server to update the user with the given id
            fetch(`${API_URL}/users/${user.id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(user),
            })
                .then(() => {
                    console.log(`User ${user.id} updated`);
                    // update the list of users by fetching the latest data from the server
                    fetch('${API_URL}/users')
                        .then((res) => res.json())
                        .then((result) => {
                            setUsers(result);
                        });
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        } else {
            // send a POST request to the server to create a new user
            fetch('${API_URL}/users', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(user),
            })
                .then(() => {
                    console.log('New user added');
                    // update the list of users by
                    fetch('${API_URL}/users')
                        .then((res) => res.json())
                        .then((result) => {
                            setUsers(result);
                        });
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        }
        handleClose();
    };

    useEffect(() => {
        // fetch the list of users from the server on initial render
        fetch('${API_URL}/users')
            .then((res) => res.json())
            .then((result) => {
                setUsers(result);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }, []);

    return (
        <Container className="pt-8" maxWidth="lg" >
            <Typography variant="h3" gutterBottom >
                Users
            </Typography>
            <Button
                variant="contained"
                color="primary"
                className={classes.addButton}
                onClick={() => setOpen(true)}
            >
                Add User
            </Button>
            <TableContainer component={Paper}>
                <Table className={classes.table} aria-label="users table">
                    <TableHead>
                        <TableRow>
                            <TableCell>ID</TableCell>
                            <TableCell>Name</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Role</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {users.map((user) => (
                            <TableRow key={user.id}>
                                <TableCell component="th" scope="row">
                                    {user.id}
                                </TableCell>
                                <TableCell>{user.name}</TableCell>
                                <TableCell>{user.email}</TableCell>
                                <TableCell>{user.role}</TableCell>
                                <TableCell>
                                    <IconButton
                                        aria-label="edit"
                                        className={classes.editButton}
                                        onClick={() => handleEdit(user)}
                                    >
                                        <Edit />
                                    </IconButton>
                                    <IconButton
                                        aria-label="delete"
                                        className={classes.deleteButton}
                                        onClick={() => handleDelete(user.id)}
                                    >
                                        <Delete />
                                    </IconButton>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="form-dialog-title"
            >
                <DialogTitle id="form-dialog-title">
                    {editing ? 'Edit User' : 'Add User'}
                </DialogTitle>
                <DialogContent className={classes.dialog}>
                    <DialogContentText>
                        To {editing ? 'edit' : 'add'} a user, please fill in the fields below.
                    </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        name="username"
                        label="Username"
                        type="text"
                        required
                        value={user.name}
                        onChange={handleInputChange}
                        fullWidth
                    />
                    <TextField
                        margin="dense"
                        name="email"
                        label="Email"
                        type="email"
                        required
                        value={user.email}
                        onChange={handleInputChange}
                        fullWidth
                    />
                    <TextField
                        margin="dense"
                        name="password"
                        label="Password"
                        type="password"
                        required
                        value={user.password}
                        onChange={handleInputChange}
                        fullWidth
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleSave} color="primary">
                        {editing ? 'Save' : 'Add'}
                    </Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
}

export default Admin