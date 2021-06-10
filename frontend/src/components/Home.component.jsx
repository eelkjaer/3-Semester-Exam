import React from "react";
import {
  // BrowserRouter as Router,
  // Switch,
  // Route,
  Link,
  // NavLink,
  // Redirect,
  // useLocation,
  // Prompt,
  // useRouteMatch,
  // useParams,
  // useHistory,
} from "react-router-dom";
import Footer from "./Footer.component";
import "../style/skoleStyle.css";

export default function Home(props) {
  const { facade, schoolData } = props;

  return (
    <div>
      {/* {console.log(props.isLoggedIn)} */}
      {console.log(schoolData)}
      <div className="row">
        {schoolData.map((school) => (
          <div className="col-md-4 theMargin" key={school.id}>
            <div className="item-box the-box">
              <Link className="" to={`/` + school.name}>
                <img
                  className="img-fluid the-image imgSchool"
                  src={school.img}
                  alt={school.name}
                />
              </Link>
            </div>
          </div>
        ))}
      </div>
      <br></br>
      <br></br>
      <br></br>
      <Footer facade={facade} />
    </div>
  );
}
