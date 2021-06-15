import React from "react";
import {
  // BrowserRouter as Router,
  // Switch,
  // Route,
  // Link,
  NavLink,
  // Redirect,
  //   useLocation,
  // Prompt,
  // useRouteMatch,
  // useParams,
  // useHistory,
} from "react-router-dom";

export default function Header({ isLoggedIn, loginMsg, isAdmin }) {
  return (
    <div>
      <ul className="header">
      <li>
        <NavLink exact activeClassName="active" to="/">
          Home
          </NavLink>
          </li>
      {isLoggedIn ? (
      <li>
      <NavLink activeClassName="active" to="/harbours">
        Harbours
      </NavLink>
      </li>
          ) : ""}
      {isLoggedIn ? (
      <li>
      <NavLink exact activeClassName="active" to="/owners">
        Owners
      </NavLink>
    </li>
          ) : ""}
        
        {isLoggedIn && isAdmin ? (
      <li>
      <NavLink exact activeClassName="active" to="/admin">
        Admin
      </NavLink>
    </li>
          ) : ""}
        
        
        
        
        <li>
          <NavLink activeClassName="active" to="/login-out">
            {loginMsg}
          </NavLink>
        </li>
      </ul>
      <hr />
    </div>
  );
}
