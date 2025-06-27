import React, { useEffect, useState } from "react";
import "../src/assets/BW.css";
import "bootstrap/dist/css/bootstrap.min.css";

const ToTaleClienti = () => {
  const [totaleClienti, setTotaleClienti] = useState(0);
  const oggi = new Date().toLocaleDateString("it-IT");
  useEffect(() => {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/api/clienti", {
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
        setTotaleClienti(data.totalElements);
      })
      .catch((error) => {
        console.error("Errore:", error.message);
      });
  }, []);
  return (
    <div className="card mt-3 ">
      <div className="card-body ">
        <div className="d-flex flex-column align-items-center justify-content-around">
          <p className="card-text p-0 m-0">
            <span className="fw-bold fs-5 me-3 text-secondary">
              Numero Totale di Clienti:
            </span>
          </p>
          <p className="p-0 m-0 fs-6"> dato aggiornato al:</p>
          <p className="p-0 m-0 fs-6">{oggi}</p>
          <p className="fw-bold fs-3 pt-2 m-0">{totaleClienti}</p>
        </div>
      </div>
    </div>
  );
};

export default ToTaleClienti;
