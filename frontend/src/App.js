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
import Uddannelse from "./components/Uddannelse.component";
import Semester from "./components/Semester.component";
import Queue from "./components/Queue.component";

import Home from "./components/Home.component";


import NoMatch from "./components/NoMatch.component";
import Login from "./components/Login.component";
// import PrivateRoute from "./components/PrivateRoute.component";
import "./App.css";

function App(props) {
  const defaultList = [];
  const { facade, utils } = props;
  const [schoolData, setSchoolData] = useState([...defaultList]);

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [displayError, setDisplayError] = useState("");
  let history = useHistory();

  useEffect(() => {
    facade.getData((data) => {
      console.log(data);
      setSchoolData([...data]);
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
            schoolData={schoolData}
          />
        </Route>

        {/* Home Page Option */}
        {schoolData.map((school) => (
          <Route exact path={`/` + school.name} key={school.id}>
            <Uddannelse
              uddannelse={schoolData.find((s) => s.name === school.name)}
              schoolName={school.name}
              facade={facade}
            />
          </Route>
        ))}

        {/* Udd Page Option */}
        {schoolData.map((school) =>
          school.educations.map((udd) => (
            // console.log(`/` + school.name + `/` + udd.name)
            <Route exact path={`/` + school.name + `/` + udd.name} key={udd.id}>
              <Semester
                uddannelse={schoolData.find((s) => s.name === school.name)}
                schoolName={school.name}
                uddName={udd.name}
                facade={facade}
              />
            </Route>
          ))
        )}

        {/* Udd Page Option */}
        {schoolData.map((school) =>
          school.educations.map((udd) =>
            udd.semesters.map((semester) => (
              // console.log(`/` + school.name + `/` + udd.name + `/` + semester.name)
              <Route
                exact
                path={`/` + school.name + `/` + udd.name + `/` + semester.name}
                key={semester.id}
              >
                <Queue
                  schoolName={school.name}
                  schoolId={school.id}
                  uddName={udd.name}
                  uddId={udd.id}
                  semesterName={semester.name}
                  semesterId={semester.id}
                  facade={facade}
                  isLoggedIn={isLoggedIn}
                />
              </Route>
            ))
          )
        )}

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
