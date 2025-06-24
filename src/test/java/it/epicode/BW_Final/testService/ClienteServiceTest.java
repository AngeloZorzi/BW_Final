package it.epicode.BW_Final.testService;

import it.epicode.BW_Final.dto.ClienteDto;
import it.epicode.BW_Final.dto.IndirizzoDto;
import it.epicode.BW_Final.exception.UtenteNotFoundException;
import it.epicode.BW_Final.model.Cliente;
import it.epicode.BW_Final.model.Comune;
import it.epicode.BW_Final.model.Indirizzo;
import it.epicode.BW_Final.repository.ClienteRepository;
import it.epicode.BW_Final.repository.ComuneRepository;
import it.epicode.BW_Final.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ComuneRepository comuneRepository;

    private ClienteDto clienteDto;
    private Cliente cliente;
    private Comune comuneLegale;
    private Comune comuneOperativa;

    @BeforeEach
    void init() {
        clienteDto = new ClienteDto();
        clienteDto.setPec("test@pec.it");
        clienteDto.setRagioneSociale("ACME");
        clienteDto.setFatturatoAnnuale(BigDecimal.valueOf(100000));
        clienteDto.setDataInserimento(LocalDate.now());
        clienteDto.setDataUltimoContatto(LocalDate.now());

        IndirizzoDto legale = new IndirizzoDto();
        legale.setVia("Via Roma");
        legale.setCivico("1");
        legale.setCap("00100");
        legale.setLocalita("Roma");
        legale.setIdComune(1L);
        clienteDto.setSedeLegale(legale);

        IndirizzoDto operativa = new IndirizzoDto();
        operativa.setVia("Via Milano");
        operativa.setCivico("2");
        operativa.setCap("20100");
        operativa.setLocalita("Milano");
        operativa.setIdComune(2L);
        clienteDto.setSedeOperativa(operativa);

        cliente = new Cliente();
        cliente.setSedeLegale(new Indirizzo());
        cliente.setSedeOperativa(new Indirizzo());

        comuneLegale = new Comune();
        comuneOperativa = new Comune();
    }

    @Test
    void saveCliente_ok() {
        when(comuneRepository.findById(1L)).thenReturn(Optional.of(comuneLegale));
        when(comuneRepository.findById(2L)).thenReturn(Optional.of(comuneOperativa));
        when(clienteRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Cliente saved = clienteService.saveCliente(clienteDto);
        assertNotNull(saved);
        verify(clienteRepository).save(any());
    }

    @Test
    void getCliente_trovato() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        Cliente found = clienteService.getCliente(1);
        assertEquals(cliente, found);
    }

    @Test
    void getCliente_nonTrovato_lanciaEccezione() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(UtenteNotFoundException.class, () -> clienteService.getCliente(1));
    }

    @Test
    void deleteCliente_ok() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        clienteService.deleteCliente(1);
        verify(clienteRepository).delete(cliente);
    }

    @Test
    void updateDipendente_ok() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(comuneRepository.findById(anyLong())).thenReturn(Optional.of(new Comune()));
        when(clienteRepository.save(any())).thenReturn(cliente);

        Cliente updated = clienteService.updateDipendente(1, clienteDto);
        assertNotNull(updated);
        verify(clienteRepository).save(cliente);
    }

    @Test
    void getClienti_ok() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
        Page<Cliente> page = new PageImpl<>(List.of(cliente));
        when(clienteRepository.findAll(pageable)).thenReturn(page);

        Page<Cliente> result = clienteService.getClienti(0, 5, "id");
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getClientiByNome_ok() {
        Page<Cliente> page = new PageImpl<>(List.of(cliente));
        when(clienteRepository.findByRagioneSocialeContainingIgnoreCase(eq("acme"), any()))
                .thenReturn(page);

        Page<Cliente> result = clienteService.getClientiByNome("acme", 0, 5, "id");
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getClientiByFatturato_ok() {
        Page<Cliente> page = new PageImpl<>(List.of(cliente));
        when(clienteRepository.findByFatturatoBetween(any(), any(), any()))
                .thenReturn(page);

        Page<Cliente> result = clienteService.getClientiByFatturatoAnnuale(
                BigDecimal.valueOf(50000), BigDecimal.valueOf(200000), 0, 5, "id");
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getClientiByDataInserimento_ok() {
        Page<Cliente> page = new PageImpl<>(List.of(cliente));
        when(clienteRepository.findByDataInserimentoBetween(any(), any(), any()))
                .thenReturn(page);

        Page<Cliente> result = clienteService.getClientiByDataInserimento(
                LocalDate.now().minusMonths(1), LocalDate.now(), 0, 5, "id");
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getClientiByUltimoContatto_ok() {
        Page<Cliente> page = new PageImpl<>(List.of(cliente));
        when(clienteRepository.findByDataUltimoContattoBetween(any(), any(), any()))
                .thenReturn(page);

        Page<Cliente> result = clienteService.getClientiByUltimoContatto(
                LocalDate.now().minusDays(10), LocalDate.now(), 0, 5, "id");
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getClientiBySedeLegale_ok() {
        Page<Cliente> page = new PageImpl<>(List.of(cliente));
        when(clienteRepository.findByProvinciaSedeLegale(eq("MI"), any()))
                .thenReturn(page);

        Page<Cliente> result = clienteService.getClientiBySedeLegale("MI", 0, 5, "id");
        assertEquals(1, result.getTotalElements());
    }
}