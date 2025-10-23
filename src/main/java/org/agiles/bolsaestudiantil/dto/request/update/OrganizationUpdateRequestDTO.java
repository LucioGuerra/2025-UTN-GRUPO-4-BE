package org.agiles.bolsaestudiantil.dto.request.update;

import lombok.Data;
import org.agiles.bolsaestudiantil.enums.Size;

@Data
public class OrganizationUpdateRequestDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String location;
    private String description;
    private String linkedinUrl;
    private String webSiteUrl;
    private String industry;
    private Size size;
}