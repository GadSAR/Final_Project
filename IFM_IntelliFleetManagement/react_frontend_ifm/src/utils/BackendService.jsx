import axios from 'axios';
import jwtDecode from 'jwt-decode';
import { API_URL_Backend } from '../constants';

class AuthService {

  async login(email, password) {
    if (localStorage.getItem('user')) {
      this.logout();
    }
    const response = await axios
      .post(`${API_URL_Backend}/auth/login`, { email, password });
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
      const response = await axios.post(`${API_URL_Backend}/auth/registerAdmin`, { username, company, email, password });
      return response.data;
    } catch (error) {
      throw new Error(`Error during registration: ${error.message}`);
    }
  }

  async registerUser(username, company, email, password) {
    try {
      const response = await axios.post(`${API_URL_Backend}/auth/registerUser`, { username, company, email, password });
      return response.data;
    } catch (error) {
      throw new Error(`Error during registration: ${error.message}`);
    }
  }

  async updateUser(email, username, company, c_password, n_password) {
    try {
      const response = await axios.post(`${API_URL_Backend}/auth/updateUser`, { email, username, company, c_password, n_password });
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
      const response = await axios.post(`${API_URL_Backend}/auth/updateUserByAdmin`, { email, n_email, username, company, n_password });
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
      const response = await axios.post(`${API_URL_Backend}/auth/deleteUserByAdmin`, { email })
      return response.data;
    } catch (error) {
      throw new Error(`Error during update: ${error.message}`);
    }
  }

  async logout() {
    try {
      const user = JSON.parse(localStorage.getItem('user'));
      if (user && user.accessToken) {
        axios.post(`${API_URL_Backend}/auth/logout`, { accessToken: user.accessToken });
      }
    } catch (error) {
      throw new Error(`Error during update: ${error.message}`);
    }
    localStorage.removeItem('user');
  }

  async getCompanyUsers(companyName) {
    try {
      const response = await fetch(`${API_URL_Backend}/get_company_users?companyName=${companyName}`);
      const result = await response.json();
      return result;
    } catch (error) {
      throw new Error(`Error during update: ${error.message}`);
    }
  }

  async getCars() {
    try {
      let response;
      if (this.isAuthenticated()) {
        response = await fetch(`${API_URL_Backend}/get_all_cars`);
      } else {
        response = await fetch(`${API_URL_Backend}/get_user_cars?driverEmail=${this.getCurrentUser().email}`);
      }
      const result = await response.json();
      return result;
    } catch (error) {
      throw new Error(`Error during update: ${error.message}`);
    }
  }

  async refreshToken() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user && user.refreshToken) {
      const response = await axios.post(`${API_URL_Backend}/auth/refreshToken`, { refreshToken: user.refreshToken });
      if (response.data.accessToken) {
        localStorage.setItem('user', JSON.stringify(response.data));
        return response.data;
      }
    }
    return null;
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
