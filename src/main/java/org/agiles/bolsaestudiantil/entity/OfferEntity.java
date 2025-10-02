package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;

import java.util.HashSet;

@Entity
public class OfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private HashSet<StudentEntity> students;
}
