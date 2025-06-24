package it.epicode.BW_Final.testController;

import it.epicode.BW_Final.controller.FatturaController;
import it.epicode.BW_Final.dto.FatturaDTO;
import it.epicode.BW_Final.model.Fattura;
import it.epicode.BW_Final.service.FatturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FatturaControllerTest {

    @Mock
    private FatturaService fatturaService;

    @InjectMocks
    private FatturaController fatturaController;

    private FatturaDTO mockDto;

    @BeforeEach
    void setUp() {
        mockDto = new FatturaDTO();
        // Popola qui i campi richiesti, se il DTO ha vincoli @NotNull, ecc.
    }

    @Test
    void shouldReturnFatturaById() {
        when(fatturaService.getById(1L)).thenReturn(mockDto);

        FatturaDTO result = fatturaController.getById(1L);

        assertEquals(mockDto, result);
    }

    @Test
    void shouldCreateFattura() {
        when(fatturaService.create(mockDto)).thenReturn(mockDto);

        FatturaDTO result = fatturaController.create(mockDto);

        assertEquals(mockDto, result);
        verify(fatturaService).create(mockDto);
    }

    @Test
    void shouldUpdateFattura() {
        when(fatturaService.update(1L, mockDto)).thenReturn(mockDto);

        FatturaDTO result = fatturaController.update(1L, mockDto);

        assertEquals(mockDto, result);
        verify(fatturaService).update(1L, mockDto);
    }

    @Test
    void shouldDeleteFattura() {
        doNothing().when(fatturaService).delete(1L);

        fatturaController.delete(1L);

        verify(fatturaService).delete(1L);
    }

    @Test
    void shouldGetAllWithSpecAndPageable() {
        Page<FatturaDTO> mockPage = mock(Page.class);
        when(fatturaService.getAll(any(), any())).thenReturn(mockPage);

        Page<FatturaDTO> result = fatturaController.getAll(
                null, null, null, null,
                BigDecimal.valueOf(100), BigDecimal.valueOf(1000),
                Pageable.unpaged()
        );

        assertEquals(mockPage, result);
        verify(fatturaService).getAll(any(), any());
    }
}