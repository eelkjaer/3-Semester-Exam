import React from "react";
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

export default function Uddannelse(props) {
  const { uddannelse, schoolName, facade } = props;
  // const [uddannelser] = useState([...uddannelse.uddannelser]);
  // const [school] = useState(schoolName);

  return (
    <div>
      {/* {console.log(uddannelse)} */}
      {/* {console.log(uddannelse.uddannelser)} */}
      {/* {console.log(school)} */}
      {/* {JSON.stringify(uddannelser)} */}

      <div className="row">
        {uddannelse.educations.map((udd) => (
          <div className="col-md-4 theMargin" key={udd.id}>
            <div className="">
              <Link
                className="item-box the-box underline"
                to={`/` + schoolName + `/` + udd.name}
              >
                <h1 className="text-center udd" style={{ color: "black" }}>
                  {udd.name}
                </h1>
              </Link>
            </div>
          </div>
        ))}
      </div>
      <Footer facade={facade}/>
    </div>
  );
}
