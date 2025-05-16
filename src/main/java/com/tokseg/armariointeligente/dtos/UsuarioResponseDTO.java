package com.tokseg.armariointeligente.dtos;

import com.tokseg.armariointeligente.models.usuario.TipoUsuario;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private TipoUsuario tipoUsuario;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
}

