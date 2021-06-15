import React from "react";

export default function Footer(props) {

  return (
    <div
      className="fixed-bottom"
      style={{ backgroundColor: "rgba(255,255,255, 0.9)" }}
    >
      <div className="container">
        <div className="row">
          <div className="col-12 col-md-12 text-center">
            <h6 style={{ fontSize: "100%" }}><a href="https://github.com/eelkjaer/3-Semester-Exam">2021 ® Emil Elkjær Nielsen</a></h6>
          </div>
        </div>
      </div>
    </div>
  );
}
