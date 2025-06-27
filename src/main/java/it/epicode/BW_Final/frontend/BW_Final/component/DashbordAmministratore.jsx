import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import ToTaleClienti from "./ToTaleClienti";
import { Col, Container, Row } from "react-bootstrap";
import "../src/assets/BW.css";

const DashbordAmministratore = () => {
  return (
    <div>
      <div className="sfondo-grigio">
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
      <Container>
        <Row>
          <Col xl={3}>
            <ToTaleClienti />
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default DashbordAmministratore;
