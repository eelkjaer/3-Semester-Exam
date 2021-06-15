import React, { useState, useEffect } from "react";
import {
  Redirect
} from "react-router-dom";




export default function EditBoat(props){
  const {
    facade,
    harbourData,
    ownerList
  } = props;

  const boatId = 1;

  const [boatData, setBoatData] = useState();

  const [posted, setPosted] = useState(false);

  

  

  const updateBoat = (evt) => {
    evt.preventDefault();

    const objectToSend = {
      id: boatData.id,
      harbour: {
        id: boatData.harbour
      },
      owners: 
        [
          {
          id: boatData.owner
        },
      ],
      brand: boatData.brand,
      make: boatData.make,
      name: boatData.name,
      image: boatData.image
    };

    facade.createBoat(objectToSend, (data) => {
      setBoatData({ ...boatData });
      setPosted(true);
    });

  };

  const onChange = (evt) => {
    setBoatData({
      ...boatData,
      [evt.target.id]: evt.target.value,
    });
    console.log(boatData)
  };

  useEffect(() => {
    facade.getBoatsById(boatId, (data) => {
      const boat = data;
      setBoatData(...boat);
      console.log(boatData)
    });
  });


  return (
    posted ? <Redirect push to="/admin"/> : 
    <div className="container">
      <div className="row">
        <div className="col-sm-2 text-center"></div>
        <div className="col-sm-8 text-center">
        <h2>Edit boat</h2>
          <form onChange={onChange}>
            <input className="form-control" placeholder="Input boat name" type="text" id="name"  />{" "}<br/>
            <input className="form-control" placeholder="Input boat brand" type="text" id="brand"  />{" "}<br/>
            <input className="form-control" placeholder="Input boat make" type="text" id="make" />{" "}<br/>
            <input className="form-control" placeholder="Input url to boat image" type="text" id="image"  />{" "}<br/>
            <select className="form-control" id="harbour">
            {harbourData.map((harbour) => (
              <option value={harbour.id} key={harbour.id} name="id" >{harbour.name}</option>
            ))}
            </select><br/>
            <select className="form-control" id="owner">
            {ownerList.map((owner) => (
              <option value={owner.id} key={owner.id} name="owner">{owner.name}</option>
            ))}
            </select><br/>
            <p></p>
            <button className="btn btn-primary" onClick={updateBoat}>Update boat</button>
          </form>
        </div>
        <div className="col-sm-2 text-center"></div>
      </div>
    </div>
  );
}
