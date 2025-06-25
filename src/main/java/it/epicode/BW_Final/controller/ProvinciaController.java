package it.epicode.BW_Final.controller;

import it.epicode.BW_Final.model.Provincia;
import it.epicode.BW_Final.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/province")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ProvinciaController {

    private final ProvinciaRepository provinciaRepo;

    @GetMapping
    public List<Provincia> getAll() {
        return provinciaRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provincia> getById(@PathVariable Long id) {
        return provinciaRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Provincia create(@RequestBody Provincia provincia) {
        return provinciaRepo.save(provincia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Provincia> update(@PathVariable Long id, @RequestBody Provincia updated) {
        return provinciaRepo.findById(id)
                .map(p -> {
                    p.setNome(updated.getNome());
                    p.setSigla(updated.getSigla());
                    return ResponseEntity.ok(provinciaRepo.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Provincia> patch(@PathVariable Long id, @RequestBody Provincia partial) {
        return provinciaRepo.findById(id)
                .map(p -> {
                    if (partial.getNome() != null) p.setNome(partial.getNome());
                    if (partial.getSigla() != null) p.setSigla(partial.getSigla());
                    return ResponseEntity.ok(provinciaRepo.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!provinciaRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        provinciaRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
