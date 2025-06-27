import React, { useEffect, useState } from "react";
import "../src/assets/BW.css";
import "bootstrap/dist/css/bootstrap.min.css";

const TotaleFatture = () => {
  const [totaleFatture, setTotaleFatture] = useState(0);
  const oggi = new Date().toLocaleDateString("it-IT");
  useEffect(() => {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/api/fatture", {
      method: "GET",
      headers: {
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => {
        if (!response.ok) {
          console.log(token);
          throw new Error("Token non valido o scaduto");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Risposta:", data);
        setTotaleFatture(data.totalElements);
      })
      .catch((error) => {
        console.error("Errore:", error.message);
      });
  }, []);
  return (
    <div className="card mt-3 ">
      <div className="card-body">
        <p className="card-text">
          <div className="d-flex flex-column align-items-center text-secondary ">
            <span className="fw-bold fs-5 me-3">Numero totale fatture:</span>
            <p className="p-0 m-0 fs-6"> dato aggiornato al:</p>
            <p className="p-0 m-0 fs-6">{oggi}</p>
            <p className="fw-bold fs-3 pt-2 m-0 text-black">{totaleFatture}</p>
          </div>
        </p>
      </div>
    </div>
  );
};

export default TotaleFatture;
