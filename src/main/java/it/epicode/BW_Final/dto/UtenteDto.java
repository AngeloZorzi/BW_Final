package it.epicode.BW_Final.dto;

import it.epicode.BW_Final.enumeration.RuoloTipo;
import lombok.Data;

import java.util.Set;

@Data
public class UtenteDto {
    private String username;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String avatar;
    private Set<RuoloTipo> ruoli;
}
