package org.agiles.bolsaestudiantil.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(
        name = "keycloak-auth",
        url = "${keycloak.server-url}",
        configuration = org.agiles.bolsaestudiantil.configuration.FeignConfig.class
)
public interface KeycloakClient {
    @PostMapping(
            value = "/realms/{realm}/protocol/openid-connect/token",
            consumes = "application/x-www-form-urlencoded"
    )
    Map<String, Object> login(
            @PathVariable("realm") String realm,
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );

    @PostMapping("/admin/realms/{realm}/users")
    void createUser(
            @PathVariable("realm") String realm,
            @RequestBody Map<String, Object> user,
            @RequestHeader("Authorization") String bearerToken
    );

    @PostMapping(value = "/realms/master/protocol/openid-connect/token", consumes = "application/x-www-form-urlencoded")
    Map<String, Object> getAdminToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );
}
