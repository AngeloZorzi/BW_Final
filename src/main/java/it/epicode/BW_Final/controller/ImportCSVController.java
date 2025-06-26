package it.epicode.BW_Final.controller;

import it.epicode.BW_Final.service.CSVImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/import")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ImportCSVController {

    private final CSVImportService csvImportService;

    @PostMapping("/province")
    public ResponseEntity<String> importProvince(@RequestParam("file") MultipartFile file) {
        try {
            csvImportService.importProvinceCSV(file);
            return ResponseEntity.ok("Province importate con successo!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore importazione province: " + e.getMessage());
        }
    }

    @PostMapping("/comuni")
    public ResponseEntity<String> importComuni(@RequestParam("file") MultipartFile file) {
        try {
            csvImportService.importComuniCSV(file);
            return ResponseEntity.ok("Comuni importati con successo!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore importazione comuni: " + e.getMessage());
        }
    }
}
