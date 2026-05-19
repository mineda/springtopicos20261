package br.gov.sp.cps.springtopicos20261.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.cps.springtopicos20261.entity.Detalhe;
import br.gov.sp.cps.springtopicos20261.repository.DetalheRepository;

@Service
public class DetalheServiceImpl implements DetalheService {

    private DetalheRepository repo;

    private AnotacaoService anotacaoService;

    public DetalheServiceImpl(DetalheRepository repo, AnotacaoService anotacaoService) {
        this.repo = repo;
        this.anotacaoService = anotacaoService;
    }

    @Override
    public List<Detalhe> buscarDetalhesPorAnotacaoEAlcance(String textoAnotacao, Integer alcance) {
        if(textoAnotacao == null || textoAnotacao.isBlank() ||
                alcance == null || alcance < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parâmetros de busca inválidos: textoAnotacao e alcance são obrigatórios e devem ser válidos.");
        }
        return repo.findByAnotacaoTextoContainingIgnoreCaseAndAlcanceGreaterThanEqual(textoAnotacao, alcance);
    }

    @Override
    public List<Detalhe> buscarTodos() {
        return repo.findAll();
    }

    @Override
    public Detalhe buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Detalhe não encontrado com id: " + id)
        );
    }

    @Override
    public Detalhe cadastrar(Detalhe detalhe) {
        if(detalhe == null || 
                detalhe.getAnotacao() == null || 
                detalhe.getAnotacao().getId() == null ||
                detalhe.getAlcance() == null || 
                detalhe.getAlcance() < 0 ||
                detalhe.getDescricao() == null ||
                detalhe.getDescricao().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos obrigatórios não informados.");
        }
        if(detalhe.getDataHora() == null) {
            detalhe.setDataHora(LocalDateTime.now());
        }
        // Verificar se a anotação existe
        detalhe.setAnotacao(anotacaoService.buscarPorId(detalhe.getAnotacao().getId()));
        return repo.save(detalhe);
    }
    
}
