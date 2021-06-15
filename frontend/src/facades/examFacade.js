import utils from "../utils";
import { SERVER_URL_BACKEND } from "../settingsBackend";

function apiFacade() {
  //OBSERVE fetchAny takes a url and a callback. The callback handles the data from the response body.

  /**
   * Backend DATA LOCAL
   */
  // function getData() {
  //   return data;
  // }

  // function addQuestion(question) {
  //   question.id = questions.length + 1;
  //   questions.push(question);
  // }

  // function getQuestions() {
  //   return questions;
  // }

  // function getOldQuestions() {
  //   return questions;
  // }

  // function getQuestion(id) {
  //   return questions.find((q) => q.id === parseInt(id));
  // }

  // function editQuestion(question) {
  //   let questionToEdit = questions.find((q) => q.id === question.id);
  //   questionToEdit.teacher.teacherName = question.teacher.teacherName;
  //   questionToEdit.teacher.zoom_url = question.teacher.zoom_url;
  //   questionToEdit.answer = question.answer;
  //   questionToEdit.teacherLink = question.teacherLink;
  // }

  // function getProxy() {
  //   return proxyData;
  // }

  /**
   * Backend DATA SERVER
   */

  function getData(callback) {
    // Change me to do something with data
    utils.fetchAny(SERVER_URL_BACKEND + "/api", callback);
  };

  function getAllHarbours(callback) {
    utils.fetchAny(SERVER_URL_BACKEND + "/api/harbours", callback);
  }

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
    getAllHarbours,
    getAllOwners,
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