package com.tokseg.armariointeligente.dtos;

import com.tokseg.armariointeligente.models.retirada.MetodoAutenticacao;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RetiradaResponseDTO {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntregaId() {
        return entregaId;
    }

    public void setEntregaId(Long entregaId) {
        this.entregaId = entregaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public LocalDateTime getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDateTime dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public MetodoAutenticacao getMetodoAutenticacao() {
        return metodoAutenticacao;
    }

    public void setMetodoAutenticacao(MetodoAutenticacao metodoAutenticacao) {
        this.metodoAutenticacao = metodoAutenticacao;
    }

    private Long id;
    private Long entregaId;
    private Long usuarioId;
    private String usuarioNome;
    private LocalDateTime dataRetirada;
    private MetodoAutenticacao metodoAutenticacao;


}
