package br.gov.sp.cps.springtopicos20261.controller;

import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.gov.sp.cps.springtopicos20261.security.JwtUtils;
import br.gov.sp.cps.springtopicos20261.security.Login;

@RestController
@CrossOrigin
@RequestMapping(value = "/login")
public class LoginController {
    private AuthenticationManager authManager;
    private JwtUtils jwtUtils;

    public LoginController(AuthenticationManager authManager, JwtUtils jwtUtils) {
        this.authManager = authManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    public Login autenticar(@RequestBody Login login) throws JsonProcessingException {
        Authentication auth = new UsernamePasswordAuthenticationToken(login.getUsername(),
                login.getPassword());
        auth = authManager.authenticate(auth);
        login.setToken(jwtUtils.generateToken(auth));
        login.setAuth(auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList())
                        .toArray(new String[auth.getAuthorities().size()]));
        return login;
    }

    @GetMapping
    public String message() {
        return "Login page. Use POST.";
    }
}
