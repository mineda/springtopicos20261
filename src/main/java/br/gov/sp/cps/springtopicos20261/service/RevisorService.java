package br.gov.sp.cps.springtopicos20261.service;

import java.util.List;

import br.gov.sp.cps.springtopicos20261.entity.Revisor;

public interface RevisorService {

    public List<Revisor> buscarPorUsuarioEFeedback(String nomeUsuario, String feedback);

    public List<Revisor> buscarTodos();

    public Revisor buscarPorId(Long id);

    public Revisor cadastrar(Revisor revisor);
    
}
