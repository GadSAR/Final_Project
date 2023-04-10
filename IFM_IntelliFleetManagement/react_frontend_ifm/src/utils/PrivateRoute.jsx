import { Route, Navigate } from 'react-router-dom';
import * as AuthService from './AuthService';

export function PrivateRoute({ element, ...rest }) {
  return (
    <Route
      {...rest}
      element={AuthService.isAuthenticated() ? element : <Navigate to='/' replace={true} />}
    />
  );
}

export default PrivateRoute;
