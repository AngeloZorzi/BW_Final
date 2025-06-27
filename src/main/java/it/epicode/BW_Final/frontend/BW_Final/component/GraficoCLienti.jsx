import React, { useEffect, useState } from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

const GraficoClienti = () => {
  const [datiGrafico, setDatiGrafico] = useState([]);
  useEffect(() => {
    const token = localStorage.getItem("token");
    fetch("http://localhost:8080/api/clienti", {
      headers: {
        Authorization: "Bearer " + token,
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error("Errore nella richiesta");
        return res.json();
      })
      .then((clienti) => {
        const conteggioPerMese = {};

        clienti.content.forEach((cliente) => {
          const data = new Date(cliente.dataInserimento);
          const mese = data.toLocaleString("it-IT", {
            month: "short",
            year: "numeric",
          });

          conteggioPerMese[mese] = (conteggioPerMese[mese] || 0) + 1;
        });

        const mesiIT = {
          gen: "01",
          feb: "02",
          mar: "03",
          apr: "04",
          mag: "05",
          giu: "06",
          lug: "07",
          ago: "08",
          set: "09",
          ott: "10",
          nov: "11",
          dic: "12",
        };

        const dati = Object.entries(conteggioPerMese)
          .sort((a, b) => {
            const [mA, yA] = a[0].split(" ");
            const [mB, yB] = b[0].split(" ");
            const dateA = new Date(`${yA}-${mesiIT[mA.toLowerCase()]}-01`);
            const dateB = new Date(`${yB}-${mesiIT[mB.toLowerCase()]}-01`);
            return dateA - dateB;
          })
          .map(([mese, valore]) => ({ mese, clienti: valore }));

        setDatiGrafico(dati);
      })
      .catch((err) => console.error(err.message));
  }, []);
  return (
    <div style={{ width: "100%", height: 300 }} className=" me-4 p-4">
      <h4 className="text-center">Clienti Registrati per Mese</h4>
      <ResponsiveContainer>
        <LineChart data={datiGrafico}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="mese" />
          <YAxis allowDecimals={false} />
          <Tooltip />
          <Line type="monotone" dataKey="clienti" stroke="#8884d8" />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default GraficoClienti;
