import utils from "../utils";
import { SERVER_URL_BACKEND } from "../settingsBackend";

function apiFacade() {

  function getData(callback) {
    utils.fetchAny(SERVER_URL_BACKEND + "/api", callback);
  };

  function getAllHarbours(callback) {
    utils.fetchAny(SERVER_URL_BACKEND + "/api/harbours", callback);
  }

  function getAllBoats(callback) {
    utils.fetchAny(SERVER_URL_BACKEND + "/api/boats", callback);
  }

  function getBoatById(id, callback) {
    utils.fetchAny(
      SERVER_URL_BACKEND + "/api/boat/" + id,
      callback
    );
  };

  //US1
  function getAllOwners(callback) {
    utils.fetchAny(
      SERVER_URL_BACKEND + "/api/owners",
      callback
    );
  };

  //US2
  function getBoatsByHarbourId(id, callback) {
    utils.fetchAny(
      SERVER_URL_BACKEND + "/api/boats/harbour/" + id,
      callback
    );
  };

  //US3
  function getOwnersByBoatId(id, callback) {
    utils.fetchAny(
      SERVER_URL_BACKEND + "/api/harbour/" + id,
      callback
    );
  };

  //US4
  function createBoat(boat, callback) {
    utils.fetchAny(
      SERVER_URL_BACKEND + "/api/boat",
      callback,
      "POST",
      boat
    );
  };

    //US5
  function giveBoatNewHarbour(boat, callback) {
    utils.fetchAny(
      SERVER_URL_BACKEND + "/api/boat/changeharbour",
      callback,
      "PUT",
      boat
    );
    };

    //US6
  function updateBoat(boat, callback) {
    utils.fetchAny(
      SERVER_URL_BACKEND + "/api/boat",
      callback,
      "PUT",
      boat
    );
    };

    //US7
  function deleteBoat(id, callback) {
    utils.fetchAny(
      SERVER_URL_BACKEND + "/api/boat/" + id,
      callback,
      "DELETE"
    );
  };
    

  return {
    getData,
    getBoatById,
    getAllHarbours,
    getAllOwners,
    getAllBoats,
    getBoatsByHarbourId,
    getOwnersByBoatId,
    createBoat,
    giveBoatNewHarbour,
    updateBoat,
    deleteBoat
  };
}


const facade = apiFacade();
export default facade;