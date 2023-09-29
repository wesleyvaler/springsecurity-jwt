package com.github.wesleyvaler.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.github.wesleyvaler.domain.entity.Usuario;
import com.github.wesleyvaler.exception.SenhaInvalidaException;
import com.github.wesleyvaler.rest.dto.CredenciaisDTO;
import com.github.wesleyvaler.rest.dto.TokenDTO;
import com.github.wesleyvaler.security.jwt.JwtService;
import com.github.wesleyvaler.service.impl.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	 private final UsuarioServiceImpl usuarioService;

	    private final PasswordEncoder passwordEncoder;
	    private final JwtService jwtService;

	    @PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    public Usuario salvar(@RequestBody @Valid Usuario usuario) {
	        String senhaCriptografada = passworEncoder.encode(Usuario.getSenha());
	        usuario.setSenha(senhaCriptografada);
	        return usuarioService.salvar(usuario);
	    }


	    @PostMapping("/auth")
	    public tokenDTO autenticat(@RequestBody CredenciaisDTO credenciais) {
	        try {
	            Usuario usuario = Usuario.builder()
	                    .login(credenciais.getLogin())
	                    .senha(credenciais.getSenha()).build();
	            UserDetails usuarioAutencicado = usuarioService.autenticar(usuario);
	            String toke =  jwtService.gerarToke(usuario);
	            return new TokenDTO(usuario.getLogin(), token);
	        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
	            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
	        }
	    }

	
}

