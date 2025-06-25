package it.epicode.BW_Final.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import it.epicode.BW_Final.model.Comune;
import it.epicode.BW_Final.model.Provincia;
import it.epicode.BW_Final.repository.ComuneRepository;
import it.epicode.BW_Final.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CSVImportService {

    private final ProvinciaRepository provinciaRepo;
    private final ComuneRepository comuneRepo;

    @Transactional
    public void importProvinceCSV(MultipartFile file) throws Exception {
        // Carico in memoria tutti i nomi province già presenti (lowercase)
        Set<String> existingProvinceNames = provinciaRepo.findAll()
                .stream()
                .map(p -> p.getNome().toLowerCase())
                .collect(Collectors.toSet());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVReader csvReader = new CSVReaderBuilder(br)
                     .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                     .build()) {

            String[] line;
            csvReader.readNext(); // salta intestazione

            while ((line = csvReader.readNext()) != null) {
                if (line.length < 3) continue;

                String sigla = line[0].trim();
                String nome = line[1].trim();

                if (nome.isBlank() || sigla.isBlank()) continue;

                if (!existingProvinceNames.contains(nome.toLowerCase())) {
                    Provincia provincia = new Provincia(null, nome, sigla);
                    provinciaRepo.save(provincia);
                    existingProvinceNames.add(nome.toLowerCase());  // aggiorno il set in memoria
                }
            }
        }
    }

    @Transactional
    public void importComuniCSV(MultipartFile file) throws Exception {
        // Carico in memoria tutti i comuni esistenti (lowercase)
        Set<String> existingComuneNames = comuneRepo.findAll()
                .stream()
                .map(c -> c.getNome().toLowerCase())
                .collect(Collectors.toSet());

        // Carico in memoria tutte le province mappate per nome lowercase
        Map<String, Provincia> provinciaMap = provinciaRepo.findAll()
                .stream()
                .collect(Collectors.toMap(p -> p.getNome().toLowerCase(), p -> p));

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVReader csvReader = new CSVReaderBuilder(br)
                     .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                     .build()) {

            String[] line;
            csvReader.readNext(); // salta intestazione

            while ((line = csvReader.readNext()) != null) {
                if (line.length < 4) continue;

                String nomeComune = line[2].trim();
                String nomeProvincia = line[3].trim();

                if (nomeComune.isBlank() || nomeProvincia.isBlank()) continue;

                Provincia provincia = provinciaMap.get(nomeProvincia.toLowerCase());
                if (provincia != null && !existingComuneNames.contains(nomeComune.toLowerCase())) {
                    Comune comune = new Comune(null, nomeComune, provincia);
                    comuneRepo.save(comune);
                    existingComuneNames.add(nomeComune.toLowerCase()); // aggiorno set in memoria
                }
            }
        }
    }
}

