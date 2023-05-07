import React, { useEffect, useState } from 'react';
import {
    Box,
    Button,
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    IconButton,
    TextField,
    Typography
} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { Delete, Edit, Mail, Subject } from '@material-ui/icons';
import { AuthService } from '../../utils';
import { API_URL_Backend, API_URL_RabbitMQ } from "../../constants"
import { DataGrid } from '@material-ui/data-grid';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';

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
        marginBottom: theme.spacing(2),
    },
    editButton: {
        marginRight: theme.spacing(1),
    },
    deleteButton: {
        color: theme.palette.error.main,
        marginRight: theme.spacing(1),
    },
    mailButton: {
        color: theme.palette.success.main,
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

const initialMail = {
    subject: '',
    message: '',
};

const Admin = () => {
    const classes = useStyles();
    const [users, setUsers] = useState([]);
    const [user, setUser] = useState(initialUser);
    const [open, setOpen] = useState(false);
    const [editing, setEditing] = useState(false);


    const handleClose = () => {
        setOpen(false);
        setOpen2(false);
        setEditing(false);
        setUser(initialUser);
        setMail(initialMail);
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

    const openHandleMail = (selectedUser) => {
        setUser(selectedUser);
        setOpen2(true);
    };

    const Alert = (props) => {
        return <MuiAlert elevation={6} variant="filled" {...props} />;
    };
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarSeverity, setSnackbarSeverity] = useState('success');
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const handleSnackbarClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        setSnackbarOpen(false);
    };

    const handleDelete = (email) => {
        const companyName = AuthService.getCurrentUser().companies[0];

        // send a DELETE request to the server to delete the user with the given id
        AuthService.deleteUserByAdmin(email)
            .then(() => {
                console.log(`User deleted`);
                setSnackbarOpen(true);
                setSnackbarSeverity('success');
                setSnackbarMessage('User deleted successfully!');
                // update the list of users by fetching the latest data from the server
                fetch(`${API_URL_Backend}/get_company_users?companyName=${companyName}`)
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
                setSnackbarOpen(true);
                setSnackbarSeverity('error');
                setSnackbarMessage('Failed to delete user.');
            });

    };

    const handleMail = () => {
        if (!mail.subject || !mail.message) {
            alert("Please fill in all required fields.");
            return;
        }

        const name = user.name;
        const sender = AuthService.getCurrentUser().email;
        const recipient = user.email;
        const subject = mail.subject;
        const message = mail.message;

        fetch(`${API_URL_RabbitMQ}/mailToUser`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name, sender, recipient, subject, message })
        })
            .then((res) => {
                setOpen2(false);
                setMail(initialMail);
                setSnackbarOpen(true);
                setSnackbarSeverity('success');
                setSnackbarMessage('Message sent successfully!');
            })
            .catch((error) => {
                console.error('Error:', error);
                setSnackbarOpen(true);
                setSnackbarSeverity('error');
                setSnackbarMessage('Failed to send message.');
            });
    };

    const handleSave = () => {
        const companyName = AuthService.getCurrentUser().companies[0];
        if (editing) {
            // send a PUT request to the server to update the user with the given id
            AuthService.updateUserByAdmin(user.email, user.n_email, user.name, companyName, user.n_password)
                .then(() => {
                    setSnackbarOpen(true);
                    setSnackbarSeverity('success');
                    setSnackbarMessage('User updated successfully!');
                    handleClose();
                    fetch(`${API_URL_Backend}/get_company_users?companyName=${companyName}`)
                        .then((res) => res.json())
                        .then((result) => {
                            setUsers(result);
                        })
                        .catch((error) => {
                            console.error('Error:', error);
                            setSnackbarOpen(true);
                            setSnackbarSeverity('error');
                            setSnackbarMessage('Failed to update user.');
                        });
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        } else {
            if (user.n_password === '' || user.n_email === '' || user.name === '' || !/\S+@\S+\.\S+/.test(user.n_email)) {
                setSnackbarOpen(true);
                setSnackbarSeverity('error');
                setSnackbarMessage('Invalid input.');
            }
            else {
                // send a POST request to the server to create a new user
                AuthService.registerUser(user.name, companyName, user.n_email, user.n_password)
                    .then(() => {
                        setSnackbarOpen(true);
                        setSnackbarSeverity('success');
                        setSnackbarMessage('User registerd successfully!');
                        handleClose();
                        fetch(`${API_URL_Backend}/get_company_users?companyName=${companyName}`)
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
                        setSnackbarOpen(true);
                        setSnackbarSeverity('error');
                        setSnackbarMessage('Failed to register user.');
                    });
            }
        }
    };

    useEffect(() => {
        // fetch the list of users from the server on initial render
        const companyName = AuthService.getCurrentUser().companies[0];
        fetch(`${API_URL_Backend}/get_company_users?companyName=${companyName}`)
            .then((res) => res.json())
            .then((result) => {
                setUsers(result);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }, []);

    const columns = [
        { field: 'name', headerName: 'Username', width: 200 },
        { field: 'email', headerName: 'Email', width: 250 },
        {
            field: 'roles', headerName: 'Role', width: 200,
            valueGetter: (params) => params.row.roles.length > 0 ? params.row.roles[0].name : 'No role'
        },
        {
            field: 'actions',
            headerName: 'Actions',
            width: 180,
            sortable: false,
            filterable: false,
            renderCell: (params) => (
                <>
                    <IconButton
                        aria-label="edit"
                        className={classes.editButton}
                        onClick={() => handleEdit(params.row)}
                    >
                        <Edit />
                    </IconButton>
                    <IconButton
                        aria-label="delete"
                        className={classes.deleteButton}
                        onClick={() => handleDelete(params.row.email)}
                    >
                        <Delete />
                    </IconButton>
                    <IconButton
                        aria-label="mail"
                        className={classes.mailButton}
                        onClick={() => openHandleMail(params.row)}
                    >
                        <Mail />
                    </IconButton>
                </>
            )
        },
    ];

    const [page, setPage] = useState(0);
    const [pageSize, setPageSize] = useState(5);

    const handlePageChange = (params) => {
        setPage(params.page);
    };

    const handlePageSizeChange = (params) => {
        setPageSize(params.pageSize);
        setPage(0);
    };

    const [open2, setOpen2] = useState(false);
    const [mail, setMail] = useState(initialMail);

    return (
        <Container className="pt-8" align="center" maxWidth="lg" >
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
            <Box height={500}>
                <DataGrid
                    rows={users}
                    columns={columns}
                    pageSize={pageSize}
                    pagination
                    disableSelectionOnClick
                    autoHeight
                    page={page}
                    onPageChange={handlePageChange}
                />
            </Box>
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
                        type="text"
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

            <Dialog
                open={open2}
                onClose={handleClose}
                aria-labelledby="form-dialog-title"
            >
                <DialogTitle id="form-dialog-title">
                    Send Mail
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
                        name="subject"
                        label="Subject"
                        type="text"
                        required
                        value={mail.subject}
                        onChange={(event) => setMail({ ...mail, subject: event.target.value })}
                        fullWidth
                    />
                    <TextField
                        margin="dense"
                        name="message"
                        label="Message"
                        type="text"
                        required
                        multiline
                        minRows={5}
                        value={mail.message}
                        onChange={(event) => setMail({ ...mail, message: event.target.value })}
                        fullWidth
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleMail} color="primary">
                        Send
                    </Button>
                </DialogActions>
            </Dialog>

            <Snackbar open={snackbarOpen} autoHideDuration={3000} onClose={handleSnackbarClose}>
                <Alert severity={snackbarSeverity}>{snackbarMessage}</Alert>
            </Snackbar>
        </Container>
    );
}

export default Admin