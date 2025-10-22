package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class StudentEntity extends UserEntity {

    private String githubUrl;

    private String career;

    private Integer currentYearLevel;

    private String institution;

    private List<String> skills;

    private String phone;

    private LocalDate incomeDate;

    private LocalDate dateOfBirth;

    private String cvUrl;

    private String cvFileName;

    private String coverLetter;

    public StudentEntity() {
        super();
    }

}
