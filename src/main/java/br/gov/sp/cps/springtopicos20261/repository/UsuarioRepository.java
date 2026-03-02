package br.gov.sp.cps.springtopicos20261.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.cps.springtopicos20261.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
