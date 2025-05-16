package com.tokseg.armariointeligente.dtos;

import com.tokseg.armariointeligente.models.entrega.StatusEntrega;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EntregaResponseDTO {
    private Long id;
    private Long compartimentoId;
    private String entregador;
    private LocalDateTime dataEntrega;
    private Long destinatarioId;
    private String destinatarioNome;
    private String codigoAcesso;
    private StatusEntrega status;
    private String descricao;
}
