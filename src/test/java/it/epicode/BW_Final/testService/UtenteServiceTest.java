package it.epicode.BW_Final.testService;

import it.epicode.BW_Final.enumeration.RuoloTipo;
import it.epicode.BW_Final.model.Utente;
import it.epicode.BW_Final.repository.UtenteRepository;
import it.epicode.BW_Final.service.UtenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtenteServiceTest {

    @InjectMocks
    private UtenteService utenteService;

    @Mock
    private UtenteRepository utenteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Utente utente;

    @BeforeEach
    void setUp() {
        utente = new Utente();
        utente.setUsername("johndoe");
        utente.setEmail("johndoe@example.com");
        utente.setPassword("rawPassword");
        utente.setNome("John");
        utente.setCognome("Doe");
    }

    @Test
    void registraUtente_shouldEncodePasswordAndSave() {
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(utenteRepository.save(any(Utente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Set<RuoloTipo> ruoli = Set.of(RuoloTipo.USER);
        Utente result = utenteService.registraUtente(utente, ruoli);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(ruoli, result.getRuoli());
        verify(utenteRepository).save(utente);
    }

    @Test
    void findByEmail_shouldReturnUtente() {
        when(utenteRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.of(utente));

        Optional<Utente> result = utenteService.findByEmail("johndoe@example.com");

        assertTrue(result.isPresent());
        assertEquals("johndoe", result.get().getUsername());
    }

    @Test
    void findByUsername_shouldReturnUtente() {
        when(utenteRepository.findByUsername("johndoe")).thenReturn(Optional.of(utente));

        Optional<Utente> result = utenteService.findByUsername("johndoe");

        assertTrue(result.isPresent());
        assertEquals("johndoe@example.com", result.get().getEmail());
    }
}