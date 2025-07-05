import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import { HiInformationCircle } from "react-icons/hi2";
import { ImMail4 } from "react-icons/im";
import Clienti2 from "./Clienti2";
import DashbordAmministratore from "./DashbordAmministratore";

const BarraLaterale = () => {
  const [avatar, setAvatar] = useState("");
  const [nome, setNome] = useState("");
  const [cognome, setCognome] = useState("");
  const [imgErrore, setImgErrore] = useState(false);
  const [sezioneAttiva, setSezioneAttiva] = useState("dashboard");
  const initial = nome.charAt(0).toUpperCase();

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
        setAvatar(data.utente.avatar);
        setNome(data.utente.nome);
        setCognome(data.utente.cognome);
      })
      .catch((error) => {
        console.error("Errore:", error.message);
      });
  }, []);

  return (
    <div className="h-100 d-flex">
      <Container className="h-100 m-0 rettangolo-grigio-laterale p-0">
        <Row>
          <Col xl={3} className="p-0 d-flex justify-content-end">
            <div className=" fs-2 pt-5 mt-2 ">
              <HiInformationCircle style={{ color: "white" }} />
            </div>
          </Col>
          <Col xl={6} className="mt-4 d-flex justify-content-center p-0">
            {imgErrore || !avatar ? (
              <div className="avatar-fallback">{initial}</div>
            ) : (
              <img
                src={avatar}
                alt="Avatar"
                className="avatar-img"
                onError={() => setImgErrore(true)}
              />
            )}
          </Col>
          <Col xl={3} className="p-0">
            <div className=" fs-2 pt-5 mt-2">
              <ImMail4 style={{ color: "white" }} />
            </div>
          </Col>
        </Row>
        <div className="d-flex flex-column align-items-center mt-2 text-white">
          <h6 className="m-0">
            {nome} {cognome}
          </h6>
          <small className="p-0 mb-4">Web developer super bravo</small>
        </div>
        <span
          onClick={() => setSezioneAttiva("dashboard")}
          className="text-white fw-bold "
        >
          <div className="d-flex flex-column align-items-center border-top border-2 border-secondary p-2">
            DashBoard
          </div>
        </span>
        <span
          onClick={() => setSezioneAttiva("clienti")}
          className="text-white fw-bold "
        >
          <div className="d-flex flex-column align-items-center border-top border-2 border-secondary p-2">
            Clienti
          </div>
        </span>
      </Container>
      <div className="w-100 ">
        {sezioneAttiva === "dashboard" && <DashbordAmministratore />}
        {sezioneAttiva === "clienti" && <Clienti2 />}
      </div>
    </div>
  );
};

export default BarraLaterale;
