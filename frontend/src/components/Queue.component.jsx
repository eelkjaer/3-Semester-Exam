import React, { useState, useEffect } from "react";
import CreateQModal from "react-responsive-modal";
import ShowQModal from "react-responsive-modal";
import "../style/uddannelsesStyle.css";
import picture from "../images/tutor.jpg";
import "react-responsive-modal/styles.css";
import "../style/modal.css";

export default function Queue(props) {
  const {
    // semesterName,
    semesterId,
    // schoolName,
    // schoolId,
    // uddName,
    // uddId,
    facade,
    isLoggedIn,
  } = props;

  const question = {
    id: -1,
    description: "",
    password: "",
    semesterId: semesterId,
    student: {
      name: "",
      email: "",
    },
    studentLink: "",
    topic: "",
    timestamp: "",
    // teacher: {
    //   teacherName: "",
    //   zoom_url: "",
    // },
    answer: {
      id: -1,
      teacher: {
        id: -1,
        teacherName: "",
        email: "",
        password: "",
        zoom_url: "",
      },
      answer: "",
      teacherLink: "",
    },
  };

  const list = [];

  const [q, setQ] = useState({ ...question });
  const [qList, setQlist] = useState([...list]);
  const [qListToSearch, setQlistToSearch] = useState([...list]);
  const [qToShow, setQtoShow] = useState({ ...question });
  const [oldQ, setOldQ] = useState(false);
  const [opdateList, setOpdateList] = useState(false);
  const [teacherEmail, setTeacherEmail] = useState("");
  // const [opdateTheList, setOpdateTheList] = useState(true);
  //Modals
  const [createQ, setCreateQ] = useState(false);
  const [showQ, setShowQ] = useState(false);
  // let answeredQuestions = { answeredQuestions: false };

  useEffect(() => {
    facade.getQuestions(semesterId, (data) => {
      console.log(data);
      const questions = data.filter((quest) => quest.answer.id === -1);
      setQlist([...questions]);
      setQlistToSearch([...questions]);
    });

    if (isLoggedIn) {
      setTeacherEmail(localStorage.getItem("teacherEmail"));
    }

    // const interval = setInterval(() => {
    //   console.log(oldQ);
    //   console.log("opadate");
    //   facade.getQuestions(semesterId, (data) => {
    //     console.log(data);
    //     if (oldQ === true) {
    //       const alreadyAnsweredQuestions = data.filter(
    //         (quest) => quest.answer.id !== -1
    //       );
    //       setQlist([...alreadyAnsweredQuestions.reverse()]);
    //       setQlistToSearch([...alreadyAnsweredQuestions.reverse()]);
    //     } else {
    //       const notYetAnsweredQuestions = data.filter(
    //         (quest) => quest.answer.id === -1
    //       );
    //       setQlist([...notYetAnsweredQuestions]);
    //       setQlistToSearch([...notYetAnsweredQuestions]);
    //     }
    //   });
    // }, 5000);

    // return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    facade.getQuestions(semesterId, (data) => {
      console.log(data);
      if (oldQ === true) {
        const alreadyAnsweredQuestions = data.filter(
          (quest) => quest.answer.id !== -1
        );
        setQlist([...alreadyAnsweredQuestions.reverse()]);
        setQlistToSearch([...alreadyAnsweredQuestions.reverse()]);
      } else {
        const notYetAnsweredQuestions = data.filter(
          (quest) => quest.answer.id === -1
        );
        setQlist([...notYetAnsweredQuestions]);
        setQlistToSearch([...notYetAnsweredQuestions]);
      }
    });

    const interval = setInterval(() => {
      console.log(oldQ);
      console.log("opadate");
      facade.getQuestions(semesterId, (data) => {
        console.log(data);
        if (oldQ === true) {
          const alreadyAnsweredQuestions = data.filter(
            (quest) => quest.answer.id !== -1
          );
          setQlist([...alreadyAnsweredQuestions.reverse()]);
          setQlistToSearch([...alreadyAnsweredQuestions.reverse()]);
        } else {
          const notYetAnsweredQuestions = data.filter(
            (quest) => quest.answer.id === -1
          );
          setQlist([...notYetAnsweredQuestions]);
          setQlistToSearch([...notYetAnsweredQuestions]);
        }
      });
    }, 5000);

    return () => clearInterval(interval);
  }, [oldQ, opdateList]);

  //Open Modals
  const openCreateQ = () => {
    setQ({ ...question });
    setCreateQ(true);
  };

  const openShowQ = () => {
    setShowQ(true);
  };

  //Close Modals
  const closeCreateQ = () => {
    setCreateQ(false);
  };

  const closeShowQ = () => {
    setQ({ ...question });
    setQtoShow({ ...question });
    setShowQ(false);
  };

  //After Modal open
  // function afterOpenModal() {
  //   // references are now sync'd and can be accessed.
  // }

  /**
   * Create Q Modal
   */
  const handleChangeCreateQ = (event) => {
    //console.log(event.target.value);
    const target = event.target;
    //console.log(target);
    const value = target.type === "checkbox" ? target.checked : target.value;
    //console.log("Value: " + target.type === "checkbox" ? target.checked : target.value);
    const name = target.name;
    console.log("Name: " + name);
    if (name === "name" || name === "email") {
      console.log(name);
      q.student[name] = value;
      setQ({ ...q });
      //setQ({ ...q, [name]: value });
    }
    // else if(name === "teacherName" || name === "zoom_url"){
    //     q.teacher[name] = value;
    //     setQ({ ...q});
    // }
    else {
      q[name] = value;
      setQ({ ...q, [name]: value });
    }
    // q[name] = value;
    // setQ({ ...q, [name]: value });
    console.log(q);
  };

  const handleSubmitCreateQ = (event) => {
    event.preventDefault();

    const theQuestionToSend = {
      description: q.description,
      password: q.password,
      semesterId: q.semesterId,
      student: {
        email: q.student.email,
        name: q.student.name,
      },
      studentLink: q.studentLink,
      topic: q.topic,
    };

    facade.addQuestion(theQuestionToSend, (data) => {
      console.log(data);
      setQ({ ...question });
      setOpdateList(!opdateList); //Bruges til at opdatere liste
    });
    closeCreateQ();
  };

  /**
   * Show Q Modal
   */
  const handleShowQ = (event) => {
    // console.log(event.currentTarget);
    // console.log(event.target);
    // const target = event.currentTarget;
    // console.log(target);
    const id = parseInt(event.currentTarget.id);
    console.log(id);
    const questionToShow = qList.find((quest) => quest.id === id);
    console.log(questionToShow);
    const theQuestionToShow = {
      id: questionToShow.id,
      description: questionToShow.description,
      password: questionToShow.password,
      semesterId: questionToShow.semesterId,
      student: {
        email: questionToShow.student.email,
        name: questionToShow.student.name,
      },
      studentLink: questionToShow.studentLink,
      topic: questionToShow.topic,
      timestamp: questionToShow.timestamp,
      answer: {
        id: questionToShow.answer.id,
        teacher: {
          id: questionToShow.answer.teacher.id,
          teacherName:
            questionToShow.answer.teacher.teacherName === null
              ? ""
              : questionToShow.answer.teacher.teacherName,
          email:
            questionToShow.answer.teacher.email === null
              ? ""
              : questionToShow.answer.teacher.email,
          password:
            questionToShow.answer.teacher.password === null
              ? ""
              : questionToShow.answer.teacher.password,
          zoom_url:
            questionToShow.answer.teacher.zoom_url === null
              ? ""
              : questionToShow.answer.teacher.zoom_url,
        },
        answer:
          questionToShow.answer.answer === null
            ? ""
            : questionToShow.answer.answer,
        teacherLink:
          questionToShow.answer.teacherLink === null
            ? ""
            : questionToShow.answer.teacherLink,
      },
    };
    //console.log(questionToShow);
    setQtoShow({ ...theQuestionToShow });
    setQ({ ...theQuestionToShow }); //Ellers lukker modal når svar bliver mere end ""
    //console.log(q);
    //console.log(qToShow);
    openShowQ();
  };

  const handleChangeShowQ = (event) => {
    //console.log(event.target.value);
    const target = event.target;
    //console.log(target);
    const value = target.type === "checkbox" ? target.checked : target.value;
    //console.log("Value: " + target.type === "checkbox" ? target.checked : target.value);
    const name = target.name;
    console.log("Name: " + name);
    // if (name === "name" || name === "email") {
    //   console.log(name);
    //   q.student[name] = value;
    //   setQ({ ...q, [name]: value });
    // }
    if (
      name === "teacherName" ||
      name === "zoom_url" ||
      name === "email" ||
      name === "password"
    ) {
      q.answer.teacher[name] = value;
      setQ({ ...q });
    } else if (name === "answer") {
      q.answer.answer = value;
      setQ({ ...q });
    } else {
      q.answer[name] = value;
      setQ({ ...q, [name]: value });
    }
    // q[name] = value;
    // setQ({ ...q, [name]: value });
    //console.log(q);
    console.log(qToShow);
  };

  const handleSubmitShowQ = (event) => {
    event.preventDefault();
    const answerToSend = {
      id: q.id,
      answer: {
        teacher: {
          email: teacherEmail,
        },
        answer: q.answer.answer,
        teacherLink: q.answer.teacherLink,
      },
    };
    console.log(answerToSend);
    facade.addAnswer(answerToSend, (data) => {
      console.log(data);
      setQ({ ...question });
      setQtoShow({ ...question });
      setOpdateList(!opdateList); //Bruges til at opdatere liste
    });

    closeShowQ();
  };

  const search = (event) => {
    //console.log(event.target.value);
    const searchField = event.target.value.toLowerCase();
    //console.log("Søger...");
    if (searchField.length > 0) {
      //console.log("Der står noget");
      const newList = qListToSearch.filter(
        (q) =>
          q.answer.answer.toLowerCase().includes(searchField) ||
          q.topic.toLowerCase().includes(searchField) ||
          q.description.toLowerCase().includes(searchField)
      );
      //console.log(newList);
      setQlist([...newList]);
    } else {
      //console.log("Der står IKKE noget");
      setOpdateList(!opdateList); //Bruges til at opdatere liste
    }
  };

  return (
    <div>
      {/* {console.log(schoolName)} */}
      {/* {console.log(schoolId)} */}
      {/* {console.log(uddName)} */}
      {/* {console.log(uddId)} */}
      {/* {console.log(semesterName)} */}
      {console.log(semesterId)}
      {/* {console.log(qList)} */}
      {/* {console.log(oldQ)} */}
      {/* {console.log(
        new Date(question.timestamp).getUTCDate() +
          "-" +
          new Date(question.timestamp).getUTCMonth() +
          "-" +
          new Date(question.timestamp).getUTCFullYear()
      )} */}
      {/* {console.log(new Date(question.timestamp).toLocaleDateString())} */}
      {/* {console.log(new Date(question.timestamp).toTimeString().slice(0, 5))} */}
      {/* <img className="img-fluid" src="images/tutor.png" alt="tutor" /> */}
      <div className="row">
        <div className="col-md-1"></div>
        <div className="col-md-10">
          <h1 className="text-center">Hvad kan jeg hjælpe dig med?</h1>
          <div className="text-center">
            <img className="img-fluid" src={picture} alt="tutor" width="25%" />
          </div>
        </div>
        <div className="col-md-1"></div>
      </div>

      {/* Opret spørgsmål */}
      <div className="row">
        <div className="col-md-1"></div>
        <div className="col-md-10 text-center">
          <button className="btn btn-primary btn-sm" onClick={openCreateQ}>
            Opret et Spørgmål
          </button>
          <p id="errorMsg" style={{ color: "red" }}></p>
        </div>
        <div className="col-md-1"></div>
      </div>
      <br></br>

      {/* Søg i gamle besvarelser */}
      <div className="row">
        <div className="col-md-12">
          <form>
            <div className="form-check">
              <input
                type="checkbox"
                className="form-check-input"
                id="findOldQ"
                onChange={() => setOldQ(!oldQ)}
              />
              <label className="form-check-label" htmlFor="exampleCheck1">
                Tidligere Spørgsmål
              </label>
            </div>
            {oldQ === false ? (
              ""
            ) : (
              <div className="form-group">
                <input
                  type="text"
                  className="form-control w-50"
                  id="find"
                  name="find"
                  placeholder="Søg i emner og spørgsmål..."
                  size="10px"
                  onChange={search}
                />
              </div>
            )}
          </form>
        </div>
      </div>

      {/* Tabel  */}
      <div className="row">
        <div className="col-md-12">
          <table className="table table-striped">
            <thead>
              <tr>
                {oldQ === false ? <th scope="col">Kø</th> : ""}
                <th scope="col">Navn</th>
                <th scope="col">Emne</th>
                <th scope="col">Dato</th>
                <th scope="col">Spørgsmål</th>
              </tr>
            </thead>
            <tbody>
              {qList.map((next, index) => (
                <tr
                  style={{ cursor: "pointer" }}
                  key={next.id}
                  id={next.id}
                  onClick={handleShowQ}
                >
                  {oldQ === false ? <th scope="row">{index + 1}</th> : ""}
                  <td>{next.student.name}</td>
                  <td>{next.topic}</td>
                  <td>
                    {new Date(next.timestamp.replace(" ", "T"))
                      .toLocaleDateString()
                      .replaceAll(".", "-") +
                      " " +
                      new Date(next.timestamp.replace(" ", "T"))
                        .toTimeString()
                        .slice(0, 5)}
                  </td>
                  {/* <td>{next.status === false ? "Afenter" : "Besvaret"}</td> */}
                  <td style={{ maxWidth: "300px" }}>
                    {next.description.length > 80
                      ? next.description.slice(0, 79) + "..."
                      : next.description}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {/* Create Q */}
      <CreateQModal
        open={createQ}
        onClose={closeCreateQ}
        center
        classNames={{
          overlay: "customOverlay",
          modal: "customModal",
        }}
        // isOpen={createQ}
        // onAfterOpen={afterOpenModal}
        // onRequestClose={closeCreateQ}
        // style={bigModalStyle}
        // contentLabel="Create Q"
      >
        <h3 className="text-center">Opret Spørgsmål</h3>
        <hr />
        <form onChange={handleChangeCreateQ} onSubmit={handleSubmitCreateQ}>
          {/* <div className="form-group">
            <label htmlFor="name" className="col-form-label">
              Navn:
            </label>
            <input
              type="text"
              className="form-control"
              id="name"
              name="name"
              placeholder="Skriv dit navn..."
              defaultValue={q.student.name}
              required
            />
          </div> */}

          <div className="form-group">
            <label htmlFor="email" className="col-form-label">
              Email:
            </label>
            <input
              type="email"
              className="form-control"
              id="email"
              name="email"
              placeholder="Skriv din skolemail..."
              defaultValue={q.student.email}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="topic" className="col-form-label">
              Emne:
            </label>
            <input
              type="text"
              className="form-control"
              id="topic"
              name="topic"
              placeholder="Skriv et emne..."
              defaultValue={q.topic}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="question" className="col-form-label">
              Spørgmål:
            </label>
            <textarea
              className="form-control"
              id="description"
              name="description"
              placeholder="Beskriv problemet..."
              defaultValue={q.description}
              rows="5"
              required
            ></textarea>
          </div>

          <div className="form-group">
            <label htmlFor="link" className="col-form-label">
              Ekstern Link:
            </label>
            <input
              type="text"
              className="form-control"
              id="studentLink"
              name="studentLink"
              placeholder="Indsæt evt et link..."
              defaultValue={q.studentLink}
            />
          </div>

          <div className="form-group">
            <label htmlFor="password" className="col-form-label">
              {/* Sikkerhedskode: */}
            </label>
            <input
              type="hidden"
              className="form-control"
              id="password"
              name="password"
              placeholder="Skriv et kodeord..."
              // defaultValue={q.password}
              defaultValue="Kodeord1234"
              required
            />
          </div>

          {/* <input type="hidden" id="id" /> */}

          <hr />

          <div className="form-group">
            <div className="row">
              <div className="col-auto mr-auto"></div>
              <div className="col-auto">
                <button
                  style={{ width: "65px" }}
                  type="button"
                  className="btn btn-dark"
                  onClick={closeCreateQ}
                >
                  Luk
                </button>
                <button
                  style={{ width: "65px" }}
                  type="submit"
                  style={{ marginLeft: 5 }}
                  className="btn btn-primary"
                >
                  Opret
                </button>
              </div>
            </div>
          </div>
        </form>
      </CreateQModal>

      {/* Show Q */}
      <ShowQModal
        open={showQ}
        onClose={closeShowQ}
        center
        classNames={{
          overlay: "customOverlay",
          modal: "customModal",
        }}

        // isOpen={showQ}
        // onAfterOpen={afterOpenModal}
        // onRequestClose={closeShowQ}
        // style={bigModalStyle}
        // contentLabel="Show Q"
      >
        <h3 className="text-center">Spørgsmålet</h3>

        <hr />

        <div>
          <h4 className="text-center">{qToShow.topic}</h4>
        </div>
        <div>
          <p>{qToShow.description}</p>
        </div>
        <br></br>
        <div>
          <a
            href={qToShow.studentLink}
            target="_blank"
            rel="noopener noreferrer"
          >
            <button className="btn btn-primary btn-sm">
              Ekstern Link fra Elev
            </button>
          </a>
        </div>
        <br />
        <hr />

        {qToShow.answer.id === -1 ? (
          isLoggedIn === false ? (
            <div>
              <div>
                <h4 className="text-center">Afventer besvarelse....</h4>
              </div>
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <hr />
              <div className="form-group">
                <div className="row">
                  <div className="col-auto mr-auto"></div>
                  <div className="col-auto">
                    <button
                      style={{ width: "65px" }}
                      type="button"
                      className="btn btn-dark"
                      onClick={closeShowQ}
                    >
                      Luk
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ) : (
            <div>
              <div>
                <h4 className="text-center">Besvar spørgmålet</h4>
              </div>
              <form onChange={handleChangeShowQ} onSubmit={handleSubmitShowQ}>
                {/* <div className="form-group">
                  <label htmlFor="name" className="col-form-label">
                    Navn:
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="teacherName"
                    name="teacherName"
                    placeholder="Skriv dit navn..."
                    defaultValue={qToShow.answer.teacher.teacherName}
                    required
                  />
                </div> */}
                <div className="form-group">
                  <label htmlFor="name" className="col-form-label">
                    Email:
                  </label>
                  <input
                    readOnly
                    type="text"
                    className="form-control"
                    id="email"
                    name="email"
                    placeholder="Skriv din email..."
                    defaultValue={teacherEmail}
                    required
                  />
                </div>
                {/* <div className="form-group">
                  <label htmlFor="name" className="col-form-label">
                    Kodeord:
                  </label>
                  <input
                    type="password"
                    className="form-control"
                    id="password"
                    name="password"
                    placeholder="Skriv dit kodeord..."
                    defaultValue={qToShow.answer.teacher.password}
                    required
                  />
                </div> */}
                <div className="form-group">
                  <label htmlFor="question" className="col-form-label">
                    Svar:
                  </label>
                  <textarea
                    className="form-control"
                    id="answer"
                    name="answer"
                    placeholder="Skriv dit svar her..."
                    defaultValue={qToShow.answer.answer}
                    rows="5"
                    required
                  ></textarea>
                </div>
                {/* <div className="form-group">
                  <label htmlFor="link" className="col-form-label">
                    Zoom:
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="zoom_url"
                    name="zoom_url"
                    placeholder="Indsæt Zoom link..."
                    defaultValue={qToShow.answer.teacher.zoom_url}
                  />
                </div> */}
                <div className="form-group">
                  <label htmlFor="link" className="col-form-label">
                    Ekstern Link:
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    id="teacherLink"
                    name="teacherLink"
                    placeholder="Indsæt link..."
                    defaultValue={qToShow.answer.teacherLink}
                  />
                </div>
                <br />
                <hr />
                <div className="form-group">
                  <div className="row">
                    <div className="col-auto mr-auto"></div>
                    <div className="col-auto">
                      <button
                        style={{ width: "65px" }}
                        type="button"
                        className="btn btn-dark"
                        onClick={closeShowQ}
                      >
                        Luk
                      </button>
                      <button
                        style={{ width: "65px" }}
                        id={qToShow.id}
                        //   onClick={}
                        style={{ marginLeft: 5 }}
                        className="btn btn-primary"
                      >
                        Besvar
                      </button>
                    </div>
                  </div>
                </div>
              </form>
            </div>
          )
        ) : (
          <div>
            <div>
              <h4 className="text-center">
                Besvaret af: {qToShow.answer.teacher.teacherName}
              </h4>
            </div>
            <div>
              <p>{qToShow.answer.answer}</p>
            </div>

            <br></br>
            <div>
              <a
                href={qToShow.answer.teacher.zoom_url}
                target="_blank"
                rel="noopener noreferrer"
              >
                <button className="btn btn-primary btn-sm">Zoom</button>
              </a>
            </div>
            <br></br>
            <div>
              <a
                href={qToShow.answer.teacherLink}
                target="_blank"
                rel="noopener noreferrer"
              >
                <button className="btn btn-primary btn-sm">
                  Ekstern Link fra Tutor
                </button>
              </a>
            </div>
            <br />
            <hr />
            <div className="form-group">
              <div className="row">
                <div className="col-auto mr-auto"></div>
                <div className="col-auto">
                  <button
                    style={{ width: "65px" }}
                    type="button"
                    className="btn btn-dark"
                    onClick={closeShowQ}
                  >
                    Luk
                  </button>
                </div>
              </div>
            </div>
          </div>
        )}
      </ShowQModal>
    </div>
  );
}
