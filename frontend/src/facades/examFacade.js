import utils from "../utils";
import { SERVER_URL_BACKEND } from "../settingsBackend";
// import { SERVER_URL } from "../settings";
// import { data } from "../data/data";

// let questions = [
//   {
//     id: 1,
//     semesterId: 23,
//     student: {
//       name: "TestName",
//       email: "test@test.dk",
//     },
//     topic: "Test emne",
//     description: "Dette er mit test problem...",
//     studentLink: "https://google.com",
//     password: "1234",
//     timestamp: "2021-05-05 12:52:07.0",
//     teacher: {
//       teacherName: "Arik",
//       zoom_url: "https://zoom.com",
//     },
//     answer: "Dette er min besvarelse til dig",
//     teacherLink: "https://codedev.dk",
//   },
//   {
//     id: 2,
//     semesterId: 23,
//     student: {
//       name: "Hans",
//       email: "test@test.dk",
//     },
//     topic: "New Test emne",
//     description: "Dette er mit test problem...",
//     studentLink: "https://google.com",
//     password: "1234",
//     timestamp: "2021-05-05 12:52:07.0",
//     teacher: {
//       teacherName: "",
//       zoom_url: "",
//     },
//     answer: "",
//     teacherLink: "",
//   },
// ];

// const proxyData = {
//   tested: 13495620,
//   infected: 198472,
//   recovered: 187318,
//   deceased: 2125,
//   dailyInfected: 377,
//   dailyDead: 19,
//   dailyRecovered: 706,
//   uniqueTests: 4200576,
//   admissions: 534,
//   respirator: 73,
//   intensive: 98,
//   newAdmissions: 21,
//   admissionsDiff: -7,
//   respiratorDiff: -6,
//   intensiveDiff: -4,
//   uniqueTestsDiff: 6948,
//   testsDiff: 126275,
//   dailyInfectedDiff: -54,
//   country: "Denmark",
//   historyData:
//     "https://api.apify.com/v2/datasets/Ugq8cNqnhUSjfJeHr/items?format=json&clean=1",
//   sourceUrl:
//     "https://www.ssi.dk/sygdomme-beredskab-og-forskning/sygdomsovervaagning/c/covid19-overvaagning",
//   lastUpdatedAtApify: "2021-05-06T18:00:00.000Z",
//   lastUpdatedAtSource: "2021-01-31T00:00:00.000Z",
//   readMe: "https://apify.com/tugkan/covid-dk",
// };

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
    utils.fetchAny(SERVER_URL_BACKEND + "/api/schools/all", callback);
  }

  function addQuestion(question, callback) {
    utils.fetchAny(
      SERVER_URL_BACKEND + "/api/question",
      callback,
      "POST",
      question
    );
  }

  function getQuestions(semesterID, callback) {
    utils.fetchAny(
      SERVER_URL_BACKEND + "/api/questions/semester/" + semesterID,
      callback
    );
  }

  function getProxy(callback) {
    utils.fetchAny(SERVER_URL_BACKEND + "/api/covid", callback);
  }

  function addAnswer(answer, callback) {
    utils.fetchAny(
      SERVER_URL_BACKEND + "/api/question",
      callback,
      "PUT",
      answer
    );
  }

  return {
    getData,
    addQuestion,
    getQuestions,
    // getOldQuestions,
    // getQuestion,
    // editQuestion,
    getProxy,
    addAnswer,
  };
}
const facade = apiFacade();
export default facade;
