package it.epicode.BW_Final.service;

import it.epicode.BW_Final.exception.UtenteNotFoundException;
import it.epicode.BW_Final.model.Utente;
import it.epicode.BW_Final.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtenteDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UtenteNotFoundException("Utente non trovato: " + username));

        return User.builder()
                .username(utente.getUsername())
                .password(utente.getPassword())
                .authorities(
                        utente.getRuoli().stream()
                                .map(ruoloTipo -> "ROLE_" + ruoloTipo.name())
                                .toArray(String[]::new)
                )
                .build();
    }
}
