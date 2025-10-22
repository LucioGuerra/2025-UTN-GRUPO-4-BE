package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ApplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customCoverLetter;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private OfferEntity offer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private StudentEntity student;
}
