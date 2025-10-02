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

    public void addStudent(StudentEntity student) {
        if (students == null) {
            students = new HashSet<>();
        }
        students.add(student);
    }

    public void removeStudent(StudentEntity student) {
        if (students != null) {
            students.remove(student);
        }
    }
}
