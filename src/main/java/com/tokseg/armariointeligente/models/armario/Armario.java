package com.tokseg.armariointeligente.models.armario;

import com.tokseg.armariointeligente.models.compartimento.Compartimento;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "armario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Armario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String localizacao;

    private Integer quantidadeCompartimento;

    @Enumerated(EnumType.STRING)
    private StatusArmario status;

    @CreationTimestamp
    private LocalDateTime dataCadastro;
}
