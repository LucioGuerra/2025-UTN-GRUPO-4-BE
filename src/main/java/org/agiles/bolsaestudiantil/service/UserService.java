package org.agiles.bolsaestudiantil.service;

import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.entity.AplicanteEntity;
import org.agiles.bolsaestudiantil.entity.UserEntity;
import org.agiles.bolsaestudiantil.event.RegisterUserEvent;
import org.agiles.bolsaestudiantil.repository.UserRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    @Async
    @EventListener
    public void registerUserListener(RegisterUserEvent event) {
        if (Objects.equals(event.getRole(), "Student")){
            AplicanteEntity applicante = new AplicanteEntity();
            applicante.setNombre(event.getFirstName() + " " + event.getLastName());
            applicante.setKeycloakId(event.getKeycloakId());
            userRepository.save(applicante);
        }
        if (Objects.equals(event.getRole(), "Organization")){
            System.out.println("Se crea organizacion");
        }
    }
}
