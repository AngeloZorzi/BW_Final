import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./assets/Login";
import Dashboard from "./assets/Dashboard";

const App = () => {
  const isLoggedIn = !!localStorage.getItem("token");

  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route
        path="/me"
        element={isLoggedIn ? <Dashboard /> : <Navigate to="/login" replace />}
      />
      <Route
        path="*"
        element={<Navigate to={isLoggedIn ? "/me" : "/login"} replace />}
      />
    </Routes>
  );
};

export default App;
