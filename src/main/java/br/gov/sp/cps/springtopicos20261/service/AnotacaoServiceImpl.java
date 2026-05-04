package br.gov.sp.cps.springtopicos20261.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.cps.springtopicos20261.entity.Anotacao;
import br.gov.sp.cps.springtopicos20261.repository.AnotacaoRepository;

@Service
public class AnotacaoServiceImpl implements AnotacaoService {

    private AnotacaoRepository repo;

    private UsuarioService usuarioService;

    public AnotacaoServiceImpl(AnotacaoRepository repo, UsuarioService usuarioService) {
        this.repo = repo;
        this.usuarioService = usuarioService;
    }

    @Override
    public Anotacao cadastrar(Anotacao anotacao) {
        if(anotacao.getId() != null ||
                anotacao == null || 
                anotacao.getTexto() == null || 
                anotacao.getTexto().isBlank() || 
                anotacao.getAutor() == null || 
                anotacao.getAutor().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id deve ser nulo! Texto e autor são obrigatórios!");
        }
        if(anotacao.getDataHora() == null) {
            anotacao.setDataHora(LocalDateTime.now());
        }
        // Verificar se o usuário existe
        anotacao.setAutor(usuarioService.buscarPorId(anotacao.getAutor().getId()));
        return repo.save(anotacao);    
    }

    @Override
    public Anotacao buscarPorId(Long id) {
        if(id==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID é obrigatório!");
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anotação não encontrada!"));
    }

    @Override
    public List<Anotacao> buscarPorTextoEAutor(String texto, String nomeUsuario) {
        if(texto == null || texto.isBlank() || nomeUsuario == null || nomeUsuario.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Texto e nome do usuário são obrigatórios!");
        } 
        return repo.findByTextoContainingIgnoreCaseAndAutorNome(texto, nomeUsuario);
    }

    @Override
    public List<Anotacao> buscarTodas() {
        return repo.findAll();
    }
    
}
