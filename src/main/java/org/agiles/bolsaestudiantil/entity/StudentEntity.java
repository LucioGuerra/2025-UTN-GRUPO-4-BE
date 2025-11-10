package org.agiles.bolsaestudiantil.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.agiles.bolsaestudiantil.repository.OrganizationStudentAssociationRepository;

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

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<ApplyEntity> applies;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_attributes")
    private List<AttributeEntity> attributes;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentSubjectEntity> subjects;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private List<LanguageEntity> languages;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperienceEntity> workExperience;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrganizationStudentAssociationEntity> associatedCompanies;

    public StudentEntity() {
        super();
        this.applies = new ArrayList<>();
        this.attributes = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.languages = new ArrayList<>();
        this.workExperience = new ArrayList<>();
        this.associatedCompanies = new ArrayList<>();
    }

}
