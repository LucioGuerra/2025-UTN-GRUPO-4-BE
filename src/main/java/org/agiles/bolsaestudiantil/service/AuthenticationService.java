package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.client.KeycloakClient;
import org.agiles.bolsaestudiantil.dto.authentication.LoginRequest;
import org.agiles.bolsaestudiantil.dto.authentication.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final KeycloakClient keycloakClient;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.grant-type}")
    private String grantType;

    @Value("${keycloak.scope}")
    private String scope;



    public LoginResponse login(LoginRequest request) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", grantType);
        form.add("client_id", clientId);
        form.add("username", request.getUsername());
        form.add("password", request.getPassword());
        form.add("scope", scope);

        LoginResponse response = keycloakClient.login(
                realm,
                form
        );
        return response;
    }

}
