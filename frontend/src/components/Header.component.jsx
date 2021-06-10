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

export default function Header({ isLoggedIn, loginMsg }) {
  return (
    <div>
      <ul className="header">
        <li>
          <NavLink exact activeClassName="active" to="/">
            Home
          </NavLink>
        </li>
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
