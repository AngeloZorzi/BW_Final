import React from "react";
import squirrelImg from "../src/assets/squirrel.png";
import "../src/assets/BW.css";

const AboutUs = () => {
  return (
    <div className="aboutus-body">
      <div className="aboutus-container">
        <div className="aboutus-card">
          <h1 className="aboutus-title">
            Chi Siamo — Energia e... Scoiattoli!
          </h1>
          <img
            src={squirrelImg}
            alt="Scoiattolo e energia"
            className="aboutus-image"
          />
          <p className="aboutus-description" style={{ marginTop: "1rem" }}>
            Da anni garantiamo energia affidabile e sostenibile. Ma, come ogni
            grande sfida, anche noi affrontiamo ostacoli inaspettati: gli
            scoiattoli, veri protagonisti non ufficiali, sembrano aver scelto
            proprio i nostri cavi come campo di battaglia.
            <br />
            <br />
            Se la tua luce a volte fa le bizze, ricorda: non è magia, è solo la
            natura che prova a giocare con la corrente. Nel frattempo, noi
            lavoriamo senza sosta per mantenere il flusso costante... e le
            noccioline ben nascoste.
          </p>
        </div>
      </div>
    </div>
  );
};

export default AboutUs;
