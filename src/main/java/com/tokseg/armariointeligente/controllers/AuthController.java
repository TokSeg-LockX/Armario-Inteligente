package com.tokseg.armariointeligente.controllers;

import com.tokseg.armariointeligente.dtos.LoginDTO;
import com.tokseg.armariointeligente.dtos.RegisterDTO;
import com.tokseg.armariointeligente.models.usuario.Usuario;
import com.tokseg.armariointeligente.security.JwtUtil;
import com.tokseg.armariointeligente.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid RegisterDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setTipo(dto.tipo());
        usuario.setAtivo(true);

        Usuario salvo = usuarioService.cadastrar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {

        System.out.println("Tentando login com email: " + loginDTO.getEmail());

        Usuario usuario = usuarioService.buscarPorEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        String senha = passwordEncoder.encode(loginDTO.getSenha());
        System.out.println(senha);

        if (!senha.equals(usuario.getSenha())) {
            throw new BadCredentialsException("Senha inválida.");
        }

        String token = jwtUtil.gerarToken(usuario);
        return ResponseEntity.ok(Map.of("token", token));
    }
}

