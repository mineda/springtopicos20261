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

import br.gov.sp.cps.springtopicos20261.entity.Detalhe;
import br.gov.sp.cps.springtopicos20261.service.DetalheService;

@RestController
@CrossOrigin
@RequestMapping("/detalhe")
public class DetalheController {

    private DetalheService service;

    public DetalheController(DetalheService service) {
        this.service = service;
    }
    
    @GetMapping("/buscar")
    public List<Detalhe> buscarDetalhesPorAnotacaoEAlcance(@RequestParam("anotacao") String textoAnotacao, @RequestParam("alcance") Integer alcance) {
        return service.buscarDetalhesPorAnotacaoEAlcance(textoAnotacao, alcance);
    }

    @GetMapping
    public List<Detalhe> buscarTodos() {
        return service.buscarTodos();
    }

    @GetMapping("/{id}")
    public Detalhe buscarDetalhePorId(@PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Detalhe> cadastrar(@RequestBody Detalhe detalhe) {
        Detalhe criado = service.cadastrar(detalhe);
        return ResponseEntity.created(URI.create("/detalhe/" + criado.getId())).body(criado);
    }

}
