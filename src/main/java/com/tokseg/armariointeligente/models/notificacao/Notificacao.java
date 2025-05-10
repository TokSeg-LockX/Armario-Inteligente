package com.tokseg.armariointeligente.models.notificacao;
import lombok.*;
import com.tokseg.armariointeligente.models.entrega.Entrega;
import com.tokseg.armariointeligente.models.usuario.Usuario;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @CreationTimestamp
    private LocalDateTime dataEnvio;

    @Enumerated(EnumType.STRING)
    private StatusNotificacao status;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String conteudo;

    @ManyToOne
    @JoinColumn(name = "entrega_id")
    private Entrega entrega;
}


