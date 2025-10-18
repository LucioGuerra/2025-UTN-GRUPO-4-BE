package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class OfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String company;

    private String location;

    private BigDecimal salary;

    private String workType; // full-time, part-time, freelance, internship

    @Column(nullable = false)
    private String contractType; // permanente, temporal, pasantía, etc.

    @Column(nullable = false)
    private LocalDateTime publishDate;

    private LocalDateTime closeDate;

    @Column(nullable = false)
    private String status; // active, closed, expired

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OfferAttributeEntity> attributes = new HashSet<>();

    @OneToMany(mappedBy = "oferta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AplicacionEntity> aplicaciones;

    public OfferEntity() {
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
