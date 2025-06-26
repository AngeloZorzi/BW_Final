package it.epicode.BW_Final.controller;

import it.epicode.BW_Final.dto.FatturaDTO;
import it.epicode.BW_Final.filter.FatturaSpecification;
import it.epicode.BW_Final.model.Fattura;
import it.epicode.BW_Final.service.FatturaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/fatture")
public class FatturaController {
    @Autowired private FatturaService service;

    @GetMapping
    public Page<FatturaDTO> getAll(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String stato,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) Integer anno,
            @RequestParam(required = false) BigDecimal minImporto,
            @RequestParam(required = false) BigDecimal maxImporto,
            Pageable pageable
    ) {
        Specification<Fattura> spec = FatturaSpecification.allFilters(clienteId, stato, data, anno, minImporto, maxImporto);
        return service.getAll(spec, pageable);
    }

    @GetMapping("/{id}")
    public FatturaDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public FatturaDTO create(@RequestBody @Valid FatturaDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public FatturaDTO update(@PathVariable Long id, @RequestBody @Valid FatturaDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}