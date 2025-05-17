package com.tokseg.armariointeligente.dtos;

import com.tokseg.armariointeligente.models.usuario.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
    @NotBlank String nome,
    @NotBlank @Email String email,
    String telefone,
    @NotBlank String senha,
    @NotNull TipoUsuario tipoUsuario
) {}
