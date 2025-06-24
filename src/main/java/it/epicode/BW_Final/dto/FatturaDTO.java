package it.epicode.BW_Final.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class FatturaDTO {
    private Long id;
    private LocalDate data;
    private BigDecimal importo;
    private String numero;
    private String stato;
    private Long clienteId;
}
