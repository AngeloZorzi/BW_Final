package it.epicode.BW_Final.mapper;

import it.epicode.BW_Final.dto.FatturaDTO;
import it.epicode.BW_Final.model.Cliente;
import it.epicode.BW_Final.model.Fattura;
import it.epicode.BW_Final.repository.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FatturaMapper {
    @Autowired
    private StatoFatturaRepository statoRepo;

    public FatturaDTO toDTO(Fattura f) {
        FatturaDTO dto = new FatturaDTO();
        dto.setId(f.getId());
        dto.setData(f.getData());
        dto.setImporto(f.getImporto());
        dto.setNumero(f.getNumero());

        if (f.getStato() != null) {
            dto.setStato(f.getStato().getNome());
        } else {
            dto.setStato("NON ASSEGNATA");
        }

        dto.setClienteId((long) f.getCliente().getId());
        return dto;
    }


    public Fattura toEntity(FatturaDTO dto, Cliente cliente) {
        Fattura f = new Fattura();
        f.setId(dto.getId());
        f.setData(dto.getData());
        f.setImporto(dto.getImporto());
        f.setNumero(dto.getNumero());
        f.setCliente(cliente);
        f.setStato(statoRepo.findByNome(dto.getStato()));
        return f;
    }
}