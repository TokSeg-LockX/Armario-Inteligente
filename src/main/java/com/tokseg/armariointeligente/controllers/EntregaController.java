package com.tokseg.armariointeligente.controllers;

import com.tokseg.armariointeligente.dtos.EntregaResponseDTO; // Importar se não existir
import com.tokseg.armariointeligente.dtos.RegenerarPinRequestDTO;
import com.tokseg.armariointeligente.dtos.SolicitarAberturaDepositoDTO;
import com.tokseg.armariointeligente.models.entrega.Entrega;
import com.tokseg.armariointeligente.models.entrega.StatusEntrega; // Removido se não usado diretamente
import com.tokseg.armariointeligente.services.EntregaService;
import com.tokseg.armariointeligente.services.UsuarioService; // Se precisar para mapear DTO
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper; // Se usar ModelMapper para DTO
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Para controle de acesso
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/entregas")
@RequiredArgsConstructor
public class EntregaController {

    private final EntregaService entregaService;
    // private final UsuarioService usuarioService; // Se precisar buscar info de usuário para DTO
    private final ModelMapper modelMapper; // Injetar ModelMapper se usar para conversão

    // Endpoint para o Entregador solicitar o início de um depósito e receber PIN
    @PostMapping("/solicitar-deposito")
    @PreAuthorize("hasRole('ENTREGADOR')")
    public ResponseEntity<EntregaResponseDTO> solicitarAberturaParaDeposito(
            @Valid @RequestBody SolicitarAberturaDepositoDTO dto) {
        Entrega entrega = entregaService.solicitarAberturaParaDeposito(
                dto.getCompartimentoId(), dto.getDescricaoPacote(), dto.getDestinatarioId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponseDTO(entrega));
    }

    // Endpoint para o Entregador (ou sistema) confirmar que o depósito foi feito
    // e gerar o PIN para o Destinatário
    @PostMapping("/{entregaId}/confirmar-deposito")
    @PreAuthorize("hasRole('ENTREGADOR')") // Ou outra role se for sistema
    public ResponseEntity<EntregaResponseDTO> confirmarDepositoEGerarPinDestinatario(@PathVariable Long entregaId) {
        Entrega entrega = entregaService.confirmarDepositoGerarPinDestinatario(entregaId);
        return ResponseEntity.ok(convertToResponseDTO(entrega));
    }

    // Endpoint para regenerar PIN (tanto para Entregador quanto para Destinatário)
    @PostMapping("/{entregaId}/regenerar-pin")
    @PreAuthorize("isAuthenticated()") // Usuário deve estar autenticado
    public ResponseEntity<String> regenerarPin(
            @PathVariable Long entregaId,
            @Valid @RequestBody RegenerarPinRequestDTO dto) {
        String mensagem = entregaService.regenerarPin(entregaId, dto.getTipoUsuarioPin());
        return ResponseEntity.ok(mensagem);
    }

    // Listar entregas por usuário (destinatário) - existente
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("isAuthenticated()") // Ajustar permissão se necessário
    public ResponseEntity<List<EntregaResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<Entrega> entregas = entregaService.listarPorUsuario(usuarioId);
        List<EntregaResponseDTO> dtos = entregas.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Buscar entrega por ID
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EntregaResponseDTO> buscarPorId(@PathVariable Long id) {
        Entrega entrega = entregaService.buscarPorId(id);
        return ResponseEntity.ok(convertToResponseDTO(entrega));
    }


    // Método para converter Entrega para EntregaResponseDTO
    // (Mova para um Mapper dedicado ou use ModelMapper)
    private EntregaResponseDTO convertToResponseDTO(Entrega entrega) {
        // Se EntregaResponseDTO não foi definido antes, defina-o em dtos/EntregaResponseDTO.java
        // Exemplo usando ModelMapper:
        EntregaResponseDTO dto = modelMapper.map(entrega, EntregaResponseDTO.class);
        if (entrega.getDestinatario() != null) {
            dto.setDestinatarioNome(entrega.getDestinatario().getNome());
        }
        if (entrega.getCompartimento() != null) {
            dto.setCompartimentoId(entrega.getCompartimento().getId()); // Adicionar código se precisar
        }
        // Não exponha PINs no DTO de resposta!
        return dto;
    }

    // O endpoint de PUT /{id}/status genérico foi mantido no seu exemplo original,
    // mas é recomendado ter endpoints mais específicos para mudanças de status.
    // Ex: POST /{id}/cancelar, etc.
    // Se mantiver, proteja adequadamente:
    /*
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')") // Exemplo: apenas admin pode mudar status arbitrariamente
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestParam StatusEntrega status) {
        entregaService.atualizarStatus(id, status);
        return ResponseEntity.noContent().build();
    }
    */

    // O endpoint POST / para registrar entrega genérica foi removido em favor do fluxo de solicitar-deposito.
}