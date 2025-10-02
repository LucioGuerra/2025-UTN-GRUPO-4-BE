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

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudentOfferEntity> students;


    public OfferEntity() {
        this.students = new HashSet<>();
    }

    public void addStudent(StudentEntity student, String coverLetter) {
        StudentOfferEntity relation = new StudentOfferEntity();
        relation.setId(new StudentOfferId(student.getId(), this.getId()));
        relation.setStudent(student);
        relation.setOffer(this);
        relation.setCoverLetter(coverLetter);

        students.add(relation);
        student.getOffers().add(relation);
    }

    public void removeStudent(StudentEntity student) {
        if (students == null) return;

        students.removeIf(rel -> rel.getStudent().equals(student));
        student.getOffers().removeIf(rel -> rel.getOffer().equals(this));
    }
}
