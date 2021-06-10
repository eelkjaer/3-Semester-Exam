import React from 'react';
import {
    // BrowserRouter as Router,
    // Switch,
    // Route,
    // Link,
    // NavLink,
    // Redirect,
    useLocation
  } from "react-router-dom";

  export default function NoMatch() {
    let location = useLocation();
  
    return (
      <div>
        <h3>
          Code: 404 - No match for <code>{location.pathname}</code>
        </h3>
      </div>
    );
  }