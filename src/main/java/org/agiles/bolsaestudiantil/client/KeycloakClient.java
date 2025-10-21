package org.agiles.bolsaestudiantil.client;

import org.agiles.bolsaestudiantil.dto.authentication.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    LoginResponse login(
            @PathVariable("realm") String realm,
            @RequestBody MultiValueMap<String, String> form
    );

    @PostMapping("/admin/realms/{realm}/users")
    void createUser(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("realm") String realm,
            @RequestBody Map<String, Object> user
    );

    @PostMapping(value = "/realms/master/protocol/openid-connect/token", consumes = "application/x-www-form-urlencoded")
    Map<String, Object> getAdminToken(@RequestBody MultiValueMap<String, String> form);

    @GetMapping("/admin/realms/{realm}/users/{userId}/role-mappings/realm")
    List<Map<String, Object>> getUserRealmRoles(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("realm") String realm,
            @PathVariable("userId") String userId
    );

    @PostMapping("/admin/realms/{realm}/users/{userId}/role-mappings/realm")
    void assignRole(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("realm") String realm,
            @PathVariable("userId") String userId,
            @RequestBody List<Map<String, Object>> roles
    );

    @GetMapping("/admin/realms/{realm}/roles/{roleName}")
    Map<String, Object> getRoleByName(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("realm") String realm,
            @PathVariable("roleName") String roleName
    );

    @GetMapping("/admin/realms/{realm}/users")
    List<Map<String, Object>> getUsersByUsername(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable("realm") String realm,
            @RequestParam("username") String username
    );
}
