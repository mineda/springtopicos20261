package br.gov.sp.cps.springtopicos20261.service;

import java.time.LocalDate;
import java.util.List;

import br.gov.sp.cps.springtopicos20261.entity.Capitulo;

public interface CapituloService {

    public List<Capitulo> buscarTodos();

    public Capitulo buscarPorId(Long id);

    public Capitulo cadastrar(Capitulo capitulo);

    public List<Capitulo> buscarCapitulosAtrasados(String tituloTrabalho, LocalDate dataCriacao);
    
}
