package com.tokseg.armariointeligente.dtos;

import com.tokseg.armariointeligente.models.usuario.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record RegisterDTO (
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Email é obrigatório")
        String email,
        @NotBlank(message = "Telefone é obrigatório")
        String telefone,
        @NotBlank(message = "Senha é obrigatória")
        String senha,
        @NotNull(message = "Tipo de usuário é obrigatório")
        TipoUsuario tipo
){
}

