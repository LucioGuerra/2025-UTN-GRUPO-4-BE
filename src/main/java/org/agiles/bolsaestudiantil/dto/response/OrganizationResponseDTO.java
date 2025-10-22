package org.agiles.bolsaestudiantil.dto.response;

import lombok.Data;
import org.agiles.bolsaestudiantil.enums.Size;

@Data
public class OrganizationResponseDTO extends UserResponseDTO {
    private String webSiteUrl;

    private String industry;

    private Size size;
}
