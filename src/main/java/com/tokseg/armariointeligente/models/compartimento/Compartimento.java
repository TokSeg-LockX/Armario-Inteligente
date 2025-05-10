package com.tokseg.armariointeligente.models.compartimento;
import com.tokseg.armariointeligente.models.armario.Armario;
import com.tokseg.armariointeligente.models.entrega.Entrega;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "compartimento", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"armario_id", "codigo"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compartimento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "armario_id")
    private Armario armario;

    private String codigo;

    @Enumerated(EnumType.STRING)
    private StatusCompartimento status;

    @Enumerated(EnumType.STRING)
    private TamanhoCompartimento tamanho;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;
}
