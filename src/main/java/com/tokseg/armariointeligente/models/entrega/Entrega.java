package com.tokseg.armariointeligente.models.entrega;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.tokseg.armariointeligente.models.compartimento.Compartimento;
import com.tokseg.armariointeligente.models.usuario.Usuario;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "entrega")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entrega {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compartimento_id")
    private Compartimento compartimento;

    private String entregador;

    @CreationTimestamp
    private LocalDateTime dataEntrega;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Usuario destinatario;

    private String codigoAcesso;

    @Enumerated(EnumType.STRING)
    private StatusEntrega status;

    private String descricao;

    private String pinEntregador; // PIN para o entregador colocar o item
    private LocalDateTime pinEntregadorValidade; // Validade do PIN do entregador

    private String pinDestinatario; // PIN para o usuário/destinatário retirar o item
    private LocalDateTime pinDestinatarioValidade; //
}


