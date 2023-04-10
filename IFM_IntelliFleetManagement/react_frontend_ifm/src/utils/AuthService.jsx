import axios from 'axios';
import jwtDecode from 'jwt-decode';

const currentIP = window.location.hostname;
const API_URL = `http://${currentIP}:8080/ifm_api`;

class AuthService {
  login(username, password) {
    return axios
      .post(`${API_URL}/auth/login`, { username, password })
      .then((response) => {
        if (response.data.accessToken) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem('user');
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
