package com.tokseg.armariointeligente.services;

import com.tokseg.armariointeligente.models.compartimento.Compartimento;
import com.tokseg.armariointeligente.models.compartimento.StatusCompartimento;
import com.tokseg.armariointeligente.models.entrega.Entrega;
import com.tokseg.armariointeligente.models.entrega.StatusEntrega;
import com.tokseg.armariointeligente.repositories.compartimento.CompartimentoRepository;
import com.tokseg.armariointeligente.repositories.entrega.EntregaRepository;
import org.springframework.context.annotation.Lazy;
// import lombok.RequiredArgsConstructor; // <<< REMOVA ESTA LINHA
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
// import java.util.Optional; // Removido se não usado diretamente aqui

@Service
// @RequiredArgsConstructor // <<< REMOVA ESTA ANOTAÇÃO
public class CompartimentoService {

    private final CompartimentoRepository compartimentoRepository;
    private final EntregaRepository entregaRepository;
    private final EntregaService entregaService; // Dependência que pode precisar ser @Lazy

    // Mantenha apenas este construtor explícito
    public CompartimentoService(CompartimentoRepository compartimentoRepository,
                                EntregaRepository entregaRepository,
                                @Lazy EntregaService entregaService) { // @Lazy AQUI se esta for a dependência a ser carregada tardiamente
        this.compartimentoRepository = compartimentoRepository;
        this.entregaRepository = entregaRepository;
        this.entregaService = entregaService;
    }

    public List<Compartimento> listarPorArmario(Long armarioId) {
        return this.compartimentoRepository.findByArmario_Id(armarioId);
    }

    @Transactional
    public Compartimento atualizarStatus(Long id, StatusCompartimento status) {
        Compartimento c = buscarPorId(id);
        c.setStatus(status);
        return this.compartimentoRepository.save(c);
    }

    public Compartimento buscarPorId(Long id) {
        return this.compartimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compartimento não encontrado. ID: " + id));
    }

    @Transactional
    public String abrirComPin(Long compartimentoId, String pinFornecido, String tipoUsuarioOperacao) {
        Compartimento compartimento = buscarPorId(compartimentoId);

        List<StatusEntrega> statusesParaAbertura = List.of(
                StatusEntrega.AGUARDANDO_DEPOSITO,
                StatusEntrega.DEPOSITO_PENDENTE_CONFIRMACAO,
                StatusEntrega.PENDENTE
        );

        Entrega entrega = this.entregaRepository.findByCompartimento_IdAndStatusIn(compartimentoId, statusesParaAbertura)
                .orElseThrow(() -> new RuntimeException("Nenhuma entrega ativa ou PIN inválido para este compartimento (" + compartimento.getCodigo() + ")."));

        boolean pinCorretoEValido = false;
        String mensagemSucesso = "";

        if ("ENTREGADOR".equalsIgnoreCase(tipoUsuarioOperacao)) {
            if (entrega.getPinEntregador() != null &&
                    entrega.getPinEntregador().equals(pinFornecido) &&
                    entrega.getPinEntregadorValidade() != null &&
                    entrega.getPinEntregadorValidade().isAfter(LocalDateTime.now())) {
                pinCorretoEValido = true;
                mensagemSucesso = "Compartimento " + compartimento.getCodigo() + " aberto para depósito pelo entregador.";
            } else {
                throw new RuntimeException("PIN do entregador inválido, expirado ou não aplicável ao estado atual da entrega.");
            }
        } else if ("DESTINATARIO".equalsIgnoreCase(tipoUsuarioOperacao)) {
            if (entrega.getStatus() == StatusEntrega.PENDENTE &&
                    entrega.getPinDestinatario() != null &&
                    entrega.getPinDestinatario().equals(pinFornecido) &&
                    entrega.getPinDestinatarioValidade() != null &&
                    entrega.getPinDestinatarioValidade().isAfter(LocalDateTime.now())) {
                pinCorretoEValido = true;
                this.entregaService.marcarComoRetirado(entrega.getId());
                mensagemSucesso = "Compartimento " + compartimento.getCodigo() + " aberto para retirada pelo destinatário. Item retirado.";
            } else {
                throw new RuntimeException("PIN do destinatário inválido, expirado ou não aplicável ao estado atual da entrega.");
            }
        } else {
            throw new IllegalArgumentException("Tipo de usuário ('" + tipoUsuarioOperacao + "') para operação inválido. Use 'ENTREGADOR' ou 'DESTINATARIO'.");
        }

        if (pinCorretoEValido) {
            System.out.println("ACIONANDO HARDWARE (Simulado): Abrindo compartimento " + compartimento.getCodigo());
            return mensagemSucesso;
        }
        throw new RuntimeException("Falha inesperada na validação do PIN.");
    }
}