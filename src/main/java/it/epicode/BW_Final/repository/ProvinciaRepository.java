package it.epicode.BW_Final.repository;

import it.epicode.BW_Final.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    Optional<Provincia> findByNomeIgnoreCase(String nome);
}
