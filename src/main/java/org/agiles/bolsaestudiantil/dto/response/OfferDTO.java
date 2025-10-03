package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OfferDTO {
    private Long id;
    private String title;
    private String description;
    private String company;
    private String location;
    private BigDecimal salary;
    private String workType;
    private String contractType;
    private LocalDateTime publishDate;
    private LocalDateTime closeDate;
    private String status;
    private Set<StudentDTO> students;
}
