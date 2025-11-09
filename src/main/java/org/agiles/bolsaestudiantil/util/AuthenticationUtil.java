package org.agiles.bolsaestudiantil.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class AuthenticationUtil {

    public static String getKeycloakIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        
        if (principal instanceof Jwt jwt) {
            return jwt.getSubject(); // El subject del JWT contiene el keycloakId
        }
        
        return null;
    }
}
