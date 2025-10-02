package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(nullable = false, length = 1000)
    private String description;

    private String requirements;
    private String modality;
    private String location;
    private String approximatePayment;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OfferAttributeEntity> attributes = new HashSet<>();

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentOfferEntity> students;

    public OfferEntity() {
        this.students = new HashSet<>();
    }

    public void addStudent(StudentEntity student, String coverLetter) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        StudentOfferEntity studentOffer = new StudentOfferEntity();
        studentOffer.setStudent(student);
        studentOffer.setOffer(this);
        studentOffer.setCoverLetter(coverLetter);
        this.students.add(studentOffer);
        student.getOffers().add(studentOffer);
    }
}
