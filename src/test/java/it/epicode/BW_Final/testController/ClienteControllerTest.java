package it.epicode.BW_Final.testController;

import it.epicode.BW_Final.controller.ClienteController;
import it.epicode.BW_Final.dto.ClienteDto;
import it.epicode.BW_Final.model.Cliente;
import it.epicode.BW_Final.service.ClienteService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ClienteController clienteController;

    private ClienteDto clienteDto;

    @BeforeEach
    void setUp() {
        clienteDto = new ClienteDto();
        // Qui puoi settare campi necessari se ClienteDto ha validazioni obbligatorie
    }

    @Test
    void shouldThrowValidationExceptionIfBindingHasErrors() {
        ObjectError error = new ObjectError("clienteDto", "Il campo 'nome' è obbligatorio");
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(error));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            clienteController.saveCliente(clienteDto, bindingResult);
        });

        assertTrue(exception.getMessage().contains("Il campo 'nome'"));
    }

    @Test
    void shouldCallServiceWhenInputIsValid() {
        when(bindingResult.hasErrors()).thenReturn(false);
        Cliente mockCliente = new Cliente();
        when(clienteService.saveCliente(clienteDto)).thenReturn(mockCliente);

        Cliente result = clienteController.saveCliente(clienteDto, bindingResult);

        assertNotNull(result);
        verify(clienteService, times(1)).saveCliente(clienteDto);
    }
}
