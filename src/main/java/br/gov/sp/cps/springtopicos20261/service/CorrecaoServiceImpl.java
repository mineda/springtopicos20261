package br.gov.sp.cps.springtopicos20261.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.cps.springtopicos20261.entity.Correcao;
import br.gov.sp.cps.springtopicos20261.repository.CorrecaoRepository;

@Service
public class CorrecaoServiceImpl implements CorrecaoService {

    private CorrecaoRepository repo;
    
    private AnotacaoService anotacaoService;

    public CorrecaoServiceImpl(CorrecaoRepository repo, AnotacaoService anotacaoService) {
        this.repo = repo;
        this.anotacaoService = anotacaoService;
    }

    @Override
    public List<Correcao> buscarCorrecaoPorAnotacaoEDataHora(String textoAnotacao, LocalDateTime dataHora) {
        if(textoAnotacao == null || textoAnotacao.isBlank() || dataHora == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos.");
        }
        return repo.findByAnotacaoTextoContainingIgnoreCaseAndDataHoraLessThan(textoAnotacao, dataHora);
    }

    @Override
    public List<Correcao> buscarTodos() {
        return repo.findAll();
    }

    @Override
    public Correcao buscarPorId(Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Correção não encontrada com id: " + id)
        );
    }

    @Override
    public Correcao cadastrar(Correcao correcao) {

        if(correcao == null || 
                correcao.getAnotacao() == null || 
                correcao.getAnotacao().getId() == null ||
                correcao.getTextoAntigo() == null || 
                correcao.getTextoAntigo().isBlank() ||
                correcao.getTextoNovo() == null ||
                correcao.getTextoNovo().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos obrigatórios não informados.");
        }
        if(correcao.getDataHora() == null) {
            correcao.setDataHora(LocalDateTime.now());
        }
        // Verificar se a anotação existe
        correcao.setAnotacao(anotacaoService.buscarPorId(correcao.getAnotacao().getId()));
        return repo.save(correcao);
    }
    
}
