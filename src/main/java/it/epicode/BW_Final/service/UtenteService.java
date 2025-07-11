package it.epicode.BW_Final.service;

import it.epicode.BW_Final.exception.NotFoundException;
import it.epicode.BW_Final.model.Utente;
import it.epicode.BW_Final.enumeration.RuoloTipo;
import it.epicode.BW_Final.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Optional<Utente> findByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }

    public Page<Utente> getAllUtentePaginati(Pageable pageable) {
        return utenteRepository.findAll(pageable);
    }
}
