import React, { useEffect, useState } from "react";
import "../src/assets/BW.css";

const Dashboard = () => {
  const [utente, setUtente] = useState(null);
  const [errore, setErrore] = useState(null);

  useEffect(() => {
    const fetchUtente = async () => {
      const token = localStorage.getItem("token");

      if (!token) {
        setErrore("Token mancante. Effettua di nuovo il login.");
        return;
      }

      try {
        const response = await fetch("http://localhost:8081/me", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (response.ok) {
          const data = await response.json();
          setUtente(data);
        } else {
          setErrore("Accesso negato. Token non valido o scaduto.");
        }
        // eslint-disable-next-line no-unused-vars
      } catch (err) {
        setErrore("Errore nella comunicazione col server.");
      }
    };

    fetchUtente();
  }, []);

  return (
    <div className="dashboard-container">
      <div className="dashboard-card">
        <h3 className="dashboard-title">Dashboard</h3>

        {errore && <div className="alert alert-danger">{errore}</div>}

        {utente ? (
          <>
            <h4 className="dashboard-greeting">
              Ciao, {utente.nome} {utente.cognome} 👋
            </h4>
            <p>
              <strong>Email:</strong> {utente.email}
            </p>
            <p>
              <strong>Ruoli:</strong> {utente.ruoli?.join(", ")}
            </p>
          </>
        ) : (
          !errore && (
            <div className="dashboard-loading">Caricamento in corso...</div>
          )
        )}
      </div>
    </div>
  );
};

export default Dashboard;
