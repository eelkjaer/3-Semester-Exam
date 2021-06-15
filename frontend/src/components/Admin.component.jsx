import React, { useState, useEffect } from "react";
import CreateQModal from "react-responsive-modal";
import ShowQModal from "react-responsive-modal";
import queryString from 'query-string'

import "../style/uddannelsesStyle.css";
import {
  Redirect,
  Route,
  useParams,
} from "react-router-dom";
import picture from "../images/tutor.jpg";
import "react-responsive-modal/styles.css";
import "../style/modal.css";
import { Alert } from "bootstrap";

export default function Admin(props) {
  const {
    utils,
    facade,
    role
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
    facade.getBoatsByHarbourId(-99, (data) => {
      const boats = data;
      setBoatList([...boats]);
    });

  }, [facade]);

  const handleSubmit = (event) => {
    const target = event.target;
    
    event.preventDefault();
    facade.deleteBoat(target[0].value, (data) => {
      if(data === "true") {
        facade.getBoatsByHarbourId(-99, (data) => {
          const boats = data;
          setBoatList([...boats]);
        });
      }
    });
  };

  if(role !== "admin"){
    return (
      <Redirect to="/"/>
    )
  }



  return (
    <div>
      <div className="row">
        <div className="col-md-1"></div>
        <div className="col-md-10">
          <h1 className="text-center">Admin page</h1>
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
                <th scope="col">Harbour</th>
                <th scope="col">Owners</th>
                <th scope="col"></th>
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
                  <td>{boat.harbour.name}</td>
                  <td>{boat.owners.map((owner) => owner.name)}</td>
                  <td>
                  <form
        className="form-horizontal"
        onSubmit={handleSubmit}
        onChange={handleSubmit}
      >
        <input
              className="form-control"
              type="hidden"
              name="boatToDelId"
              defaultValue={boat.id}
              />
              <button type="submit" className="btn btn-primary">
              Delete
            </button>
            </form>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>


    </div>
  );
}
