package com.tokseg.armariointeligente.controllers;

import com.tokseg.armariointeligente.dtos.UsuarioRequestDTO;
import com.tokseg.armariointeligente.dtos.UsuarioResponseDTO;
import com.tokseg.armariointeligente.models.usuario.Usuario;
import com.tokseg.armariointeligente.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> usuarios =
                usuarioService.listarTodos().stream().map(this::converterParaDTO).toList();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = usuarioService.cadastrar(dto);
        return ResponseEntity.ok(converterParaDTO(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(converterParaDTO(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id,
            @RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = usuarioService.atualizar(id, dto);
        return ResponseEntity.ok(converterParaDTO(usuario));
    }

    private UsuarioResponseDTO converterParaDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setTipoUsuario(usuario.getTipo());
        dto.setAtivo(usuario.getAtivo());
        dto.setDataCadastro(usuario.getDataCadastro());
        return dto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> desativar(@PathVariable Long id) {
        usuarioService.desativar(id);
        return ResponseEntity.ok(Map.of("message", "Usuário desativado com sucesso"));
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<Map<String, String>> ativar(@PathVariable Long id) {
        usuarioService.ativar(id);
        return ResponseEntity.ok(Map.of("message", "Usuário ativado com sucesso"));
    }
}
