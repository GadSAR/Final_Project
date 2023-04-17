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

  logout() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (user && user.accessToken) {
      axios.post(`${API_URL}/auth/logout`, { token: user.accessToken });
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
