package com.tokseg.armariointeligente.models.retirada;

import com.tokseg.armariointeligente.models.entrega.Entrega;
import com.tokseg.armariointeligente.models.usuario.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "retirada")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Retirada {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entrega_id")
    private Entrega entrega;

    @ManyToOne
    @JoinColumn(name = "retirado_por")
    private Usuario retiradoPor;

    @CreationTimestamp
    private LocalDateTime dataRetirada;

    @Enumerated(EnumType.STRING)
    private MetodoAutenticacao metodoAutenticacao;
}

