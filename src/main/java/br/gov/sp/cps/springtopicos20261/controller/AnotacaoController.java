package br.gov.sp.cps.springtopicos20261.controller;

import java.net.URI;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.cps.springtopicos20261.entity.Anotacao;
import br.gov.sp.cps.springtopicos20261.service.AnotacaoService;

@RestController
@CrossOrigin
@RequestMapping("/anotacao")
public class AnotacaoController {

    private AnotacaoService service;

    public AnotacaoController(AnotacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Anotacao> buscarTodas() {
        return service.buscarTodas();
    }

    @GetMapping("/{id}")
    public Anotacao buscarPorId(@PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/busca")
    public List<Anotacao> buscarPorTextoEAutor(@RequestParam("texto") String texto, @RequestParam("usuario") String nomeUsuario) {
        return service.buscarPorTextoEAutor(texto, nomeUsuario);
    }

    @PostMapping
    public ResponseEntity<Anotacao> cadastrar(@RequestBody Anotacao anotacao) {
        Anotacao novaAnotacao = service.cadastrar(anotacao);
        return ResponseEntity.created(URI.create("/anotacao/" + novaAnotacao.getId())).body(novaAnotacao);
    }
}
