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
    private Set<StudentOfferEntity> students;

    public OfferEntity() {
        this.students = new HashSet<>();
        this.publishDate = LocalDateTime.now();
        this.status = "active";
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
