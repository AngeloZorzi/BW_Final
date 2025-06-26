import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import "bootstrap-icons/font/bootstrap-icons.css";
import Login from "../component/Login";
import Dashboard from "../component/Dashboard";
import Registrazione from "../component/Registrazione";
import Titolo from "../component/Titolo";

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
      <Route path="/register" element={<Registrazione />} />
      <Route path="/login" element={<Login />} />

      <Route
        path="*"
        element={<Navigate to={isLoggedIn ? "/me" : "/login"} replace />}
      />
    </Routes>
  );
};

export default App;
