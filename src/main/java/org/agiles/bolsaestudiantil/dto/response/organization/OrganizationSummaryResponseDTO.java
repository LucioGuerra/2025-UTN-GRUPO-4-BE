package org.agiles.bolsaestudiantil.dto.response.organization;

import lombok.Data;

@Data
public class OrganizationSummaryResponseDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String industry;
    private String location;
    private String role = "Organization";
}
