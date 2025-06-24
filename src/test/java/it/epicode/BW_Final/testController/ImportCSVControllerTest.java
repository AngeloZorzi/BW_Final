package it.epicode.BW_Final.testController;

import it.epicode.BW_Final.controller.ImportCSVController;
import it.epicode.BW_Final.service.CSVImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImportCSVControllerTest {

    @Mock
    private CSVImportService csvImportService;

    @InjectMocks
    private ImportCSVController controller;

    private MultipartFile mockFile;

    @BeforeEach
    void setup() {
        mockFile = new MockMultipartFile(
                "file",
                "province.csv",
                "text/csv",
                "provincia,codice\nMI,001".getBytes()
        );
    }

    @Test
    void shouldImportProvinceSuccessfully() throws Exception {
        doNothing().when(csvImportService).importProvinceCSV(mockFile);

        ResponseEntity<String> response = controller.importProvince(mockFile);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Province importate con successo!", response.getBody());
        verify(csvImportService).importProvinceCSV(mockFile);
    }

    @Test
    void shouldReturnErrorIfProvinceImportFails() throws Exception {
        doThrow(new RuntimeException("Errore simulato")).when(csvImportService).importProvinceCSV(mockFile);

        ResponseEntity<String> response = controller.importProvince(mockFile);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Errore importazione province"));
    }

    @Test
    void shouldImportComuniSuccessfully() throws Exception {
        doNothing().when(csvImportService).importComuniCSV(mockFile);

        ResponseEntity<String> response = controller.importComuni(mockFile);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Comuni importati con successo!", response.getBody());
        verify(csvImportService).importComuniCSV(mockFile);
    }

    @Test
    void shouldReturnErrorIfComuniImportFails() throws Exception {
        doThrow(new RuntimeException("Errore simulato")).when(csvImportService).importComuniCSV(mockFile);

        ResponseEntity<String> response = controller.importComuni(mockFile);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Errore importazione comuni"));
    }
}
