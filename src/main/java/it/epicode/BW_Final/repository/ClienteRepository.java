package it.epicode.BW_Final.repository;

import it.epicode.BW_Final.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface ClienteRepository extends JpaRepository<Cliente,Integer>, JpaSpecificationExecutor<Cliente> {

    // Filtro per parte del nome (ragioneSociale) case insensitive
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.ragioneSociale) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Cliente> findByRagioneSocialeContainingIgnoreCase(@Param("nome") String nome, Pageable pageable);

    // Filtro per fatturato annuale compreso tra min e max
    @Query("SELECT c FROM Cliente c WHERE c.fatturatoAnnuale BETWEEN :minFatturato AND :maxFatturato")
    Page<Cliente> findByFatturatoBetween(@Param("minFatturato") BigDecimal minFatturato,
                                         @Param("maxFatturato") BigDecimal maxFatturato, Pageable pageable);

    // Filtro per data di inserimento tra due date
    @Query("SELECT c FROM Cliente c WHERE c.dataInserimento BETWEEN :dataInizio AND :dataFine")
    Page<Cliente> findByDataInserimentoBetween(@Param("dataInizio") LocalDate dataInizio,
                                               @Param("dataFine") LocalDate dataFine, Pageable pageable);

    // Filtro per data di ultimo contatto tra due date
    @Query("SELECT c FROM Cliente c WHERE c.dataUltimoContatto BETWEEN :dataInizio AND :dataFine")
    Page<Cliente> findByDataUltimoContattoBetween(@Param("dataInizio") LocalDate dataInizio,
                                                  @Param("dataFine") LocalDate dataFine, Pageable pageable);

    // Filtro per provincia della sede legale (ordinamento o filtro)
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.sedeLegale.comune.provincia.nome) = LOWER(:provincia)")
    Page<Cliente> findByProvinciaSedeLegale(@Param("provincia") String provincia, Pageable pageable);



}
