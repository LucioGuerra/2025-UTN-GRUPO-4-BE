package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(fetch = FetchType.LAZY)
    private List<ApplyEntity> applies;

    @OneToMany(fetch = FetchType.LAZY)
    private List<AttributeEntity> attributes;

    @OneToMany(fetch = FetchType.LAZY)
    private List<SubjectEntity> subjects;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<LanguageEntity> languages;

    public StudentEntity() {
        super();
        this.applies = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.languages = new ArrayList<>();
    }

}
