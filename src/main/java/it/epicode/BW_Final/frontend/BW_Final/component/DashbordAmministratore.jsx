import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import ToTaleClienti from "./TotaleClienti";
import { Col, Container, Row } from "react-bootstrap";
import "../src/assets/BW.css";
import TotaleFatture from "./TotaleFatture";
import TotaleUtenti from "./TOtaleUtenti";
import GraficoClienti from "./GraficoCLienti";
import GraficoFatturePerStato from "./GraficoFattureperStato";

const DashbordAmministratore = () => {
  return (
    <div className="h-100">
      <div className="sfondo-grigio ">
        <small className=" ms-3 text-secondary">
          Home &gt;{" "}
          <span
            className="
        fw-bold text-black"
          >
            Dashboard
          </span>
        </small>
      </div>
      <div className="sfondo-dashbordCliente h-100">
        <Container>
          <Row className="d-flex justify-content-around">
            <Col xl={3}>
              <ToTaleClienti />
            </Col>
            <Col xl={3}>
              <TotaleFatture />
            </Col>
            <Col xl={3}>
              <TotaleUtenti />
            </Col>
          </Row>
        </Container>
        <Container className="my-4 py-5">
          <Row className="d-flex justify-content-around">
            <Col
              xl={5}
              className="bg-white border border-1 border-secondary rounded "
            >
              <GraficoClienti />
            </Col>
            <Col
              xl={5}
              className="bg-white border border-1 border-secondary rounded "
            >
              <GraficoFatturePerStato />
            </Col>
          </Row>
        </Container>
      </div>
    </div>
  );
};

export default DashbordAmministratore;
