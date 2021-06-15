import React, { useState, useEffect } from "react";
import CreateQModal from "react-responsive-modal";
import ShowQModal from "react-responsive-modal";
import queryString from 'query-string'

import "../style/uddannelsesStyle.css";
import {
  Route,
  useParams,
} from "react-router-dom";
import picture from "../images/tutor.jpg";
import "react-responsive-modal/styles.css";
import "../style/modal.css";

export default function Boat(props) {
  const {
    harbourId,
    harbourName,
    facade,
    isLoggedIn,
  } = props;

  const boat = {
    id: -1,
    harbour: {
      id: -1,
      name: "",
      address: "",
      capacity: -1
    },
    owners: [
      {
        id: -1,
        name: "",
        address: "",
        phone: 12345678
      }
    ],
    brand: "",
    make: "",
    name: "",
    image: ""
  };

  const list = [];

  const [b, setB] = useState({ ...boat });
  const [boatList, setBoatList] = useState([...list]);

  useEffect(() => {
    facade.getBoatsByHarbourId(harbourId, (data) => {
      let boaty = data.map((boat) => (
        console.log(boat)
      ));

      console.log("boats: " + data);
      const boats = data;
      setBoatList([...boats]);
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
          <h1 className="text-center">Alle både i {harbourName}</h1>
        </div>
        <div className="col-md-1">
        <button>Register new boat</button>
        </div>
      </div>

    

      {/* Tabel  */}
      <div className="row">
        <div className="col-md-12">
          <table className="table table-striped">
            <thead>
              <tr>
                <th scope="col">Name</th>
                <th scope="col">Brand</th>
                <th scope="col">Make</th>
                <th scope="col">Image</th>
                <th scope="col">Harbour</th>
                <th scope="col">Owners</th>
              </tr>
            </thead>
            <tbody>
              {boatList.map((boat, index) => (
                <tr
                  style={{ cursor: "pointer" }}
                  key={boat.id}
                  id={boat.id}
                  onClick={console.log("test")}
                >
                  <td>{boat.name}</td>
                  <td>{boat.brand}</td>
                  <td>{boat.make}</td>
                  <td>{boat.image}</td>
                  <td>{boat.harbour.name}</td>
                  <td>{boat.owners.map((owner) => owner.name)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>


    </div>
  );
}
