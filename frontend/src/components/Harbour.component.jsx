import React, { useEffect } from "react";
import {
  Link
} from "react-router-dom";
import Footer from "./Footer.component";
import "../style/uddannelsesStyle.css";

export default function Harbour(props) {
  const { harbourData, facade } = props;

  useEffect(() => {
    
  }, []);

  return (
    <div>
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
