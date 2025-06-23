package it.epicode.BW_Final.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Indirizzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String via;
    private String civico;
    private String localita;
    private String cap;

    @ManyToOne
    @JoinColumn(name = "comune_id")
    private Comune comune;

    @OneToOne(mappedBy = "sedeLegale")
    private Cliente clienteSedeLegale;

    @OneToOne(mappedBy = "sedeOperativa")
    private Cliente clienteSedeOperativa;
}

