import React, { useState, useEffect } from "react";
import {
  useLocation,
} from "react-router-dom";

export default function Login(props) {
  const {
    loginMsg,
    isLoggedIn,
    setLoginStatus,
    utils,
    login,
    displayError,
    clearError,
  } = props;

  const init = { username: "", password: "" };
  const [loginCredentials, setLoginCredentials] = useState(init);

  const { state } = useLocation();
  const from = state ? state.from : "/";

  const performLogin = (evt) => {
    evt.preventDefault();
    setTimeout(function () {
      clearError();
    }, 5000);
    login(loginCredentials.username, loginCredentials.password, from);
  };

  const onChange = (evt) => {
    setLoginCredentials({
      ...loginCredentials,
      [evt.target.id]: evt.target.value,
    });
  };

  useEffect(() => {
    if (isLoggedIn) {
      utils.logout();
      setLoginStatus(!isLoggedIn);
    }
  }, []);

  return (
    <div className="container">
      <div className="row">
        <div className="col-sm-2 text-center"></div>
        <div className="col-sm-8 text-center">
        <h2>Login</h2>
          <form onChange={onChange}>
            <input className="form-control" placeholder="Skriv brugernavn..." type="text" id="username" />{" "}
            <input className="form-control" placeholder="Skriv kodeord..." type="password" id="password" />{" "}
            <p></p>
            <button className="btn btn-primary" onClick={performLogin}>{loginMsg}</button>
            {console.log("error: " + displayError)}
            {displayError.length > 0 ? (
              <p className="alert alert-danger" id="errorMsg" style={{ width: "415px" }}>
                {displayError}
              </p>
            ) : (
              ""
            )}
          </form>
        </div>
        <div className="col-sm-2 text-center"></div>
      </div>
    </div>
  );
}
