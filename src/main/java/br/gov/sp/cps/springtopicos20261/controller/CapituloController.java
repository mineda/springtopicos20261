package br.gov.sp.cps.springtopicos20261.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.cps.springtopicos20261.entity.Capitulo;
import br.gov.sp.cps.springtopicos20261.service.CapituloService;

@RestController
@CrossOrigin
@RequestMapping("/capitulo")
public class CapituloController {

    private CapituloService service;

    public CapituloController(CapituloService service) {
        this.service = service;
    }

    @GetMapping
    public List<Capitulo> buscarTodos() {
        return service.buscarTodos();
    }

    @PostMapping
    public ResponseEntity<Capitulo> cadastrar(@RequestBody Capitulo capitulo) {
        Capitulo novoCapitulo = service.cadastrar(capitulo);
        return ResponseEntity.created(URI.create("/capitulo/" + novoCapitulo.getId())).body(novoCapitulo);
    }

    @GetMapping("/atrasados")
    public List<Capitulo> buscarCapitulosAtrasados(@RequestParam("trabalho") String tituloTrabalho, @RequestParam("data") LocalDate dataCriacao) {
        return service.buscarCapitulosAtrasados(tituloTrabalho, dataCriacao);
    }

    @GetMapping("/{id}")
    public Capitulo buscarPorId(@PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }

    
}
