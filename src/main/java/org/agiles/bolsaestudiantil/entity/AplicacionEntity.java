package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.agiles.bolsaestudiantil.enums.EstadoAplicacion;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"aplicante_id", "oferta_id"})
})
public class AplicacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aplicante_id")
    private AplicanteEntity aplicante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oferta_id")
    private OfertaEntity oferta;

    private String cartaPresentacion;

    @Column(nullable = false)
    private LocalDateTime fechaAplicacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoAplicacion estado;

    public AplicacionEntity() {
        this.fechaAplicacion = LocalDateTime.now();
        this.estado = EstadoAplicacion.APLICADO;
    }
}
