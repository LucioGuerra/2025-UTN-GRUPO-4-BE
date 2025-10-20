package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class OfertaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 2000)
    private String descripcion;

    @Column(length = 2000)
    private String requisitos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaEntity empresa;

    private String locacion;

    private String pagoAprox;

    private String modalidad; // remoto, híbrido, presencial

    @Column(nullable = false)
    private String tipoContrato; // permanente, temporal, pasantía, etc.

    @Column(nullable = false)
    private LocalDateTime publishDate;

    private LocalDateTime closeDate;

    @Column(nullable = false)
    private String status; // active, closed, expired

    @OneToMany(mappedBy = "oferta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OfertaAttributeEntity> attributes = new HashSet<>();

    @OneToMany(mappedBy = "oferta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AplicacionEntity> aplicaciones;

    public OfertaEntity() {
        this.aplicaciones = new HashSet<>();
        this.publishDate = LocalDateTime.now();
        this.status = "active";
    }

    public void addAplicante(AplicanteEntity aplicante, String cartaPresentacion) {
        if (aplicante == null) {
            throw new IllegalArgumentException("Aplicante no puede ser null");
        }

        AplicacionEntity aplicacion = new AplicacionEntity();
        aplicacion.setAplicante(aplicante);
        aplicacion.setOferta(this);
        aplicacion.setCartaPresentacion(cartaPresentacion);
        this.aplicaciones.add(aplicacion);
        aplicante.getOfertas().add(aplicacion);
    }
}
