package com.tokseg.armariointeligente.models.relatorioacesso;

import com.tokseg.armariointeligente.models.usuario.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "relatorio_acesso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatorioAcesso {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private TipoAcao acao;

    @Enumerated(EnumType.STRING)
    private ResultadoAcao resultado;

    @CreationTimestamp
    private LocalDateTime dataHora;

    private String detalhes;
    private String ip;
    private String dispositivo;
}


