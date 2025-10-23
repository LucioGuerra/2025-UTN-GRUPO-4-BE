package org.agiles.bolsaestudiantil.dto.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;

    private String description;

    private String phone;

    private String email;

    private String location;

    private String name;

    private String surname;

    private String imageUrl;

    private String linkedinUrl;

    private String role;
}
