package it.epicode.BW_Final.dto;

import lombok.Data;

@Data
public class IndirizzoDto {
    private Long idComune;
    private String via;
    private String civico;
    private String localita;
    private String cap;
    private String comune;
}
