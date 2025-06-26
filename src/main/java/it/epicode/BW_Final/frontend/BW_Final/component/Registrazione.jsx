import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../src/assets/BW.css";

const Registrazione = () => {
  const [nome, setNome] = useState("");
  const [cognome, setCognome] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errore, setErrore] = useState("");
  const [email, setEmail] = useState("");
  const [avatar, setAvatar] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          nome,
          cognome,
          username,
          password,
          email,
          avatar,
          ruoli: ["USER"],
        }),
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem("token", data.token);
        navigate("/me");
      } else {
        setErrore("Credenziali non valide");
      }
    } catch {
      setErrore("Errore di connessione al server");
    }
  };

  return (
    <div className="auth-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2 className="login-title">Registrati</h2>
        {errore && <div className="alert alert-danger">{errore}</div>}

        <input
          type="text"
          placeholder="Nome"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Cognome"
          value={cognome}
          onChange={(e) => setCognome(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <input
          type="text"
          placeholder="Avatar URL"
          value={avatar}
          onChange={(e) => setAvatar(e.target.value)}
        />
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <button type="submit" className="btn-login">
          Registrati
        </button>
      </form>
    </div>
  );
};

export default Registrazione;
