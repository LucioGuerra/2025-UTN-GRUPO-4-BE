package org.agiles.bolsaestudiantil.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserEvent {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String keycloakId;
    private String role;
    private String phone;
    private String location;
    private String description;
    private String linkedinUrl;
}
