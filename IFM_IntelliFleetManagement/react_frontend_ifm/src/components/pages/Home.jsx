import React from 'react';
import { Link } from 'react-router-dom';
import { CarCanva } from '../canvas';

const Home = () => {
  return (
    <div className="flex flex-col items-center">
      <div className="mt-12">
        <CarCanva />
      </div>
      <h1 className="text-4xl sd:text-2xl font-bold mt-4 mb-8 text-center">
        Welcome to Fleet Management
      </h1>
      <p className="text-xl sd:text-0.5xl mb-8 text-center">
        Manage your fleet with ease using our advanced fleet management system.
      </p>
      <Link
        to="/login"
        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
      >
        Get Started
      </Link>
    </div>
  );
};

export default Home;