package it.epicode.BW_Final.controller;

import it.epicode.BW_Final.exception.NotFoundException;
import it.epicode.BW_Final.model.Utente;
import it.epicode.BW_Final.security.JwtUtil;
import it.epicode.BW_Final.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/amministratore")
@RequiredArgsConstructor
public class UtenteController {
    @Autowired
    private  JwtUtil jwtUtil;
    @Autowired
    private UtenteService utenteService;

    @GetMapping
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) throws NotFoundException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        Utente utente = utenteService.findByUsername(username);
        return ResponseEntity.ok(Map.of("utente", utente));
    }

    @GetMapping("/api/utenti")
    public Page<Utente> getAllUtentePaginati(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return utenteService.getAllUtentePaginati(pageable);
    }
}
