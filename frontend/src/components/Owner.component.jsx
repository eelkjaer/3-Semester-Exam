import React, { useState, useEffect } from "react";

import "../style/uddannelsesStyle.css";
import "react-responsive-modal/styles.css";
import "../style/modal.css";

export default function Owner(props) {
  const {
    facade,
  } = props;


  const list = [];

  const [ownerList, setOwnerList] = useState([...list]);

  useEffect(() => {
    facade.getAllOwners((data) => {
      console.log("owners: " + data);
      const owners = data;
      setOwnerList([...owners]);
    });
  }, [facade]);


  return (
    <div>
      <div className="row">
        <div className="col-md-1"></div>
        <div className="col-md-10">
          <h1 className="text-center">All owners</h1>
        </div>
        <div className="col-md-1"></div>
      </div>

    
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
