import React, { useState, useEffect } from "react";
import CreateQModal from "react-responsive-modal";
import ShowQModal from "react-responsive-modal";
import queryString from 'query-string'

import "../style/uddannelsesStyle.css";
import {
  Route,
  useParams
} from "react-router-dom";
import picture from "../images/tutor.jpg";
import "react-responsive-modal/styles.css";
import "../style/modal.css";

export default function Owner(props) {
  const {
    facade,
    isLoggedIn,
  } = props;

  const owner = {
    id: -1,
    name: "",
    address: "",
    phone: 12345678
  };

  const list = [];

  const [ownerList, setOwnerList] = useState([...list]);

  useEffect(() => {
    facade.getAllOwners((data) => {
      console.log("owners: " + data);
      const owners = data;
      setOwnerList([...owners]);
    });

    if (isLoggedIn) {
      //setTeacherEmail(localStorage.getItem("teacherEmail"));
    }
  }, []);


  return (
    <div>
      <div className="row">
        <div className="col-md-1"></div>
        <div className="col-md-10">
          <h1 className="text-center">All owners</h1>
        </div>
        <div className="col-md-1"></div>
      </div>

    

      {/* Tabel  */}
      <div className="row">
        <div className="col-md-12">
          <table className="table table-striped">
            <thead>
              <tr>
                <th scope="col">Name</th>
                <th scope="col">Address</th>
                <th scope="col">Phone</th>
              </tr>
            </thead>
            <tbody>
              {ownerList.map((owner, index) => (
                <tr
                  style={{ cursor: "pointer" }}
                  key={owner.id}
                  id={owner.id}
                  onClick={console.log("test")}
                >
                  <td>{owner.name}</td>
                  <td>{owner.address}</td>
                  <td>{owner.phone}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>


    </div>
  );
}
