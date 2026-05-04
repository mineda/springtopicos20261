package br.gov.sp.cps.springtopicos20261.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.cps.springtopicos20261.entity.Autorizacao;
import br.gov.sp.cps.springtopicos20261.entity.Usuario;
import br.gov.sp.cps.springtopicos20261.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepo;

    private AutorizacaoService autorizacaoService;

    private PasswordEncoder encoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepo, AutorizacaoService autorizacaoService, PasswordEncoder encoder) {
        this.usuarioRepo = usuarioRepo;
        this.autorizacaoService = autorizacaoService;
        this.encoder = encoder;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Usuario cadastrar(Usuario usuario) {
        if(usuario == null 
                || usuario.getId() != null
                || usuario.getNome() == null 
                || usuario.getNome().isBlank()
                || usuario.getSenha() == null
                || usuario.getSenha().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados de usuário inválidos!");
        }
        if(usuario.getAutorizacoes() != null) {
            Set<Autorizacao> autorizacoes = new HashSet<Autorizacao>();
            for(Autorizacao aut : usuario.getAutorizacoes()) {
                if(aut.getId() == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados de autorização inválidos!");
                }
                autorizacoes.add(autorizacaoService.buscarPorId(aut.getId()));
            }
            usuario.setAutorizacoes(autorizacoes);
        }
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return usuarioRepo.save(usuario);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        Optional<Usuario> usuarioOp = usuarioRepo.findById(id);
        if(usuarioOp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe usuário com esse id!");
        }
        return usuarioOp.get();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public List<Usuario> buscarPorNome(String nome) {
        if(nome == null || nome.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome de usuário é obrigatório!");
        }
        return usuarioRepo.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return usuarioRepo.findAll();
    }
    
}
