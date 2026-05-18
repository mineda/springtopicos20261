package br.gov.sp.cps.springtopicos20261.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.cps.springtopicos20261.entity.Capitulo;
import br.gov.sp.cps.springtopicos20261.repository.CapituloRepository;

@Service
public class CapituloServiceImpl implements CapituloService {

    private CapituloRepository repo;

    private TrabalhoService trabalhoService;

    public CapituloServiceImpl(CapituloRepository repo, TrabalhoService trabalhoService) {
        this.repo = repo;
        this.trabalhoService = trabalhoService;
    }

    @Override
    public List<Capitulo> buscarTodos() {
        return repo.findAll();
    }

    @Override
    public Capitulo buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Capítulo não encontrado")
        );
    }

    @Override
    public Capitulo cadastrar(Capitulo capitulo) {
        if(capitulo == null ||
            capitulo.getConteudo() == null ||
            capitulo.getTrabalho() == null || 
            capitulo.getTrabalho().getId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conteúdo inválido");
        }
        capitulo.setTrabalho(trabalhoService.buscarPorId(capitulo.getTrabalho().getId()));
        if(capitulo.getDataCriacao() == null) {
            capitulo.setDataCriacao(LocalDate.now());
        }
        return repo.save(capitulo);
    }

    @Override
    public List<Capitulo> buscarCapitulosAtrasados(String tituloTrabalho, LocalDate dataCriacao) {
        if(tituloTrabalho == null || dataCriacao == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Título do trabalho e data de criação são obrigatórios");
        }
        return repo.buscarCapitulosAtrasados(tituloTrabalho, dataCriacao);
    }
    
}
