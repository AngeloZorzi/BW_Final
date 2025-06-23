package it.epicode.BW_Final.model;

import it.epicode.BW_Final.enumerating.RuoloTipo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ruoli")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ruolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RuoloTipo nome;
}
