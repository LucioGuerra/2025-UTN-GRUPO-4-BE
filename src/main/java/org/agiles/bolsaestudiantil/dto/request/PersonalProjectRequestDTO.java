package org.agiles.bolsaestudiantil.dto.request;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PersonalProjectRequestDTO {
    private Long studentId;
    private String title;
    private String description;
    private List<String> technologies;
    private String projectUrl;
    private String imageUrl;
    private LocalDate startDate;
    private LocalDate endDate;
}
