package com.tokseg.armariointeligente.models.autorizacao;

import com.tokseg.armariointeligente.models.entrega.Entrega;
import com.tokseg.armariointeligente.models.usuario.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "autorizacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autorizacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entrega_id")
    private Entrega entrega;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "autorizado_id")
    private Usuario autorizado;

    private LocalDateTime dataValidade;

    @Enumerated(EnumType.STRING)
    private StatusAutorizacao status;

    @CreationTimestamp
    private LocalDateTime dataCadastro;
}

