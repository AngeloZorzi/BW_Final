package it.epicode.BW_Final.dto;

import lombok.Data;

@Data
public class UtenteDto {
    private String username;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String avatar;
}
