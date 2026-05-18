package br.gov.sp.cps.springtopicos20261.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.cps.springtopicos20261.entity.Revisor;
import br.gov.sp.cps.springtopicos20261.repository.RevisorRepository;

@Service
public class RevisorServiceImpl implements RevisorService {

    private RevisorRepository repo;

    private RevisaoService revisaoService;

    private UsuarioService usuarioService;

    public RevisorServiceImpl(RevisorRepository repo, RevisaoService revisaoService, UsuarioService usuarioService) {
        this.repo = repo;
        this.revisaoService = revisaoService;
        this.usuarioService = usuarioService;
    }

    @Override
    public Revisor cadastrar(Revisor revisor) {
        if(revisor.getUsuario() == null 
                || revisor.getUsuario().getId() == null 
                || revisor.getRevisao() == null 
                || revisor.getRevisao().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário e revisão são obrigatórios para cadastrar um revisor.");
        }
        revisor.setUsuario(usuarioService.buscarPorId(revisor.getUsuario().getId()));
        revisor.setRevisao(revisaoService.buscarPorId(revisor.getRevisao().getId()));
        return repo.save(revisor);
    }

    @Override
    public Revisor buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Revisor não encontrado")
        );
    }

    @Override
    public List<Revisor> buscarTodos() {
        return repo.findAll();
    }

    @Override
    public List<Revisor> buscarPorUsuarioEFeedback(String nomeUsuario, String feedback) {
        if(nomeUsuario == null || feedback == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do usuário e feedback são obrigatórios para a busca.");
        }
        return repo.findByUsuarioNomeContainingIgnoreCaseAndRevisaoFeedbackContainingIgnoreCase(nomeUsuario, feedback);
    }
    
}
