package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AssociatedCompanyDTO {
    private Long id;
    private Long companyId;
    private String companyName;
    private String companyImageUrl;
    private String companyIndustry;
    private LocalDate associationDate;
    private String recognitionType;
}
