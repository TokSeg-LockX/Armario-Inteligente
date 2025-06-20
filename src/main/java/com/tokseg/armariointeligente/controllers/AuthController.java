package com.tokseg.armariointeligente.controllers;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tokseg.armariointeligente.dtos.LoginDTO;
import com.tokseg.armariointeligente.dtos.RegisterDTO;
import com.tokseg.armariointeligente.dtos.UsuarioRequestDTO;
import com.tokseg.armariointeligente.dtos.UsuarioResponseDTO;
import com.tokseg.armariointeligente.exception.BadRequestException;
import com.tokseg.armariointeligente.models.usuario.Usuario;
import com.tokseg.armariointeligente.security.JwtUtil;
import com.tokseg.armariointeligente.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid RegisterDTO dto) {
        // Converter RegisterDTO para UsuarioRequestDTO
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO(dto.nome(), dto.email(),
                dto.telefone(), dto.senha(), dto.tipo());

        Usuario salvo = usuarioService.cadastrar(requestDTO);
        UsuarioResponseDTO response = usuarioService.toResponseDTO(salvo);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(loginDTO.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "Usuário não encontrado com o email informado."));

            if (!usuario.getAtivo()) {
                throw new BadCredentialsException("Usuário inativo. Contate o administrador.");
            }

            if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
                throw new BadCredentialsException("Senha inválida. Verifique suas credenciais.");
            }
            String token = jwtUtil.gerarToken(usuario);
            UsuarioResponseDTO usuarioResponse = usuarioService.toResponseDTO(usuario);
            return ResponseEntity.ok(Map.of("token", token, "usuario", usuarioResponse));
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            // Transformando em BadRequestException para ser tratada pelo handler específico
            throw new BadRequestException("Credenciais inválidas: " + e.getMessage());
        }
    }
}

