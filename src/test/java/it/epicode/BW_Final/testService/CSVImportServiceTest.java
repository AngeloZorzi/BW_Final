package it.epicode.BW_Final.testService;

import it.epicode.BW_Final.model.Comune;
import it.epicode.BW_Final.model.Provincia;
import it.epicode.BW_Final.repository.ComuneRepository;
import it.epicode.BW_Final.repository.ProvinciaRepository;
import it.epicode.BW_Final.service.CSVImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.mockito.Mockito.*;

class CSVImportServiceTest {

    @InjectMocks
    private CSVImportService service;

    @Mock
    private ProvinciaRepository provinciaRepo;

    @Mock
    private ComuneRepository comuneRepo;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void importProvinceCSV_shouldImportValidRows() throws Exception {
        String csv = "Nome,Sigla\nMilano,MI\nRoma,RM\n";

        MockMultipartFile file = new MockMultipartFile(
                "file", "province.csv", "text/csv", csv.getBytes(StandardCharsets.UTF_8));

        when(provinciaRepo.findByNomeIgnoreCase("Milano")).thenReturn(Optional.empty());
        when(provinciaRepo.findByNomeIgnoreCase("Roma")).thenReturn(Optional.empty());

        service.importProvinceCSV(file);

        verify(provinciaRepo, times(2)).save(any(Provincia.class));
    }

    @Test
    void importComuniCSV_shouldImportValidRows() throws Exception {
        String csv = "Col1,Col2,Comune,Provincia\n_,_,Bergamo,Bergamo\n_,_,Firenze,Firenze\n";
        MockMultipartFile file = new MockMultipartFile(
                "file", "comuni.csv", "text/csv", csv.getBytes(StandardCharsets.UTF_8));

        Provincia bergamo = new Provincia(1L, "Bergamo", "BG");
        Provincia firenze = new Provincia(2L, "Firenze", "FI");

        when(provinciaRepo.findByNomeIgnoreCase("Bergamo")).thenReturn(Optional.of(bergamo));
        when(provinciaRepo.findByNomeIgnoreCase("Firenze")).thenReturn(Optional.of(firenze));

        when(comuneRepo.findByNomeIgnoreCase("Bergamo")).thenReturn(Optional.empty());
        when(comuneRepo.findByNomeIgnoreCase("Firenze")).thenReturn(Optional.empty());

        service.importComuniCSV(file);

        verify(comuneRepo, times(2)).save(any(Comune.class));
    }

    @Test
    void importProvinceCSV_shouldIgnoreDuplicate() throws Exception {
        String csv = "Nome,Sigla\nMilano,MI\n";
        MockMultipartFile file = new MockMultipartFile(
                "file", "province.csv", "text/csv", csv.getBytes(StandardCharsets.UTF_8));

        when(provinciaRepo.findByNomeIgnoreCase("Milano")).thenReturn(Optional.of(new Provincia()));

        service.importProvinceCSV(file);

        verify(provinciaRepo, never()).save(any());
    }

    @Test
    void importComuniCSV_shouldIgnoreIfProvinciaNotFound() throws Exception {
        String csv = "Col1,Col2,Comune,Provincia\n_,_,Bologna,Inesistente\n";
        MockMultipartFile file = new MockMultipartFile(
                "file", "comuni.csv", "text/csv", csv.getBytes(StandardCharsets.UTF_8));

        when(provinciaRepo.findByNomeIgnoreCase("Inesistente")).thenReturn(Optional.empty());

        service.importComuniCSV(file);

        verify(comuneRepo, never()).save(any());
    }
}