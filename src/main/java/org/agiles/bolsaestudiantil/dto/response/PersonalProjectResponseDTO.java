package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PersonalProjectResponseDTO {
    private Long id;
    private String title;
    private String description;
    private List<String> technologies;
    private String projectUrl;
    private String imageUrl;
    private LocalDate startDate;
    private LocalDate endDate;
}
