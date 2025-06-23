package it.epicode.BW_Final.repository;

import it.epicode.BW_Final.model.StatoFattura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatoFatturaRepository extends JpaRepository<StatoFattura, Long> {
    StatoFattura findByNome(String nome);
}