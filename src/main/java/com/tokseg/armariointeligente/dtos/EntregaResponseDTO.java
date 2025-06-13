package com.tokseg.armariointeligente.dtos;

import com.tokseg.armariointeligente.models.entrega.StatusEntrega;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntregaResponseDTO {
    private Long id;
    private Long compartimentoId; // ID do compartimento
    // private String compartimentoCodigo; // Poderia adicionar o código do compartimento
    private String entregador; // Nome do entregador
    private LocalDateTime dataEntrega; // Data/hora do depósito confirmado
    private Long destinatarioId;
    private String destinatarioNome;
    // Remover codigoAcesso se o PIN do destinatário o substitui completamente
    // private String codigoAcesso;
    private StatusEntrega status;
    private String descricao;
    // NUNCA inclua os PINs aqui!
}