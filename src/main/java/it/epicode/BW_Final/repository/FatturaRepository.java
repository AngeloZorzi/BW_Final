package it.epicode.BW_Final.repository;

import it.epicode.BW_Final.model.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FatturaRepository extends JpaRepository<Fattura, Long>, JpaSpecificationExecutor<Fattura> {}