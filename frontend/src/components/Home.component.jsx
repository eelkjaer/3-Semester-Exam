import React from "react";
import {
  Link,
  Redirect,
} from "react-router-dom";
import Footer from "./Footer.component";
import "../style/skoleStyle.css";

export default function Home(props) {
  const { facade, schoolData } = props;

  if(!props.isLoggedIn){
    return (
      <div>
      {console.log(schoolData)}
      <div className="row">
        <p>Please <Link to="/login-out">login</Link> to continue!</p>
      </div>
      <br></br>
      <br></br>
      <br></br>
      <Footer facade={facade} />
    </div>
    )
  } else {
    return (
      <Redirect to="/harbours"></Redirect>
    )
  }
}
