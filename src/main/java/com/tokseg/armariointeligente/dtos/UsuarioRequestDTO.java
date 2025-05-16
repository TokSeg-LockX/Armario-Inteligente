package com.tokseg.armariointeligente.dtos;

import com.tokseg.armariointeligente.models.usuario.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioRequestDTO {
    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    private String telefone;

    @NotBlank
    private String senha;

    @NotNull
    private TipoUsuario tipoUsuario;
}
