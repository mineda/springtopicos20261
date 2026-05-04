package br.gov.sp.cps.springtopicos20261.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.cps.springtopicos20261.entity.Anotacao;

public interface AnotacaoRepository extends JpaRepository<Anotacao, Long> {

    public List<Anotacao> findByTextoContainingIgnoreCaseAndAutorNome(String texto, String nomeUsuario);
    
}
