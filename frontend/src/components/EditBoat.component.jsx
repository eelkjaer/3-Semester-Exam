import React, { useState, useEffect } from "react";
import {
  Redirect,
  useParams
} from "react-router-dom";


export default function EditBoat(props){
  const {
    facade,
    harbourData,
    ownerList
  } = props;

  const { id } = useParams()

  const boatId = id;
  const [boat, setBoat] = useState("");
  const [posted, setPosted] = useState(false);


  const updateBoat = (evt) => {
    evt.preventDefault();

    const objectToSend = {
      id: boat.id,
      harbour: {
        id: boat.harbour.id
      },
      owners: 
        boat.owners
      ,
      brand: boat.brand,
      make: boat.make,
      name: boat.name,
      image: boat.image
    };

    facade.updateBoat(objectToSend, (data) => {
      console.log(JSON.stringify(objectToSend))
      setBoat(data);
      setPosted(true);
    });

  };

  const onChange = (evt) => {
    setBoat({
      ...boat,
      [evt.target.id]: evt.target.value,
    });
    console.log(boat)
  };


  useEffect(() => {
    facade.getBoatById(boatId, (data) => {
      const boat = data;
      setBoat(boat);
    });
  }, [facade]);

  return (
    posted ? <Redirect push to="/admin"/> : 
    <div className="container">
      <div className="row">
        <div className="col-sm-2 text-center"></div>
        <div className="col-sm-8 text-center">
        <h2>Edit boat</h2>
        <form onChange={onChange}>
            <input className="form-control" placeholder="Input boat name" type="text" id="name"  defaultValue={boat.name}/>{" "}<br/>
            <input className="form-control" placeholder="Input boat brand" type="text" id="brand" defaultValue={boat.brand} />{" "}<br/>
            <input className="form-control" placeholder="Input boat make" type="text" id="make" defaultValue={boat.make} />{" "}<br/>
            <input className="form-control" placeholder="Input url to boat image" type="text" id="image" defaultValue={boat.image} />{" "}<br/>
            <select className="form-control" id="harbour">
            {harbourData.map((harbour) => (
              <option value={harbour.id} key={harbour.id} name="id" >{harbour.name}</option>
            ))}
            </select><br/>
            <select className="form-control" id="owner" disabled>
            {ownerList.map((owner) => (
              <option value={owner.id} key={owner.id} name="owner">{owner.name}</option>
            ))}
            </select><br/>
            <p></p>
            <button className="btn btn-primary" onClick={updateBoat} >Update boat</button>
          </form>
        </div>
        <div className="col-sm-2 text-center"></div>
      </div>
    </div>
  );
}
