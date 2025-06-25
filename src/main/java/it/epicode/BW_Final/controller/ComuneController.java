package it.epicode.BW_Final.controller;

import it.epicode.BW_Final.model.Comune;
import it.epicode.BW_Final.model.Provincia;
import it.epicode.BW_Final.repository.ComuneRepository;
import it.epicode.BW_Final.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comuni")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ComuneController {

    private final ComuneRepository comuneRepo;
    private final ProvinciaRepository provinciaRepo;

    @GetMapping
    public List<Comune> getAll() {
        return comuneRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comune> getById(@PathVariable Long id) {
        Optional<Comune> comuneOpt = comuneRepo.findById(id);
        return comuneOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Comune> create(@RequestBody Comune comune) {
        if (comune.getProvincia() == null || comune.getProvincia().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Provincia> provinciaOpt = provinciaRepo.findById(comune.getProvincia().getId());
        if (provinciaOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        comune.setProvincia(provinciaOpt.get());
        Comune saved = comuneRepo.save(comune);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comune> update(@PathVariable Long id, @RequestBody Comune updated) {
        Optional<Comune> comuneOpt = comuneRepo.findById(id);
        if (comuneOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Comune comune = comuneOpt.get();
        comune.setNome(updated.getNome());

        if (updated.getProvincia() != null && updated.getProvincia().getId() != null) {
            Optional<Provincia> provinciaOpt = provinciaRepo.findById(updated.getProvincia().getId());
            if (provinciaOpt.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            comune.setProvincia(provinciaOpt.get());
        }

        Comune saved = comuneRepo.save(comune);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comune> patch(@PathVariable Long id, @RequestBody Comune partial) {
        Optional<Comune> comuneOpt = comuneRepo.findById(id);
        if (comuneOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Comune comune = comuneOpt.get();

        if (partial.getNome() != null) {
            comune.setNome(partial.getNome());
        }

        if (partial.getProvincia() != null && partial.getProvincia().getId() != null) {
            Optional<Provincia> provinciaOpt = provinciaRepo.findById(partial.getProvincia().getId());
            if (provinciaOpt.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            comune.setProvincia(provinciaOpt.get());
        }

        Comune saved = comuneRepo.save(comune);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!comuneRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        comuneRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
