package br.gov.sp.cps.springtopicos20261.service;

import java.util.List;

import br.gov.sp.cps.springtopicos20261.entity.Anotacao;

public interface AnotacaoService {

    public Anotacao cadastrar(Anotacao anotacao);

    public Anotacao buscarPorId(Long id);

    public List<Anotacao> buscarPorTextoEAutor(String texto, String nomeUsuario);
    
    public List<Anotacao> buscarTodas();
}
