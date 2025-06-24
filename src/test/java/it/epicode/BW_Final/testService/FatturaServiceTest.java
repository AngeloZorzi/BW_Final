package it.epicode.BW_Final.testService;

import it.epicode.BW_Final.dto.FatturaDTO;
import it.epicode.BW_Final.mapper.FatturaMapper;
import it.epicode.BW_Final.model.Cliente;
import it.epicode.BW_Final.model.Fattura;
import it.epicode.BW_Final.repository.ClienteRepository;
import it.epicode.BW_Final.repository.FatturaRepository;
import it.epicode.BW_Final.service.FatturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class FatturaServiceTest {

    @InjectMocks
    private FatturaService fatturaService;

    @Mock
    private FatturaRepository repo;

    @Mock
    private ClienteRepository clienteRepo;

    @Mock
    private FatturaMapper mapper;

    private FatturaDTO dto;
    private Cliente cliente;
    private Fattura fattura;

    @BeforeEach
    void init() {
        dto = new FatturaDTO();
        dto.setId(1L);
        dto.setNumero(String.valueOf(5));
        dto.setImporto(BigDecimal.valueOf(1000));
        dto.setData(LocalDate.now());
        dto.setClienteId(10L);

        cliente = new Cliente();
        cliente.setId(10);

        fattura = new Fattura();
        fattura.setId(1L);
        fattura.setNumero(String.valueOf(5));
        fattura.setImporto(BigDecimal.valueOf(1000));
        fattura.setData(LocalDate.now());
        fattura.setCliente(cliente);
    }

    @Test
    void getAll_returnsMappedPage() {
        Specification<Fattura> spec = (root, query, cb) -> null;
        Pageable pageable = PageRequest.of(0, 5);
        Page<Fattura> page = new PageImpl<>(List.of(fattura));
        when(repo.findAll(spec, pageable)).thenReturn(page);
        when(mapper.toDTO(fattura)).thenReturn(dto);

        Page<FatturaDTO> result = fatturaService.getAll(spec, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(dto, result.getContent().get(0));
    }

    @Test
    void getById_found_returnsDTO() {
        when(repo.findById(1L)).thenReturn(Optional.of(fattura));
        when(mapper.toDTO(fattura)).thenReturn(dto);

        FatturaDTO result = fatturaService.getById(1L);

        assertEquals(dto, result);
    }

    @Test
    void getById_notFound_throws() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> fatturaService.getById(99L));
    }

    @Test
    void create_ok() {
        when(clienteRepo.findById(10)).thenReturn(Optional.of(cliente));
        when(mapper.toEntity(dto, cliente)).thenReturn(fattura);
        when(repo.save(fattura)).thenReturn(fattura);
        when(mapper.toDTO(fattura)).thenReturn(dto);

        FatturaDTO created = fatturaService.create(dto);

        assertNotNull(created);
        verify(repo).save(fattura);
    }

    @Test
    void create_clienteNotFound_throws() {
        when(clienteRepo.findById(10)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> fatturaService.create(dto));
    }

    @Test
    void update_ok() {
        when(clienteRepo.findById(10)).thenReturn(Optional.of(cliente));
        when(mapper.toEntity(dto, cliente)).thenReturn(fattura);
        when(repo.save(fattura)).thenReturn(fattura);
        when(mapper.toDTO(fattura)).thenReturn(dto);

        FatturaDTO updated = fatturaService.update(1L, dto);

        assertEquals(dto, updated);
        assertEquals(1L, fattura.getId());
    }

    @Test
    void update_clienteNotFound_throws() {
        when(clienteRepo.findById(10)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> fatturaService.update(1L, dto));
    }

    @Test
    void delete_callsRepository() {
        fatturaService.delete(1L);
        verify(repo).deleteById(1L);
    }
}
