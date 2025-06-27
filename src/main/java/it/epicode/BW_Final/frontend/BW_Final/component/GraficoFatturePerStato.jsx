import React, { useEffect, useState } from "react";
import {
  PieChart,
  Pie,
  Cell,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";

const GraficoFatturePerStato = () => {
  const [datiFatture, setDatiFatture] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/api/fatture?page=0&size=1000", {
      headers: {
        Authorization: "Bearer " + token,
      },
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        const conteggio = {};

        data.content.forEach((fattura) => {
          const stato = fattura.stato;
          conteggio[stato] = (conteggio[stato] || 0) + 1;
        });

        const dati = Object.entries(conteggio).map(([nome, valore]) => ({
          nome,
          valore,
        }));

        setDatiFatture(dati);
      })
      .catch((err) => console.error("Errore:", err));
  }, []);

  const colori = ["#8884d8", "#82ca9d", "#ffc658", "#ff7300", "#a4de6c"];

  return (
    <div style={{ width: "100%", height: 300 }}>
      <h4 className="text-center">Fatture per Stato</h4>
      <ResponsiveContainer>
        <PieChart>
          <Pie
            data={datiFatture}
            dataKey="valore"
            nameKey="nome"
            cx="50%"
            cy="50%"
            outerRadius={100}
            label
          >
            {datiFatture.map((entry, index) => (
              <Cell
                key={`cell-${index}`}
                fill={colori[index % colori.length]}
              />
            ))}
          </Pie>
          <Tooltip />
          <Legend />
        </PieChart>
      </ResponsiveContainer>
    </div>
  );
};

export default GraficoFatturePerStato;
