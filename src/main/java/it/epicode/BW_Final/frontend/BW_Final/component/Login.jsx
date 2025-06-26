import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../src/assets/BW.css";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errore, setErrore] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });

      if (response.ok) {
        const data = await response.json();
        localStorage.setItem("token", data.token);
        console.log({ username, password });
        navigate("/me");
      } else {
        setErrore("Credenziali non valide");
        console.log({ username, password });
      }
    } catch {
      setErrore("Errore di connessione al server");
    }
  };

  return (
    <div className="auth-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2 className="login-title">Accedi</h2>
        {errore && <div className="alert alert-danger">{errore}</div>}
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          autoFocus
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit" className="btn-login">
          Login
        </button>
      </form>
    </div>
  );
};

export default Login;
