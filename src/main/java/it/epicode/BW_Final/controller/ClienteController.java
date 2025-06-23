package it.epicode.BW_Final.controller;


import it.epicode.BW_Final.dto.ClienteDto;
import it.epicode.BW_Final.model.Cliente;
import it.epicode.BW_Final.service.ClienteService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class ClienteController {


    @Autowired
    private ClienteService clienteService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/clienti")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveCliente(@RequestBody @Validated ClienteDto clienteDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("",(e, c)->e+c));
        }

        return clienteService.saveCliente(clienteDto);

    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/clienti")
    public ResponseEntity<?> getAllClienti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy) {
        return ResponseEntity.ok(clienteService.getClienti(page, size, sortBy));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/clienti/nome")
    public ResponseEntity<?> getClientiByNome(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy) {
        return ResponseEntity.ok(clienteService.getClientiByNome(nome, page, size, sortBy));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/clienti/fattura")
    public ResponseEntity<?> getClientiByFatturato(
            @RequestParam BigDecimal minFatt,
            @RequestParam BigDecimal maxFatt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy) {
        return ResponseEntity.ok(clienteService.getClientiByFatturatoAnnuale(minFatt, maxFatt, page, size, sortBy));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/clienti/inserimento")
    public ResponseEntity<?> getClientiByInserimento(
            @RequestParam LocalDate minData,
            @RequestParam LocalDate maxData,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy) {
        return ResponseEntity.ok(clienteService.getClientiByDataInserimento(minData, maxData, page, size, sortBy));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/clienti/contatto")
    public ResponseEntity<?> getClientiByUltimoContatto(
            @RequestParam LocalDate minData,
            @RequestParam LocalDate maxData,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy) {
        return ResponseEntity.ok(clienteService.getClientiByUltimoContatto(minData, maxData, page, size, sortBy));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/clienti/sede")
    public ResponseEntity<?> getClientiBySedeLegale(
            @RequestParam String provincia,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ragioneSociale") String sortBy) {
        return ResponseEntity.ok(clienteService.getClientiBySedeLegale(provincia, page, size, sortBy));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/clienti/{id}")
    public Cliente getCliente(@PathVariable int id){return clienteService.getCliente(id);}

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/clienti/{id}")
    public Cliente updateCliente(@PathVariable int id,@RequestBody ClienteDto clienteDto){
        return clienteService.updateDipendente(id,clienteDto);


    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/clienti/{id}")
    public void deleteCliente(@PathVariable int id){
        clienteService.deleteCliente(id);
    }


}


