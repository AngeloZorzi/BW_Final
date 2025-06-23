package it.epicode.BW_Final.repository;

import it.epicode.BW_Final.model.Ruolo;
import it.epicode.BW_Final.enumerating.RuoloTipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
    Optional<Ruolo> findByNome(RuoloTipo nome);
}