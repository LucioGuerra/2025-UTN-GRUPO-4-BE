package org.agiles.bolsaestudiantil.dto.request.update;

import lombok.Data;
import org.agiles.bolsaestudiantil.dto.request.LanguageRequestDTO;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudentUpdateRequestDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String location;
    private String description;
    private String linkedinUrl;
    private String githubUrl;
    private String career;
    private Integer currentYearLevel;
    private String institution;
    private List<String> skills;
    private LocalDate incomeDate;
    private LocalDate dateOfBirth;
    private String cvUrl;
    private String cvFileName;
    private String coverLetter;
    private List<String> attributes;
    private List<LanguageRequestDTO> languages;
}