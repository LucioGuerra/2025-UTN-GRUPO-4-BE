package org.agiles.bolsaestudiantil.dto.authentication;

import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String phone;
    private String location;
    private String description;
    private String linkedinUrl;
}
