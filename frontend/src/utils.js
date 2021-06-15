import { SERVER_URL } from "./settings";
import { SERVER_URL_BACKEND } from "./settingsBackend";

function handleHttpErrors(res) {
  if (!res.ok) {
    //console.log(res);
    if (res.status === 401) {
      document.getElementById("errorMsg").innerHTML =
        "Bruger blev ikke varificeret...";
      document.getElementById("errorMsg").style.display = "block";
      setInterval(function () {
        document.getElementById("errorMsg").innerHTML =
        "";
      document.getElementById("errorMsg").style.display = "none";
      }, 2500);
    }
    //   const message = res.statusText === "" ? "Something went wrong... :(" : "";
    //   document.getElementById("errorMsg").innerHTML =
    //     "Status code: " + res.status + ", message: " + message;

    return Promise.reject({ status: res.status, fullError: res.json() });
  }
  return res.json();
}

function apiUtils() {
  function makeOptions(method, addToken, body) {
    // console.log(method);
    method = method ? method : "GET";
    var opts = {
      method: method,
      headers: {
        ...(["PUT", "POST"].includes(method) && {
          //using spread operator to conditionally add member to headers object.
          "Content-type": "application/json",
        }),
        Accept: "application/json",
      },
    };
    // console.log(method);
    if (addToken && loggedIn()) {
      opts.headers["x-access-token"] = getToken();
    }
    if (body) {
      opts.body = JSON.stringify(body);
    }
    return opts;
  }

  function fetchAny(url, callback, method, body) {
    // console.log(url);
    //console.log(callback);
    // console.log(method);
    //console.log(body);
    const options = makeOptions(method, true, body);
    //console.log(options);
    fetch(url, options)
      .then((res) => handleHttpErrors(res))
      .then((data) => callback(data))
      .catch((err) => {
        if (err.status) {
          err.fullError.then((e) => console.log(e.detail));
        } else {
          console.log("Network error: " + err);
        }
      });
  }

  function login(user, password) {
    //console.log(user);
    //console.log(password);
    /*TODO*/
    const options = makeOptions("POST", true, {
      username: user,
      password: password,
    });

    return fetch(SERVER_URL_BACKEND + "/api/auth/", options)
      .then(handleHttpErrors)
      .then((res) => {
        // console.log(res);
        // document.getElementById("error").innerHTML = "";
        setToken(res.token);
        setRole(res.role);
        saveUser(user);
      });
  }

  function fetchWelcomeData() {
    /*TODO */
    const options = makeOptions("GET", true); //True add's the token
    return fetch(SERVER_URL + "/api/info/admin", options).then(
      handleHttpErrors
    );
  }

  const setRole = (role) => {
    localStorage.setItem("role", role);
  }

  const setToken = (token) => {
    localStorage.setItem("jwtToken", token);
  };

  const saveUser = (username) => {
    localStorage.setItem("username", username);
  };

  const getToken = () => {
    return localStorage.getItem("jwtToken");
  };

  const loggedIn = () => {
    const loggedIn = getToken() != null;
    return loggedIn;
  };

  const logout = () => {
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("username");
    localStorage.removeItem("role");
  };

  return {
    makeOptions,
    fetchAny,
    // fetchAnyNoToken,
    login,
    fetchWelcomeData,
    setToken,
    getToken,
    loggedIn,
    logout,
  };
}

const utils = apiUtils();
export default utils;

//   export default { fetchAny, login, fetchWelcomeData, makeOptions };
