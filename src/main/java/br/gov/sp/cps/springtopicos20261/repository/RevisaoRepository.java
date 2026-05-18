package br.gov.sp.cps.springtopicos20261.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.cps.springtopicos20261.entity.Revisao;

public interface RevisaoRepository extends JpaRepository<Revisao, Long> {

    @Query("select r from Revisao r join r.capitulo c join c.trabalho t " + 
        "where lower(t.titulo) like lower(concat('%', :tituloTrabalho, '%')) " + 
        "and r.dataRevisao < :dataRevisao")
    public List<Revisao> buscaPorTrabalhoEData(String tituloTrabalho, LocalDate dataRevisao);
    
}
