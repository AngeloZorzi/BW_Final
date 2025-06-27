import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "../src/assets/BW.css";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { useEffect, useState } from "react";

const BarraSuperiore = () => {
  const [username, setUsername] = useState("");
  useEffect(() => {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/amministratore", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Token non valido o scaduto");
        }
        return response.json();
      })
      .then((data) => {
        setUsername(data.utente.username);
      })
      .catch((error) => {
        console.error("Errore:", error.message);
      });
  }, []);
  return (
    <Container fluid className="">
      <Row>
        <Col
          xl={2}
          className="rettangolo-arancione d-flex align-items-center justify-content-center"
        >
          <h3 className="p-2 text-white">Epic Admin</h3>
        </Col>
        <Col
          xl={10}
          className="rettangolo-grigio d-flex align-items-center justify-content-center"
        >
          <h3 className="text-secondary">Benvenuto/a {username}</h3>
        </Col>
      </Row>
    </Container>
  );
};

export default BarraSuperiore;
