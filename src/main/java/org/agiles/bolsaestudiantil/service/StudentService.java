package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentEntity findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
    }
}
