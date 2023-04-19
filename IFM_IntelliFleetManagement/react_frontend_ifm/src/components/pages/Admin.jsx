import React, { useState, useEffect } from 'react';
import {
    Container, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper,
    IconButton, TextField, Button, Dialog, DialogTitle, DialogContent, DialogActions,
} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { Delete, Edit } from '@material-ui/icons';
import { AuthService } from '../../utils';

const API_URL = 'http://localhost:8080/ifm_api';

const useStyles = makeStyles((theme) => ({
    root: {
        minHeight: `calc(100vh - 64px - ${theme.spacing(8)}px)`,
        marginTop: theme.spacing(8),
    },
    table: {
        marginTop: theme.spacing(1),
        minWidth: 650,
    },
    addButton: {
        marginTop: theme.spacing(1),
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
    n_email: '',
    roles: '',
    companies: '',
    password: '',
    n_password: '',
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

    const handleDelete = (email) => {
        const companyName = AuthService.getCurrentUser().companies[0];

        // send a DELETE request to the server to delete the user with the given id
        AuthService.deleteUserByAdmin(email)
            .then(() => {
                console.log(`User ${email} deleted`);
                // update the list of users by fetching the latest data from the server
                fetch(`${API_URL}/get_company_users?companyName=${companyName}`)
                    .then((res) => res.json())
                    .then((result) => {
                        setUsers(result);
                    })
                    .catch((error) => {
                        console.error('Error:', error);
                    });
            })
            .catch((error) => {
                console.error('Error:', error);
            });

    };

    const handleSave = () => {
        const companyName = AuthService.getCurrentUser().companies[0];
        if (editing) {
            // send a PUT request to the server to update the user with the given id
            AuthService.updateUserByAdmin(user.email, user.n_email, user.name, companyName, user.n_password)
                .then(() => {
                    console.log(`User ${user.id} updated`);
                    fetch(`${API_URL}/get_company_users?companyName=${companyName}`)
                        .then((res) => res.json())
                        .then((result) => {
                            setUsers(result);
                        })
                        .catch((error) => {
                            console.error('Error:', error);
                        });
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        } else {
            // send a POST request to the server to create a new user
            AuthService.registerUser(user.name, companyName, user.n_email, user.n_password)
                .then(() => {
                    console.log('New user added');
                    fetch(`${API_URL}/get_company_users?companyName=${companyName}`)
                        .then((res) => res.json())
                        .then((result) => {
                            setUsers(result);
                        })
                        .catch((error) => {
                            console.error('Error:', error);
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
        const companyName = AuthService.getCurrentUser().companies[0];
        fetch(`${API_URL}/get_company_users?companyName=${companyName}`)
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
                {AuthService.getCurrentUser() ? AuthService.getCurrentUser().companies[0] + '`s Users' : 'Users'}
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
                            <TableCell>Username</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Role</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {users.map((user) => (
                            <TableRow key={user.id}>
                                <TableCell>{user.name}</TableCell>
                                <TableCell>{user.email}</TableCell>
                                <TableCell>
                                    {user.roles.length > 0 ? user.roles[0].name : 'No role'} {/* Display the first role name */}
                                </TableCell>
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
                                        onClick={() => handleDelete(user.email)}
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
                    <TextField
                        autoFocus
                        margin="dense"
                        name="name"
                        label="Username"
                        type="text"
                        required
                        value={user.name}
                        onChange={handleInputChange}
                        fullWidth
                    />
                    <TextField
                        margin="dense"
                        name="n_email"
                        label="Email"
                        type="email"
                        required
                        value={user.n_email}
                        onChange={handleInputChange}
                        fullWidth
                    />
                    <TextField
                        margin="dense"
                        name="n_password"
                        label="Password"
                        type="password"
                        required
                        value={user.n_password}
                        onChange={handleInputChange}
                        fullWidth
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleSave} color="primary">
                        {editing ? 'Update' : 'Add'}
                    </Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
}

export default Admin