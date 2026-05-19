package br.gov.sp.cps.springtopicos20261.controller;

import java.net.URI;
import java.time.LocalDateTime;
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

import br.gov.sp.cps.springtopicos20261.entity.Correcao;
import br.gov.sp.cps.springtopicos20261.service.CorrecaoService;

@RestController
@CrossOrigin
@RequestMapping("/correcao")
public class CorrecaoController {

    private CorrecaoService service;

    public CorrecaoController(CorrecaoService service) {
        this.service = service;
    }

    @GetMapping("/buscar")
    public List<Correcao> buscarCorrecaoPorAnotacaoEDataHora(@RequestParam("anotacao") String textoAnotacao, @RequestParam("dataHora") LocalDateTime dataHora) {
        return service.buscarCorrecaoPorAnotacaoEDataHora(textoAnotacao, dataHora);
    }

    @GetMapping
    public List<Correcao> buscarTodos() {
        return service.buscarTodos();
    }

    @GetMapping("/{id}")
    public Correcao buscarCorrecaoPorId(@PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Correcao> cadastrar(@RequestBody Correcao correcao) {
        Correcao criado = service.cadastrar(correcao);
        return ResponseEntity.created(URI.create("/correcao/" + criado.getId())).body(criado);
    }

}
