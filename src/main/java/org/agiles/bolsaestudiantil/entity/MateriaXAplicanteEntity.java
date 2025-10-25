package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MateriaXAplicanteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer nota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id")
    private MateriaEntity materia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aplicante_id")
    private AplicanteEntity aplicante;

}
