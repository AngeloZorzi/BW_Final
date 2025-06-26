package it.epicode.BW_Final.repository;

import it.epicode.BW_Final.model.Comune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComuneRepository extends JpaRepository<Comune, Long> {
    Optional<Comune> findByNomeIgnoreCase(String nome);
    List<Comune> findByNomeContainingIgnoreCase(String nome);
    List<Comune> findByProvinciaId(Long provinciaId);
    List<Comune> findByProvincia_NomeIgnoreCase(String nome);
}

