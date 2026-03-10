package br.gov.sp.cps.springtopicos20261.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.cps.springtopicos20261.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Query("SELECT u FROM Usuario u WHERE u.nome = :nome")
    public Optional<Usuario> buscarPorNome(String nome);

    public Optional<Usuario> findByNome(String nome);

    @Query("SELECT u FROM Usuario u WHERE lower(u.nome) LIKE lower('%:nome%')")
    public List<Usuario> buscarPorNomeQueContem(String nome);
    
    public List<Usuario> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT u FROM Usuario u WHERE u.nome = :nome AND u.senha = :senha")
    public Optional<Usuario> findByNomeAndSenha(String nome, String senha);

    @Query("SELECT u FROM Usuario u JOIN u.autorizacoes a WHERE a.nome = :nomeAutorizacao")
    public List<Usuario> findByAutorizacoesNome(String nomeAutorizacao);
    
}
