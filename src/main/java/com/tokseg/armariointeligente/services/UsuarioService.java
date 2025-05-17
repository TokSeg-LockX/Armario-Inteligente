package com.tokseg.armariointeligente.services;

import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tokseg.armariointeligente.dtos.UsuarioRequestDTO;
import com.tokseg.armariointeligente.dtos.UsuarioResponseDTO;
import com.tokseg.armariointeligente.exception.ResourceAlreadyExistsException;
import com.tokseg.armariointeligente.exception.ResourceNotFoundException;
import com.tokseg.armariointeligente.models.usuario.Usuario;
import com.tokseg.armariointeligente.repositories.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario cadastrar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new ResourceAlreadyExistsException("Usuário", "email", dto.email());
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setTipo(dto.tipoUsuario());
        usuario.setAtivo(true);

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", id));
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario atualizar(Long id, Usuario atualizado) {
        Usuario usuario = buscarPorId(id);
        usuario.setNome(atualizado.getNome());
        usuario.setTelefone(atualizado.getTelefone());
        usuario.setTipo(atualizado.getTipo());
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = buscarPorId(id);
        usuario.setNome(dto.nome());
        usuario.setTelefone(dto.telefone());
        usuario.setTipo(dto.tipoUsuario());

        if (dto.senha() != null && !dto.senha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(dto.senha()));
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario desativar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(false);
        return usuarioRepository.save(usuario);
    }

    public Usuario ativar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setAtivo(true);
        return usuarioRepository.save(usuario);
    }

    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(usuario.getId());
        responseDTO.setNome(usuario.getNome());
        responseDTO.setEmail(usuario.getEmail());
        responseDTO.setTelefone(usuario.getTelefone());
        responseDTO.setTipoUsuario(usuario.getTipo());
        responseDTO.setAtivo(usuario.getAtivo());
        responseDTO.setDataCadastro(usuario.getDataCadastro());
        return responseDTO;
    }

}
