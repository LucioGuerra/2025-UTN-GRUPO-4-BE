package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class AplicanteEntity extends UserEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    private String carrera;

    private Integer anioIngreso;

    private String cvUrl;

    private String cvFileName;

    @OneToMany(mappedBy = "aplicante", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private Set<AplicacionEntity> ofertas;

    @OneToMany(mappedBy = "aplicante", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MateriaXAplicanteEntity> materias = new HashSet<>();

    public  AplicanteEntity() {
        ofertas = new HashSet<>();
    }
}
