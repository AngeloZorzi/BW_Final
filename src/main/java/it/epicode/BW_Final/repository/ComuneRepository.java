package it.epicode.BW_Final.repository;

import it.epicode.BW_Final.model.Comune;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ComuneRepository extends JpaRepository<Comune, Long> {
    Optional<Comune> findByNomeIgnoreCase(String nome);
}

