package it.epicode.BW_Final.testService;

import it.epicode.BW_Final.exception.UtenteNotFoundException;
import it.epicode.BW_Final.enumeration.RuoloTipo;
import it.epicode.BW_Final.model.Utente;
import it.epicode.BW_Final.repository.UtenteRepository;
import it.epicode.BW_Final.service.UtenteDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.userdetails.UserDetails;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtenteDetailsServiceTest {

    @InjectMocks
    private UtenteDetailsService service;

    @Mock
    private UtenteRepository utenteRepository;

    private Utente utente;

    @BeforeEach
    void setUp() {
        utente = Utente.builder()
                .id(1L)
                .username("admin")
                .password("encodedpassword")
                .email("admin@example.com")
                .ruoli(Set.of(RuoloTipo.ADMIN, RuoloTipo.USER))
                .build();
    }

    @Test
    void loadUserByUsername_found_shouldReturnUserDetailsWithRoles() {
        when(utenteRepository.findByUsername("admin")).thenReturn(Optional.of(utente));

        UserDetails userDetails = service.loadUserByUsername("admin");

        assertNotNull(userDetails);
        assertEquals("admin", userDetails.getUsername());
        assertEquals("encodedpassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_notFound_shouldThrowException() {
        when(utenteRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(UtenteNotFoundException.class, () -> service.loadUserByUsername("ghost"));
    }
}
