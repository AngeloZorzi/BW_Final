package it.epicode.BW_Final.testController;

import it.epicode.BW_Final.controller.AuthController;
import it.epicode.BW_Final.dto.UtenteDto;
import it.epicode.BW_Final.enumeration.RuoloTipo;
import it.epicode.BW_Final.model.Utente;
import it.epicode.BW_Final.security.JwtUtil;
import it.epicode.BW_Final.service.UtenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UtenteService utenteService;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private AuthController controller;

    private UtenteDto dto;

    @BeforeEach
    void setUp() {
        dto = new UtenteDto();
        dto.setUsername("testuser");
        dto.setPassword("password");
        dto.setEmail("test@example.com");
        dto.setNome("Test");
        dto.setCognome("User");
    }

    @Test
    void shouldRegisterWithDefaultUserRole() {
        Utente utenteCreato = new Utente();
        when(utenteService.registraUtente(any(Utente.class), eq(Set.of(RuoloTipo.USER))))
                .thenReturn(utenteCreato);

        ResponseEntity<?> response = controller.register(dto);

        assertEquals(200, response.getStatusCodeValue());
        verify(utenteService).registraUtente(any(Utente.class), eq(Set.of(RuoloTipo.USER)));
        assertEquals(utenteCreato, response.getBody());
    }

    @Test
    void shouldLoginAndReturnToken() {
        Map<String, String> loginData = Map.of("username", "testuser", "password", "password");

        when(authManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(userDetails.getAuthorities()).thenReturn((Set) Set.of(new SimpleGrantedAuthority("ROLE_USER")));
        when(jwtUtil.generateToken("testuser", "ROLE_USER")).thenReturn("jwt.token.test");

        ResponseEntity<?> response = controller.login(loginData);

        assertEquals(200, response.getStatusCodeValue());
        Map<?, ?> body = (Map<?, ?>) response.getBody();
        assertNotNull(body);
        assertEquals("jwt.token.test", body.get("token"));
    }
}