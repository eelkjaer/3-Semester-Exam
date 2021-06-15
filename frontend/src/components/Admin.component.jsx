import React, { useState, useEffect } from "react";

import "../style/uddannelsesStyle.css";
import {
  Redirect,
  Link
} from "react-router-dom";
import "react-responsive-modal/styles.css";
import "../style/modal.css";

export default function Admin(props) {
  const {
    facade,
    role
  } = props;

  const list = [];
  const [boatList, setBoatList] = useState([...list]);
  const [setValue] = useState();

  useEffect(() => {
    facade.getAllBoats((data) => {
      console.log("owners: " + data);
      const boats = data;
      setBoatList([...boats]);
    });
  }, [facade]);

  const handleSubmit = (event) => {
    const target = event.target;
    
    event.preventDefault();
    facade.deleteBoat(target[0].value, (data) => {
      facade.getAllBoats((data) => {
        console.log("owners: " + data);
        const boats = data;
        setBoatList([...boats]);
      });
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
        <div className="col-md-2">
        <Link to="/admin/create" className="btn btn-primary">Create new boat</Link>
        </div>
        <div className="col-md-7">
          <h1 className="text-center">Admin page</h1>
        </div>
        <div className="col-md-1"></div>
      </div>

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
                >
                  <td><Link to={`admin/boat/` + boat.id}>{boat.name}</Link></td>
                  <td>{boat.brand}</td>
                  <td>{boat.make}</td>
                  <td>{boat.harbour.name}</td>
                  <td>{boat.owners.map((owner) => owner.name)}</td>
                  <td>
                  <form
                  className="form-horizontal"
                  onSubmit={handleSubmit}
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
