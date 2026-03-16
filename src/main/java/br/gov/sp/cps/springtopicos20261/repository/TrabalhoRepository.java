package br.gov.sp.cps.springtopicos20261.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.cps.springtopicos20261.entity.Trabalho;

public interface TrabalhoRepository extends JpaRepository<Trabalho, Long>{

    public List<Trabalho> findByTituloContainingIgnoreCaseAndAutorNomeContainingIgnoreCase(String titulo, String nomeAutor);
    
}
