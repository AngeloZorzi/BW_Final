package it.epicode.BW_Final.controller;

import it.epicode.BW_Final.dto.UtenteDto;
import it.epicode.BW_Final.enumeration.RuoloTipo;
import it.epicode.BW_Final.model.Utente;
import it.epicode.BW_Final.security.JwtUtil;
import it.epicode.BW_Final.service.UtenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UtenteService utenteService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UtenteDto dto) {
        Utente nuovoUtente = Utente.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nome(dto.getNome())
                .cognome(dto.getCognome())
                .avatar(dto.getAvatar())
                .build();


        Set<RuoloTipo> ruoli = dto.getRuoli() != null && !dto.getRuoli().isEmpty()
                ? dto.getRuoli()
                : Collections.singleton(RuoloTipo.USER);

        Utente salvato = utenteService.registraUtente(nuovoUtente, ruoli);
        return ResponseEntity.ok(salvato);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginData.get("username"),
                        loginData.get("password")
                )
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        String roles = String.join(",", userDetails.getAuthorities().stream().map(a -> a.getAuthority()).toList());
        String token = jwtUtil.generateToken(userDetails.getUsername(), roles);

        return ResponseEntity.ok(Map.of("token", token));
    }
}
