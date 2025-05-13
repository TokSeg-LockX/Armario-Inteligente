package com.tokseg.armariointeligente.dtos;

import com.tokseg.armariointeligente.models.usuario.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class RegisterDTO {
    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String telefone;

    @NotBlank
    private String senha;

    @NotNull
    private TipoUsuario tipo;
}

