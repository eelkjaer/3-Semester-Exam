import React, { useState, useEffect } from "react";
import {
  // BrowserRouter as Router,
  Switch,
  Route,
  // Link,
  // NavLink,
  // Redirect,
  // useLocation,
  // Prompt,
  // useRouteMatch,
  // useParams,
  useHistory,
} from "react-router-dom";
import Logo from "./components/Logo.component";
import Header from "./components/Header.component";
import Harbour from "./components/Harbour.component";
import Boat from "./components/Boat.component";
import Owner from "./components/Owner.component";

import Home from "./components/Home.component";


import NoMatch from "./components/NoMatch.component";
import Login from "./components/Login.component";
// import PrivateRoute from "./components/PrivateRoute.component";
import "./App.css";

function App(props) {
  const defaultList = [];
  const { facade, utils } = props;
  const [harbourData, setHarbourData] = useState([...defaultList]);

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [displayError, setDisplayError] = useState("");
  let history = useHistory();

  useEffect(() => {
    facade.getAllHarbours((data) => {
      console.log(data);
      setHarbourData([...data]);
    });
  }, []);

  const setLoginStatus = (status, pageToGoTo) => {
    // console.log(pageToGoTo)
    //Redurect to home if logut is pressed ==> pageToGoTo === "undefined"
    if (typeof pageToGoTo === "undefined") {
      pageToGoTo = "/";
      // console.log(pageToGoTo)
    }
    setIsLoggedIn(status);
    history.push(pageToGoTo);
  };

  const login = (user, pass, from) => {
    /*TODO*/
    utils
      .login(user, pass)
      .then((res) => {
        setLoginStatus(true, from);
        //setIsLoggedIn(true);
        setDisplayError("");
      })
      .catch((error) => {
        error.fullError.then((errorMsg) => {
          console.log(errorMsg);
          setDisplayError(
            // "Error: Status: " +
            //   errorMsg.code +
            //   " -  Message: " +
            errorMsg.message
          );
        });
      });
  };

  const clearError = () => {
    setDisplayError("");
  };

  return (
    <div className="container">
      {/* {console.log(schoolData)} */}
      {/* {console.log(props.bookFacade.getBooks)} */}
      <Logo />
      <Header
        loginMsg={isLoggedIn ? "Logout" : "Login"}
        isLoggedIn={isLoggedIn}
      />

      <Switch>
        <Route exact path="/">
          <Home
            utils={utils}
            facade={facade}
            isLoggedIn={isLoggedIn}
            
          />
        </Route>

        <Route exact path="/harbours">
        <Harbour
                harbourData={harbourData}
                facade={facade}
              />
        </Route>

        <Route exact path="/owners">
        <Owner
                facade={facade}
              />
        </Route>

        {harbourData.map((harbour) => (
        <Route exact path={`/harbour/` + harbour.id} key={harbour.id}>
          <Boat
            facade={facade}
            harbourId={harbour.id}
            harbourName={harbour.name}
            />
        </Route>
        ))}

        

        <Route path="/login-out">
          <Login
            loginMsg={isLoggedIn ? "Logout" : "Login"}
            isLoggedIn={isLoggedIn}
            setLoginStatus={setLoginStatus}
            utils={utils}
            login={login}
            displayError={displayError}
            clearError={clearError}
          />
        </Route>

        <Route path="*">
          <NoMatch />
        </Route>
      </Switch>
    </div>
  );
}

export default App;
