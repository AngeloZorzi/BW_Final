package it.epicode.BW_Final.controller;

import it.epicode.BW_Final.model.StatoFattura;
import it.epicode.BW_Final.repository.StatoFatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statifattura")
@PreAuthorize("hasRole('ADMIN')")
public class StatoFatturaController {

    @Autowired
    private StatoFatturaRepository statoRepo;

    @GetMapping
    public List<StatoFattura> getAll() {
        return statoRepo.findAll();
    }

    @PostMapping
    public StatoFattura create(@RequestBody StatoFattura stato) {
        return statoRepo.save(stato);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        statoRepo.deleteById(id);
    }
}
