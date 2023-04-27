import React from 'react';
import { Link } from 'react-router-dom';

const Error = () => {
    return (
        <div className="flex flex-col items-center mt-24">
            <h1 className="text-4xl font-bold mt-6 mb-8 text-center">
                404 - Page Not Found
            </h1>
            <p className="text-xl mb-8 text-center">
                The page you are looking for does not exist.
            </p>
            <Link
                to="/"
                className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
            >
                Go back to home page
            </Link>
        </div>
    );
};

export default Error;