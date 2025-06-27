import "../src/assets/BW.css";
import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { Form, InputGroup, FormControl } from "react-bootstrap";
import { FaSearch } from "react-icons/fa";

const Dashboard = () => {
  return (
    <Navbar expand="lg" className="bg-body-white">
      <Container className="mx-5 px-5 ">
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Row className="w-100">
            <Col className="p-1 col-6">
              <Nav className="d-flex justify-content-between">
                <Nav.Link
                  href="#home"
                  className=" fw-bold text-primary-emphasis"
                >
                  Home
                </Nav.Link>
                <Nav.Link
                  href="#link"
                  className=" fw-bold text-primary-emphasis"
                >
                  Our company
                </Nav.Link>
                <Nav.Link
                  href="#about"
                  className="fw-bold text-primary-emphasis"
                >
                  Carrers
                </Nav.Link>
                <Nav.Link
                  href="#services"
                  className="fw-bold text-primary-emphasis"
                >
                  Inventor
                </Nav.Link>
                <Nav.Link
                  href="#contact"
                  className="fw-bold text-primary-emphasis"
                >
                  Contact us
                </Nav.Link>
              </Nav>
            </Col>
            <Col className="col-6">
              <Nav className="">
                {" "}
                <Form className="d-flex ">
                  <InputGroup>
                    <InputGroup.Text>
                      <FaSearch />
                    </InputGroup.Text>
                    <FormControl
                      type="search"
                      placeholder="Cerca..."
                      className="me-2"
                      aria-label="Search"
                    />
                  </InputGroup>
                </Form>
              </Nav>
            </Col>
          </Row>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Dashboard;
