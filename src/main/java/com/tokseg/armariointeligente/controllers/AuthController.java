package com.tokseg.armariointeligente.controllers;

import com.tokseg.armariointeligente.dtos.LoginDTO;
import com.tokseg.armariointeligente.models.usuario.Usuario;
import com.tokseg.armariointeligente.security.JwtUtil;
import com.tokseg.armariointeligente.services.UsuarioService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        Usuario novo = usuarioService.cadastrar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Usuario usuario = usuarioService.buscarPorEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        if (!new BCryptPasswordEncoder().matches(loginDTO.getSenha(), usuario.getSenha())) {
            throw new BadCredentialsException("Senha inválida.");
        }

        String token = jwtUtil.gerarToken(usuario);
        return ResponseEntity.ok(Map.of("token", token));
    }
}

