package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class AplicanteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "aplicante", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AplicacionEntity> ofertas;

    public  AplicanteEntity() {
        ofertas = new HashSet<>();
    }
}
