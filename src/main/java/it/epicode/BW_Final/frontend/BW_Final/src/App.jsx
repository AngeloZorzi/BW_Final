import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import "bootstrap-icons/font/bootstrap-icons.css";
import Login from "../component/Login";
import Dashboard from "../component/Dashboard";
import Registrazione from "../component/Registrazione";
import Titolo from "../component/Titolo";
import BarraSuperiore from "../component/BarraSuperiore";
import BarraLaterale from "../component/BarraLaterale";
import Clienti from "../component/Clienti";
import AboutUs from "../component/AboutUs";

const App = () => {
  const isLoggedIn = !!localStorage.getItem("token");

  return (
    <Routes>
      <Route
        path="/"
        element={
          <>
            <Titolo />
            <Dashboard />
          </>
        }
      />
      <Route
        path="/amministratore"
        element={
          <>
            <BarraSuperiore />
            <BarraLaterale />
          </>
        }
      />
      <Route path="/clienti" element={<Clienti />} />
      <Route path="/about" element={<AboutUs />} />
      <Route path="/register" element={<Registrazione />} />
      <Route path="/login" element={<Login />} />

      <Route
        path="*"
        element={<Navigate to={isLoggedIn ? "/" : "/login"} replace />}
      />
    </Routes>
  );
};

export default App;
