package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.event.RegisterUserEvent;
import org.agiles.bolsaestudiantil.repository.StudentRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentEntity getStudentEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
    }

    @Async
    @EventListener
    public void handleStudentRegistration(RegisterUserEvent event) {
        if ("Student".equals(event.getRole())) {
            StudentEntity student = new StudentEntity();
            student.setKeycloakId(event.getKeycloakId());
            student.setName(event.getFirstName());
            student.setSurname(event.getLastName());
            student.setEmail(event.getEmail());
            student.setPhone(event.getPhone());
            student.setLocation(event.getLocation());
            student.setDescription(event.getDescription());
            student.setLinkedinUrl(event.getLinkedinUrl());
            studentRepository.save(student);
        }
    }
}
