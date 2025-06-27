package it.epicode.BW_Final.service;


import it.epicode.BW_Final.dto.ClienteDto;
import it.epicode.BW_Final.dto.IndirizzoDto;
import it.epicode.BW_Final.exception.ComuneNotFoundException;
import it.epicode.BW_Final.exception.UtenteNotFoundException;
import it.epicode.BW_Final.model.Cliente;
import it.epicode.BW_Final.model.Indirizzo;
import it.epicode.BW_Final.repository.ClienteRepository;
import it.epicode.BW_Final.repository.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.Month;
import java.util.TreeMap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClienteService {
@Autowired
private ClienteRepository clienteRepository;
@Autowired
private ComuneRepository comuneRepository;


public Cliente saveCliente(ClienteDto clienteDto){
    Cliente cliente = new Cliente();
    cliente.setTipoCliente(clienteDto.getTipoCliente());
    cliente.setPec(clienteDto.getPec());
    cliente.setCognomeContatto(clienteDto.getCognomeContatto());
    cliente.setEmailContatto(clienteDto.getEmailContatto());
    cliente.setDataInserimento(clienteDto.getDataInserimento());
    cliente.setDataUltimoContatto(clienteDto.getDataUltimoContatto());
    cliente.setRagioneSociale(clienteDto.getRagioneSociale());
    cliente.setPartitaIva(clienteDto.getPartitaIva());
    cliente.setFatturatoAnnuale(clienteDto.getFatturatoAnnuale());
    cliente.setTelefono(clienteDto.getTelefono());
    cliente.setTelefonoContatto(clienteDto.getTelefonoContatto());
    cliente.setNomeContatto(clienteDto.getNomeContatto());
    //Indirizzo legale
    IndirizzoDto legaleDto = clienteDto.getSedeLegale();
    Indirizzo sedeLegale = new Indirizzo();
    sedeLegale.setVia(legaleDto.getVia());
    sedeLegale.setCivico(legaleDto.getCivico());
    sedeLegale.setLocalita(legaleDto.getLocalita());
    sedeLegale.setCap(legaleDto.getCap());
    sedeLegale.setComune(comuneRepository.findById(legaleDto.getIdComune())
            .orElseThrow(() -> new ComuneNotFoundException("Comune sede legale non trovato")));

  //Indirizzo Operativo
    IndirizzoDto operativaDto = clienteDto.getSedeOperativa();
    Indirizzo sedeOperativa = new Indirizzo();
    sedeOperativa.setVia(operativaDto.getVia());
    sedeOperativa.setCivico(operativaDto.getCivico());
    sedeOperativa.setLocalita(operativaDto.getLocalita());
    sedeOperativa.setCap(operativaDto.getCap());
    sedeOperativa.setComune(comuneRepository.findById(operativaDto.getIdComune())
            .orElseThrow(() -> new ComuneNotFoundException("Comune sede operativa non trovato")));
    cliente.setSedeLegale(sedeLegale);
    cliente.setSedeOperativa(sedeOperativa);

    return clienteRepository.save(cliente);

}

public Cliente getCliente(int id){
    return clienteRepository.findById(id).orElseThrow(()-> new  UtenteNotFoundException("UtenteNonTrovato"));
}

public Page<Cliente> getClienti(int pagina, int size, String sortBy){
    Pageable pageable = PageRequest.of(pagina,size, Sort.by(sortBy).ascending());
    return clienteRepository.findAll(pageable);
}

public Cliente updateDipendente(int id,ClienteDto clienteDto){
    Cliente cliente = getCliente(id);
    cliente.setTipoCliente(clienteDto.getTipoCliente());
    cliente.setPec(clienteDto.getPec());
    cliente.setCognomeContatto(clienteDto.getCognomeContatto());
    cliente.setEmailContatto(clienteDto.getEmailContatto());
    cliente.setDataInserimento(clienteDto.getDataInserimento());
    cliente.setDataUltimoContatto(clienteDto.getDataUltimoContatto());
    cliente.setRagioneSociale(clienteDto.getRagioneSociale());
    cliente.setPartitaIva(clienteDto.getPartitaIva());
    cliente.setFatturatoAnnuale(clienteDto.getFatturatoAnnuale());
    cliente.setTelefono(clienteDto.getTelefono());
    cliente.setTelefonoContatto(clienteDto.getTelefonoContatto());
    cliente.setNomeContatto(clienteDto.getNomeContatto());
    IndirizzoDto legaleDto = clienteDto.getSedeLegale();
    Indirizzo sedeLegale = cliente.getSedeLegale();
    sedeLegale.setVia(legaleDto.getVia());
    sedeLegale.setCivico(legaleDto.getCivico());
    sedeLegale.setLocalita(legaleDto.getLocalita());
    sedeLegale.setCap(legaleDto.getCap());
    sedeLegale.setComune(comuneRepository.findById(legaleDto.getIdComune())
            .orElseThrow(() -> new ComuneNotFoundException("Comune sede legale non trovato")));

    IndirizzoDto operativaDto = clienteDto.getSedeOperativa();
    Indirizzo sedeOperativa = cliente.getSedeOperativa();
    sedeOperativa.setVia(operativaDto.getVia());
    sedeOperativa.setCivico(operativaDto.getCivico());
    sedeOperativa.setLocalita(operativaDto.getLocalita());
    sedeOperativa.setCap(operativaDto.getCap());
    sedeOperativa.setComune(comuneRepository.findById(operativaDto.getIdComune())
            .orElseThrow(() -> new ComuneNotFoundException("Comune sede operativa non trovato")));
    return clienteRepository.save(cliente);

}

public void deleteCliente(int id){
    Cliente cliente = getCliente(id);
    clienteRepository.delete(cliente);
}

    public Cliente setLogoCliente(int id, String imageUrl) {
        Cliente cliente = getCliente(id);
        cliente.setLogoAziendale(imageUrl);
        return clienteRepository.save(cliente);
    }

    public Page<Cliente> getClientiByNome(String nome, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return clienteRepository.findByRagioneSocialeContainingIgnoreCase(nome, pageable);
    }

    public Page<Cliente> getClientiByFatturatoAnnuale(BigDecimal minFatt,BigDecimal MaxFatt, int page, int size, String sortBy){
    Pageable pageable = PageRequest.of(page,size,Sort.by(sortBy));
    return clienteRepository.findByFatturatoBetween(minFatt,MaxFatt,pageable);

    }
    public Page<Cliente> getClientiByDataInserimento(LocalDate dataMin, LocalDate dataMax, int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(sortBy));
        return clienteRepository.findByDataInserimentoBetween(dataMin,dataMax,pageable);
    }

    public Page<Cliente> getClientiByUltimoContatto(LocalDate dataMin, LocalDate dataMax, int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(sortBy));
        return clienteRepository.findByDataUltimoContattoBetween(dataMin,dataMax,pageable);

    }
    public Page<Cliente> getClientiBySedeLegale(String provincia,int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(sortBy));
        return clienteRepository.findByProvinciaSedeLegale(provincia,pageable);
    }

    public Map<Month, Long> getClientiPerMeseJava(LocalDate dataMin, LocalDate dataMax) {
        List<Cliente> clienti = clienteRepository.findByDataInserimentoBetween(dataMin,dataMax);

        // Raggruppa per mese e conta quanti clienti in ogni mese
        return clienti.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getDataInserimento().getMonth(),
                        TreeMap::new,
                        Collectors.counting()
                ));
    }
}
