package it.epicode.BW_Final.service;

import it.epicode.BW_Final.exception.NotFoundException;
import it.epicode.BW_Final.model.Utente;
import it.epicode.BW_Final.enumeration.RuoloTipo;
import it.epicode.BW_Final.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public Utente registraUtente(Utente utente, Set<RuoloTipo> ruoliTipi) {
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        utente.setRuoli(ruoliTipi);
        return utenteRepository.save(utente);
    }

    public Optional<Utente> findByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    public Utente findByUsername(String username) throws NotFoundException {
        return utenteRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("Utente non trovato"));
    }
}
