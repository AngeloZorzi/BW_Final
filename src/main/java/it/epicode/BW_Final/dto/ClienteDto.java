package it.epicode.BW_Final.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClienteDto {

    @NotEmpty
    private String ragioneSociale;
    @NotEmpty
    private String partitaIva;
    @NotNull
    private LocalDate dataInserimento;
    @NotNull
    private LocalDate dataUltimoContatto;
        @NotNull
    private BigDecimal fatturatoAnnuale;
    @Email
    private String pec;
    @NotEmpty
    private String telefono;
    @Email
    private String emailContatto;
    @NotEmpty
    private String nomeContatto;
    @NotEmpty
    private String cognomeContatto;
    @NotEmpty
    private String telefonoContatto;
    @NotEmpty
    private String logoAziendale;
    @NotEmpty
    private String tipoCliente;
    @NotNull
    private IndirizzoDto sedeLegale;
    @NotNull
    private IndirizzoDto sedeOperativa;

}
