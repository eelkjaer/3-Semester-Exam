import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import utils from "./utils";
import facade from "./facades/examFacade";
import { BrowserRouter as Router } from "react-router-dom";

// Importing the Bootstrap CSS
import "bootstrap/dist/css/bootstrap.min.css";

const AppWithRouter = () => {
  return (
    <Router>
      <App facade={facade} utils={utils} />
    </Router>
  );
};
ReactDOM.render(<AppWithRouter />, document.getElementById("root"));
