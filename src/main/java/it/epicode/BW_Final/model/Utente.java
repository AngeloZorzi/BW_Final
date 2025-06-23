package it.epicode.BW_Final.model;

import it.epicode.BW_Final.enumeration.RuoloTipo;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "utenti")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String nome;

    private String cognome;

    private String avatar;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "utente_ruoli", joinColumns = @JoinColumn(name = "utente_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo")
    private Set<RuoloTipo> ruoli = new HashSet<>();
}
