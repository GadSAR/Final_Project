import axios from 'axios';
import jwtDecode from 'jwt-decode';

const currentIP = window.location.hostname;
const API_URL = `http://${currentIP}:8080/ifm_api`;

class AuthService {

  async login(email, password) {
    const response = await axios
      .post(`${API_URL}/auth/login`, { email, password });
    if (response.data.accessToken) {
      localStorage.setItem('user', JSON.stringify(response.data));
      return response.data;
    }
    else {
      return 'error';
    }
  }

  async registerAdmin(username, company, email, password) {
    try {
      const response = await axios.post(`${API_URL}/auth/registerAdmin`, { username, company, email, password });
      return response.data;
    } catch (error) {
      throw new Error(`Error during registration: ${error.message}`);
    }
  }

  async registerUser(username, company, email, password) {
    try {
      const response = await axios.post(`${API_URL}/auth/registerUser`, { username, company, email, password });
      return response.data;
    } catch (error) {
      throw new Error(`Error during registration: ${error.message}`);
    }
  }

  async updateUser(email, username, company, c_password, n_password) {
    try {
      const response = await axios.post(`${API_URL}/auth/updateUser`, { email, username, company, c_password, n_password });
      if (response.data.accessToken) {
        localStorage.setItem('user', JSON.stringify(response.data));
      }
      return response.data;
    } catch (error) {
      throw new Error(`Error during update: ${error.message}`);
    }
  }


  async updateUserByAdmin(email, n_email, username, company, n_password) {
    try {
      const response = await axios.post(`${API_URL}/auth/updateUserByAdmin`, { email, n_email, username, company, n_password });
      if (response.data.accessToken) {
        localStorage.setItem('user', JSON.stringify(response.data));
      }
      return response.data;
    } catch (error) {
      throw new Error(`Error during update: ${error.message}`);
    }
  }

  async deleteUserByAdmin(email) {
    try {
      const response = await axios.post(`${API_URL}/auth/deleteUserByAdmin`, { email })
      return response.data;
    } catch (error) {
      throw new Error(`Error during update: ${error.message}`);
    }
  }

  async logout() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user && user.accessToken) {
      axios.post(`${API_URL}/auth/logout`, { accessToken: user.accessToken });
    }
    localStorage.removeItem('user');
  }

  getCurrentUser() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user && user.accessToken) {
      const decodedToken = jwtDecode(user.accessToken);
      if (user.roles) {
        decodedToken.roles = user.roles.map((role) => role.name);
      }
      return decodedToken;
    }
    return null;
  }

  getUserRole() {
    const currentUser = this.getCurrentUser();
    if (currentUser && currentUser.roles && currentUser.roles.length > 0) {
      return currentUser.roles[0];
    }
    return null;
  }

  isAuthenticated() {
    return this.getUserRole() === 'ROLE_ADMIN' ? true : false;
  }
}

export default new AuthService();
