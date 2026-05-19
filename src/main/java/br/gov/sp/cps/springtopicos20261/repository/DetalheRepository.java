package br.gov.sp.cps.springtopicos20261.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.cps.springtopicos20261.entity.Detalhe;

public interface DetalheRepository extends JpaRepository<Detalhe, Long> {

    public List<Detalhe> findByAnotacaoTextoContainingIgnoreCaseAndAlcanceGreaterThanEqual(String textoAnotacao, Integer alcance);
    
}