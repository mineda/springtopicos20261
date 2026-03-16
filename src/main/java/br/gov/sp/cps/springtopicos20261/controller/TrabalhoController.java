package br.gov.sp.cps.springtopicos20261.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.cps.springtopicos20261.entity.Trabalho;
import br.gov.sp.cps.springtopicos20261.service.TrabalhoService;

@RestController
@CrossOrigin
@RequestMapping("/trabalho")
public class TrabalhoController {

    private TrabalhoService service;

    public TrabalhoController(TrabalhoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Trabalho>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Trabalho> cadastrar(@RequestBody Trabalho trabalho) {
        Trabalho trabalhoCadastrado = service.cadastrar(trabalho);
        return ResponseEntity.created(URI.create("/trabalho/" + trabalhoCadastrado.getId())).body(trabalhoCadastrado);
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Trabalho>> buscarPorTituloEAutor(@RequestParam("titulo")String titulo, @RequestParam("autor") String nomeAutor) {
        return ResponseEntity.ok(service.buscarPorTituloEAutor(titulo, nomeAutor));
    }
    
}
