import React, { useState, useEffect } from 'react';
import {
    Container, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper,
    IconButton, TextField, Button, Dialog, DialogTitle, DialogContent, DialogActions, TableSortLabel, Tooltip, Box
} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { Delete, Edit } from '@material-ui/icons';
import { AuthService } from '../../utils';
import { API_URL_Backend } from "../../constants"
import { DataGrid } from '@material-ui/data-grid';

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
    },
    dialog: {
        minWidth: 300,
    },
}));

const initialInfo = {
    id: '',
    time: '',
    ip: '',
    vehicle_ID: '',
    speed: '',
    throttle_POS: '',
    engine_RPM: '',
    engine_LOAD: '',
    engine_COOLANT_TEMP: '',
    maf: '',
    fuel_LEVEL: '',
    fuel_PRESSURE: '',
    timing_ADVANCE: '',
    trouble_CODES: '',
    issues: '',
};

const BusTracks = () => {
    const classes = useStyles();
    const [data, setData] = useState([]);
    const [info, setInfo] = useState(initialInfo);

    useEffect(() => {
        // fetch the list of users from the server on initial render
        fetch(`${API_URL_Backend}/get_tracks`)
            .then((res) => res.json())
            .then((result) => {
                setData(result);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }, []);

    const columns = [
        { field: 'id', headerName: 'Id', width: 200 },
        { field: 'time', headerName: 'Time', width: 200 },
        { field: 'ip', headerName: 'Ip', width: 200 },
        { field: 'vehicle_ID', headerName: 'Vehicle Id', width: 200 },
        { field: 'speed', headerName: 'Speed', width: 200 },
        { field: 'throttle_POS', headerName: 'Throttle Pos', width: 200 },
        { field: 'engine_RPM', headerName: 'Engine Rpm', width: 200 },
        { field: 'engine_LOAD', headerName: 'Engine Load', width: 200 },
        { field: 'engine_COOLANT_TEMP', headerName: 'Engine Coolant Temp', width: 200 },
        { field: 'maf', headerName: 'Maf', width: 200 },
        { field: 'fuel_LEVEL', headerName: 'Fuel Level', width: 200 },
        { field: 'fuel_PRESSURE', headerName: 'Fuel Pressure', width: 200 },
        { field: 'timing_ADVANCE', headerName: 'Timing Advance', width: 200 },
        { field: 'trouble_CODES', headerName: 'Trouble Codes', width: 200 },
        { field: 'issues', headerName: 'Issues', width: 200 },
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

    return (
        <Container className="pt-8" align="center" maxWidth="lg" >
            <Typography variant="h3" gutterBottom >
                {AuthService.isAuthenticated() ? 'All tracks' : AuthService.getCurrentUser().sub + '`s tracks'}
            </Typography>
            <Box height={500}>
                <DataGrid
                    rows={data}
                    columns={columns}
                    pageSize={pageSize}
                    pagination
                    disableSelectionOnClick
                    autoHeight
                    page={page}
                    onPageChange={handlePageChange}
                />
            </Box>
        </Container>
    );
}

export default BusTracks