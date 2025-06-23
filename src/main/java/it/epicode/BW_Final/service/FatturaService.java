package it.epicode.BW_Final.service;

import it.epicode.BW_Final.dto.FatturaDTO;
import it.epicode.BW_Final.mapper.FatturaMapper;
import it.epicode.BW_Final.model.Cliente;
import it.epicode.BW_Final.model.Fattura;
import it.epicode.BW_Final.repository.ClienteRepository;
import it.epicode.BW_Final.repository.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FatturaService {
    @Autowired
    private FatturaRepository repo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private FatturaMapper mapper;

    public Page<FatturaDTO> getAll(Specification<Fattura> spec, Pageable pageable) {
        return repo.findAll(spec, pageable).map(mapper::toDTO);
    }

    public FatturaDTO getById(Long id) {
        return mapper.toDTO(repo.findById(id).orElseThrow(() -> new RuntimeException("Fattura non trovata")));
    }

    public FatturaDTO create(FatturaDTO dto) {
        Cliente cliente = clienteRepo.findById(dto.getClienteId()).orElseThrow(() -> new RuntimeException("Cliente non trovato"));
        Fattura fattura = mapper.toEntity(dto, cliente);
        return mapper.toDTO(repo.save(fattura));
    }

    public FatturaDTO update(Long id, FatturaDTO dto) {
        Cliente cliente = clienteRepo.findById(dto.getClienteId()).orElseThrow(() -> new RuntimeException("Cliente non trovato"));
        Fattura fattura = mapper.toEntity(dto, cliente);
        fattura.setId(id);
        return mapper.toDTO(repo.save(fattura));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
