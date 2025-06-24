package it.epicode.BW_Final.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class IndirizzoDto {
    @NotNull
    private Long idComune;

    @NotBlank(message = "La via è obbligatoria")
    private String via;

    @NotNull(message = "Il numero civico è obbligatorio")
    private String civico;

    @NotBlank(message = "La località è obbligatoria")
    private String localita;

    @NotBlank(message = "Il CAP è obbligatorio")
    @Pattern(regexp = "\\d{5}", message = "CAP non valido")
    private String cap;

    @NotBlank(message = "Il comune è obbligatorio")
    private String comune;
}
