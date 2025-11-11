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

    @Column(length = 5000)
    private String description;

    @Column(length = 5000)
    private String requirements;

    private String modality;

    private String location;

    private String estimatedPayment;

    @OneToMany(mappedBy = "offer", fetch = FetchType.LAZY)
    private List<ApplyEntity> applyList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "offer_attributes")
    private List<AttributeEntity> attributes;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity bidder;

    @Column()
    private Integer positiveVotes = 0;

    @Column()
    private Integer negativeVotes = 0;

    @OneToMany(mappedBy = "offer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OfferVoteEntity> votes;

    public OfferEntity() {
        this.applyList = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.votes = new ArrayList<>();
        this.positiveVotes = 0;
        this.negativeVotes = 0;
    }

    public Integer getTotalScore() {
        return positiveVotes - negativeVotes;
    }

}
