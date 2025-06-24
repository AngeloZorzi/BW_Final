package it.epicode.BW_Final.service;

import com.opencsv.CSVReader;
import it.epicode.BW_Final.model.Comune;
import it.epicode.BW_Final.model.Provincia;
import it.epicode.BW_Final.repository.ComuneRepository;
import it.epicode.BW_Final.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CSVImportService {

    private final ProvinciaRepository provinciaRepo;
    private final ComuneRepository comuneRepo;

    public void importProvinceCSV(MultipartFile file) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVReader csvReader = new CSVReader(br)) {
            String[] line;
            csvReader.readNext(); // skip header

            while ((line = csvReader.readNext()) != null) {
                if (line.length < 2) continue; // evita errori

                String sigla = line[0].trim();
                String nome = line[1].trim();

                if (nome.isEmpty() || sigla.isEmpty()) continue;

                if (provinciaRepo.findByNomeIgnoreCase(nome).isEmpty()) {
                    Provincia provincia = new Provincia(null, nome, sigla);
                    provinciaRepo.save(provincia);
                }
            }
        }
    }

    public void importComuniCSV(MultipartFile file) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVReader csvReader = new CSVReader(br)) {
            String[] line;
            csvReader.readNext();

            while ((line = csvReader.readNext()) != null) {
                String nomeComune = line[2].trim();
                String nomeProvincia = line[3].trim();

                Optional<Provincia> provinciaOpt = provinciaRepo.findByNomeIgnoreCase(nomeProvincia);
                if (provinciaOpt.isPresent() && comuneRepo.findByNomeIgnoreCase(nomeComune).isEmpty()) {
                    Comune comune = new Comune(null, nomeComune, provinciaOpt.get());
                    comuneRepo.save(comune);
                }
            }
        }
    }
}

