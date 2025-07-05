import React, { useEffect, useState } from "react";
import "bootstrap-icons/font/bootstrap-icons.css";

const Clienti2 = () => {
  const token = localStorage.getItem("token");
  const [clienti, setClienti] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [page, setPage] = useState(0);
  const [size] = useState(6);
  const [sortBy] = useState("ragioneSociale");
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    if (!token) {
      setError("Devi essere autenticato per vedere i clienti.");
      setLoading(false);
      return;
    }

    fetch(
      `http://localhost:8080/api/clienti?page=${page}&size=${size}&sortBy=${sortBy}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then((res) => {
        if (!res.ok) throw new Error("Errore nel recupero clienti");
        return res.json();
      })
      .then((data) => {
        setClienti(data.content || []);
        setTotalPages(data.totalPages || 0);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, [token, page, size, sortBy]);

  const handlePrev = () => page > 0 && setPage(page - 1);
  const handleNext = () => page < totalPages - 1 && setPage(page + 1);

  const getInitials = (name) => {
    return name
      ?.split(" ")
      .map((n) => n[0])
      .join("")
      .toUpperCase()
      .slice(0, 2);
  };

  if (loading) return <div className="loading">Caricamento clienti...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="clienti-container mx-2 px-2 mt-0 pt-0">
      <h2 className="clienti-title">
        <i className="bi bi-people-fill me-2"></i>Lista Clienti
      </h2>

      {clienti.length === 0 ? (
        <p className="empty">Nessun cliente trovato.</p>
      ) : (
        <div className="clienti-grid">
          {clienti.map((cliente) => (
            <div key={cliente.id} className="cliente-card">
              <div className="logo-wrapper">
                {cliente.logoAziendale ? (
                  <img
                    src={cliente.logoAziendale}
                    alt={cliente.ragioneSociale}
                    className="cliente-logo"
                  />
                ) : (
                  <div className="logo-placeholder">
                    {getInitials(cliente.ragioneSociale)}
                  </div>
                )}
              </div>
              <div className="cliente-info">
                <h3 className="ragione-sociale">
                  <i className="bi bi-person-badge me-2 text-primary"></i>
                  {cliente.ragioneSociale}
                </h3>
                <p className="email">
                  <i className="bi bi-envelope me-2 text-secondary"></i>
                  {cliente.emailContatto}
                </p>
                <p className="indirizzo">
                  <i className="bi bi-geo-alt me-2 text-danger"></i>
                  Sede Operativa: {cliente.sedeOperativa?.via},{" "}
                  {cliente.sedeOperativa?.civico}, {cliente.sedeOperativa?.cap}{" "}
                  {cliente.sedeOperativa?.localita} (
                  {cliente.sedeOperativa?.comune?.nome ?? "N/D"})
                </p>
              </div>
            </div>
          ))}
        </div>
      )}

      <div className="pagination">
        <button
          className="pagination-btn"
          onClick={handlePrev}
          disabled={page === 0}
        >
          &laquo; Precedente
        </button>
        <span className="pagination-text">
          Pagina {page + 1} di {totalPages}
        </span>
        <button
          className="pagination-btn"
          onClick={handleNext}
          disabled={page >= totalPages - 1}
        >
          Successiva &raquo;
        </button>
      </div>
    </div>
  );
};

export default Clienti2;
