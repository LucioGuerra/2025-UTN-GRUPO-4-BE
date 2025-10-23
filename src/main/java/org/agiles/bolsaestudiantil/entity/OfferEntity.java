package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class OfferEntity {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String requirements;

    private String modality;

    private String location;

    private String estimatedPayment;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ApplyEntity> applyList;

    @OneToMany(fetch = FetchType.LAZY)
    private List<AttributeEntity> attributes;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity bidder;

    public OfferEntity() {
        this.applyList = new ArrayList<>();
        this.attributes = new ArrayList<>();
    }

}
