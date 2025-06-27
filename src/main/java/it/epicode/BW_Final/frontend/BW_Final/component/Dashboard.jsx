import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import Container from "react-bootstrap/Container";
import { Dropdown } from "react-bootstrap";
import logo from "../src/assets/logo.jpg";
import "../src/assets/BW.css";

const Dashboard = () => {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const [userData, setUserData] = useState(null);

  useEffect(() => {
    if (token) {
      fetch("http://localhost:8080/auth/me", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => {
          if (!res.ok) throw new Error("Errore nel recupero dati utente");
          return res.json();
        })
        .then((data) => {
          setUserData(data);
        })
        .catch((err) => {
          console.error("Errore nel fetch dell'utente:", err);
          localStorage.removeItem("token");
          navigate("/login");
        });
    }
  }, [token, navigate]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  // Controllo se l’utente ha il ruolo ADMIN
  const isAdmin = userData?.ruoli?.includes("ADMIN");

  return (
    <div className="dashboard-body">
      {/* === NAVBAR === */}
      <Navbar expand="lg" className="custom-navbar">
        <Container fluid>
          <Navbar.Brand as={Link} to="/" className="navbar-brand-custom">
            <img src={logo} alt="Epic Energi Logo" className="navbar-logo" />
          </Navbar.Brand>

          <Navbar.Toggle
            aria-controls="navbar-content"
            className="navbar-toggle"
          />
          <Navbar.Collapse id="navbar-content">
            <Nav className="ms-auto nav-links">
              <Nav.Link as={Link} to="/" className="nav-item-link me-3">
                Home
              </Nav.Link>
              <Nav.Link as={Link} to="/about" className="nav-item-link me-3">
                About Us
              </Nav.Link>
              {token && (
                <Nav.Link
                  as={Link}
                  to="/clienti"
                  className="nav-item-link me-3"
                >
                  Clienti
                </Nav.Link>
              )}
              {token && isAdmin && (
                <Nav.Link
                  as={Link}
                  to="/amministratore"
                  className="nav-item-link me-3"
                >
                  Area Admin
                </Nav.Link>
              )}
              <Nav.Link
                href="mailto:contatti@epicenergi.it?subject=Richiesta informazioni"
                className="nav-item-link me-3"
              >
                Contact Us
              </Nav.Link>

              {!token ? (
                <Nav.Link
                  as={Link}
                  to="/register"
                  className="nav-item-register"
                >
                  Register
                </Nav.Link>
              ) : (
                <Dropdown>
                  <Dropdown.Toggle
                    variant="dark"
                    className="user-dropdown border border-0 me-3"
                  >
                    {userData ? userData.username : "Utente"}
                  </Dropdown.Toggle>
                  <Dropdown.Menu>
                    <Dropdown.Item onClick={handleLogout}>Logout</Dropdown.Item>
                  </Dropdown.Menu>
                </Dropdown>
              )}
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      {/* === CONTENUTO PRINCIPALE === */}
      <div className="dashboard-container">
        <div className="dashboard-card">
          <h1 className="dashboard-title">
            Benvenuto in <span className="highlight-text">EpiEnergy</span>
          </h1>
          <p className="dashboard-greeting">
            {userData ? (
              <>
                Ciao{" "}
                <strong>
                  {userData.nome} {userData.cognome}
                </strong>{" "}
                👋
              </>
            ) : (
              "Caricamento in corso..."
            )}
          </p>
          <p className="dashboard-description">
            Questa è la tua area personale. Da qui puoi gestire clienti,
            contratti, offerte e molto altro.
          </p>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
