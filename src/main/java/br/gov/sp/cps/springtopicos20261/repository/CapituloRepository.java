package br.gov.sp.cps.springtopicos20261.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.cps.springtopicos20261.entity.Capitulo;

public interface CapituloRepository extends JpaRepository<Capitulo, Long> {

    @Query("select c from Capitulo c join c.trabalho t " + 
        "where c.dataEntrega is null and c.dataCriacao < :dataCricao " + 
        "and lower(t.titulo) like lower(concat('%', :tituloTrabalho, '%'))")
    public List<Capitulo> buscarCapitulosAtrasados(String tituloTrabalho, LocalDate dataCricao);
    
}
