package it.epicode.BW_Final.repository;

import it.epicode.BW_Final.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
    Optional<Utente> findByUsername(String username);
}