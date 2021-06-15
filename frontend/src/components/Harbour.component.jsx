import React, { useState, useEffect } from "react";
import {
  // BrowserRouter as Router,
  // Switch,
  // Route,
  Link,
//   NavLink,
  // Redirect,
  // useLocation,
  // Prompt,
  // useRouteMatch,
  // useParams,
  // useHistory,
} from "react-router-dom";
import Footer from "./Footer.component";
import "../style/uddannelsesStyle.css";

export default function Semester(props) {
  const { harbourData, facade } = props;

  useEffect(() => {
    
  }, []);

  return (
    <div>
      {console.log(harbourData)}
      {/* {JSON.stringify(semestrene.semestre)} */}

      <div className="row">
        {harbourData.map((sem) => (
          <div className="col-md-4 theMargin" key={sem.id}>
            <div className="">
              <Link className="item-box the-box underline" to={`harbours/` + sem.id + `/`} props={sem.id}>
              <h1 className="text-center udd" style={{color: "black"}}>{sem.name}</h1> 
              </Link>
            </div>
          </div>
        ))}
      </div>
      <Footer facade={facade}/>
    </div>
  );
}
