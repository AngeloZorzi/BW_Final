package it.epicode.BW_Final.service;

import it.epicode.BW_Final.exception.RuoloNotFoundException;
import it.epicode.BW_Final.model.Utente;
import it.epicode.BW_Final.model.Ruolo;
import it.epicode.BW_Final.enumeration.RuoloTipo;
import it.epicode.BW_Final.repository.UtenteRepository;
import it.epicode.BW_Final.repository.RuoloRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final RuoloRepository ruoloRepository;
    private final PasswordEncoder passwordEncoder;


    public Utente registraUtente(Utente utente, Set<RuoloTipo> ruoliTipi) {

        utente.setPassword(passwordEncoder.encode(utente.getPassword()));

        Set<Ruolo> ruoli = new HashSet<>();

        for (RuoloTipo tipo : ruoliTipi) {
            Ruolo ruolo = ruoloRepository.findByNome(tipo)
                    .orElseThrow(() -> new RuoloNotFoundException("Ruolo non trovato: " + tipo));
            ruoli.add(ruolo);
        }
        utente.setRuoli(ruoli);

        return utenteRepository.save(utente);
    }

    public Optional<Utente> findByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    public Optional<Utente> findByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }
}
