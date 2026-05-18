package br.gov.sp.cps.springtopicos20261.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.cps.springtopicos20261.entity.Revisao;
import br.gov.sp.cps.springtopicos20261.repository.RevisaoRepository;

@Service
public class RevisaoServiceImpl implements RevisaoService {

    private final RevisaoRepository repo;

    private final CapituloService capituloService;

    public RevisaoServiceImpl(RevisaoRepository repo, CapituloService capituloService) {
        this.repo = repo;
        this.capituloService = capituloService;
    }

    @Override
    public List<Revisao> buscarPorTrabalhoEData(String tituloTrabalho, LocalDate dataRevisao) {
        if (tituloTrabalho == null || dataRevisao == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Título do trabalho e data de revisão são obrigatórios");
        }
        return repo.buscaPorTrabalhoEData(tituloTrabalho, dataRevisao);
    }

    @Override
    public List<Revisao> buscarTodas() {
        return repo.findAll();
    }

    @Override
    public Revisao buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Revisão não encontrada")
        );
    }

    @Override
    public Revisao cadastrar(Revisao revisao) {
        if (revisao == null || 
                revisao.getFeedback() == null ||
                revisao.getCapitulo() == null || 
                revisao.getCapitulo().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos!");
        }
        revisao.setCapitulo(capituloService.buscarPorId(revisao.getCapitulo().getId()));
        if(revisao.getDataRevisao() == null) {
            revisao.setDataRevisao(LocalDate.now());
        }
        return repo.save(revisao); 
    }
    
}
