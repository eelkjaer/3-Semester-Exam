import React, { useState, useEffect } from "react";
import {
  Switch,
  Route,
  Redirect,
  useHistory,
} from "react-router-dom";
import Logo from "./components/Logo.component";
import Header from "./components/Header.component";
import Harbour from "./components/Harbour.component";
import Boat from "./components/Boat.component";
import Owner from "./components/Owner.component";
import Admin from "./components/Admin.component";
import Home from "./components/Home.component";
import CreateBoat from "./components/CreateBoat.component";
import EditBoat from "./components/EditBoat.component";


import NoMatch from "./components/NoMatch.component";
import Login from "./components/Login.component";
import "./App.css";

function App(props) {
  const harbourList = [];
  const newOwnerList = [];
  const { facade, utils } = props;
  const [harbourData, setHarbourData] = useState([...harbourList]);
  const [ownerList, setOwnerList] = useState([...newOwnerList]);

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const [displayError, setDisplayError] = useState("");
  let history = useHistory();

  useEffect(() => {
    facade.getAllHarbours((data) => {
      setHarbourData([...data]);
    });
    facade.getAllOwners((data) => {
      setOwnerList([...data]);
    });
  }, [facade]);

  const setLoginStatus = (status, pageToGoTo) => {
    if (typeof pageToGoTo === "undefined") {
      pageToGoTo = "/";
    }
    setIsLoggedIn(status);
    history.push(pageToGoTo);
  };

  const login = (user, pass, from) => {
    utils
      .login(user, pass)
      .then((res) => {
        setLoginStatus(true, from);
        localStorage.getItem("role") === "admin" ? setIsAdmin(true) : setIsAdmin(false);
        setDisplayError("");
      })
      .catch((error) => {
        console.log("error: " + error)
          setDisplayError(
            "Wrong login"
          );
      });
  };

  const clearError = () => {
    setDisplayError("");
  };

  return (
    <div className="container">
      <Logo />
      <Header
        loginMsg={isLoggedIn ? "Logout" : "Login"}
        isLoggedIn={isLoggedIn}
        isAdmin={isAdmin}
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
        {isLoggedIn ? <Harbour harbourData={harbourData} facade={facade}/> : <Redirect to="/" />}
        </Route>

        <Route exact path="/owners">
        {isLoggedIn ? <Owner facade={facade}/> : <Redirect to="/" />}
        </Route>

        {harbourData.map((harbour) => (
        <Route exact path={`/harbours/` + harbour.id} key={harbour.id}>
          <Boat
            facade={facade}
            harbourId={harbour.id}
            harbourName={harbour.name}
            />
        </Route>
        ))}

        <Route exact path="/admin">
        {isLoggedIn ? <Admin facade={facade} utils={utils} role={localStorage.getItem("role")} harbourData={harbourData}/> : <Redirect to="/" />}
        </Route>

        <Route exact path="/admin/create">
          <CreateBoat harbourData={harbourData} ownerList={ownerList} facade={facade}/>
        </Route>

        <Route exact path="/admin/boat/:id" component={<EditBoat/>}>
          <EditBoat harbourData={harbourData} ownerList={ownerList} facade={facade}/>
        </Route>


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
