import React, { useState, useEffect } from "react";

import "../style/uddannelsesStyle.css";
import "react-responsive-modal/styles.css";
import "../style/modal.css";

export default function Boat(props) {
  const {
    harbourId,
    harbourName,
    facade,
  } = props;

  const list = [];

  const [boatList, setBoatList] = useState([...list]);

  useEffect(() => {
    facade.getBoatsByHarbourId(harbourId, (data) => {
      const boats = data;
      setBoatList([...boats]);
    });
  }, [facade]);


  return (
    <div>
      <div className="row">
        <div className="col-md-1"></div>
        <div className="col-md-10">
          <h1 className="text-center">Alle både i {harbourName}</h1>
        </div>
        <div className="col-md-1">
        </div>
      </div>

    

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
