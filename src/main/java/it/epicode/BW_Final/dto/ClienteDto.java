package it.epicode.BW_Final.dto;


import it.epicode.BW_Final.enumeration.TipoCliente;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClienteDto {

    @NotEmpty
    @NotBlank(message = "La ragione sociale è obbligatoria")
    private String ragioneSociale;

    @NotEmpty
    @NotBlank(message = "La partita IVA è obbligatoria")
    private String partitaIva;

    @NotNull(message = "La data di inserimento è obbligatoria")
    private LocalDate dataInserimento;

    @NotNull(message = "La data dell'ultimo contatto è obbligatoria")
    private LocalDate dataUltimoContatto;

    @NotNull(message = "Il fatturato è obbligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "Il fatturato deve essere positivo")
    private BigDecimal fatturatoAnnuale;

    @Email
    private String pec;

    @NotEmpty
    @NotBlank(message = "Il numero di telefono è obbligatorio")
    private String telefono;

    @Email(message = "Email non valida")
    private String emailContatto;

    @NotEmpty
    private String nomeContatto;

    @NotEmpty
    private String cognomeContatto;

    @NotEmpty
    private String telefonoContatto;

    @NotEmpty
    private String logoAziendale;


    private TipoCliente tipoCliente;

    @NotNull(message = "L'indirizzo della sede legale è obbligatorio")
    private IndirizzoDto sedeLegale;

    @NotNull(message = "L'indirizzo della sede operativa è obbligatorio")
    private IndirizzoDto sedeOperativa;

}
