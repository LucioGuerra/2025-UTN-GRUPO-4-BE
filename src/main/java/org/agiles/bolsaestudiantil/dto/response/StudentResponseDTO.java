package org.agiles.bolsaestudiantil.dto.response;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.agiles.bolsaestudiantil.dto.response.LanguageResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.SubjectResponseDTO;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudentResponseDTO extends UserResponseDTO {

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

    private List<LanguageResponseDTO> languages;
    
    private List<SubjectResponseDTO> subjects;
}
