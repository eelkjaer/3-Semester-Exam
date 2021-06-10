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
  const { uddannelse, schoolName, uddName, facade } = props;
  const semestrene = uddannelse.educations.find(s => s.name === uddName);
  const [semestre] = useState([...semestrene.semesters]);

  useEffect(() => {
    
  }, []);

  return (
    <div>
      {console.log(uddannelse.uddannelser)}
      {console.log(semestrene)}
      {console.log(semestre)}
      {console.log(schoolName)}
      {console.log(uddName)}
      {/* {JSON.stringify(semestrene.semestre)} */}

      <div className="row">
        {semestrene.semesters.map((sem) => (
          <div className="col-md-4 theMargin" key={sem.id}>
            <div className="">
              <Link className="item-box the-box underline" to={`/` + schoolName + `/` + uddName + `/` + sem.name}>
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
