package br.gov.sp.cps.springtopicos20261.service;

import java.time.LocalDateTime;
import java.util.List;

import br.gov.sp.cps.springtopicos20261.entity.Correcao;

public interface CorrecaoService {

    public List<Correcao> buscarCorrecaoPorAnotacaoEDataHora(String textoAnotacao, LocalDateTime dataHora);

    public List<Correcao> buscarTodos();

    public Correcao buscarPorId(Long id);

    public Correcao cadastrar(Correcao correcao);
    
}
