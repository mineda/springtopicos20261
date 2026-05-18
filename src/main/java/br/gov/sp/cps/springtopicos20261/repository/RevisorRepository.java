package br.gov.sp.cps.springtopicos20261.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.cps.springtopicos20261.entity.Revisor;

public interface RevisorRepository extends JpaRepository<Revisor, Long>{

    public List<Revisor> findByUsuarioNomeContainingIgnoreCaseAndRevisaoFeedbackContainingIgnoreCase(String nomeUsuario, String feedback);
    
}
