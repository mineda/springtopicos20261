package br.gov.sp.cps.springtopicos20261.controller;

import java.net.URI;
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

import br.gov.sp.cps.springtopicos20261.entity.Revisor;
import br.gov.sp.cps.springtopicos20261.service.RevisorService;

@RestController
@CrossOrigin
@RequestMapping("/revisor")
public class RevisorController {

    private final RevisorService service;

    public RevisorController(RevisorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Revisor> buscarTodos() {
        return service.buscarTodos();
    }

    @GetMapping("/{id}")
    public Revisor buscarPorId(@PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/busca")
    public List<Revisor> buscarPorUsuarioEFeedback(@RequestParam("usuario") String nomeUsuario, @RequestParam("feedback") String feedback) {
        return service.buscarPorUsuarioEFeedback(nomeUsuario, feedback);
    }

    @PostMapping
    public ResponseEntity<Revisor> cadastrar(@RequestBody Revisor revisor) {
        Revisor novoRevisor = service.cadastrar(revisor);
        return ResponseEntity.created(URI.create("/revisor/" + novoRevisor.getId())).body(novoRevisor);
    }
    
}
