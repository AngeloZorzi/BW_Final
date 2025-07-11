package it.epicode.BW_Final.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.epicode.BW_Final.enumeration.TipoCliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Cliente {
    @Id
    @GeneratedValue
    private int id;
    @NotBlank
    private String ragioneSociale;
    private String partitaIva;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private BigDecimal fatturatoAnnuale;
    @Email
    private String pec;
    private String telefono;
    @Email
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale;
    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sede_legale_id")
    @JsonManagedReference
    private Indirizzo sedeLegale;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sede_operativa_id")
    @JsonManagedReference
    private Indirizzo sedeOperativa;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Fattura> fatture = new ArrayList<>();

}
