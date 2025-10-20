package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class EmpresaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String logo;

    @Column(length = 2000)
    private String descripcion;

    private String sector;

    private String tamanio;

    private String sitioWeb;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private Set<OfertaEntity> ofertas = new HashSet<>();
}
