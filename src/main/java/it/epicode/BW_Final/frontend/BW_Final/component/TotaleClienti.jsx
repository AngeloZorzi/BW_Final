import React, { useEffect } from "react";
import "../src/assets/BW.css";
import "bootstrap/dist/css/bootstrap.min.css";

const ToTaleClienti = () => {
  useEffect(() => {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/clienti", {
      method: "GET",
      headers: {
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Token non valido o scaduto");
        }
        return response.json();
      })
      .then((data) => {
        console.log(data.lenght);
      })
      .catch((error) => {
        console.error("Errore:", error.message);
      });
  }, []);
  return (
    <div className="card mt-3 ">
      <div className="card-body sfondo-grigio">
        <p className="card-text">
          <span className="fw-bold fs-5 me-3">Numero Totale di Clienti:</span>
        </p>
      </div>
    </div>
  );
};

export default ToTaleClienti;
