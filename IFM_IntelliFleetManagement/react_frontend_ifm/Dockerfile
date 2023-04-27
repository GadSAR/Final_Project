# Use an official Node.js runtime as a parent image
FROM node:18.14.2-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the package.json and package-lock.json files to the container
COPY package.json ./

# Install dependencies
RUN npm install --force

# Copy the rest of the application code to the container
COPY . .

# Start the application
CMD ["npm", "run", "dev"]