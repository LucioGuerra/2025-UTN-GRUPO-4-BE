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
