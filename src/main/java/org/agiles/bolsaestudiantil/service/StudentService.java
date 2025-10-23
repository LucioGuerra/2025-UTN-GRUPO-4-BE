package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.StudentResponseDTO;
import org.agiles.bolsaestudiantil.entity.AttributeEntity;
import org.agiles.bolsaestudiantil.entity.LanguageEntity;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.event.RegisterUserEvent;
import org.agiles.bolsaestudiantil.repository.StudentRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;
    private final AttributeService attributeService;
    private final LanguageService languageService;

    public StudentEntity getStudentEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
    }

    public StudentResponseDTO getStudentById(Long id) {
        StudentEntity entity = getStudentEntityById(id);
        return userService.mapToStudentDTO(entity);
    }

    public StudentResponseDTO updateStudent(Long id, Map<String, Object> updates) {
        StudentEntity entity = getStudentEntityById(id);
        updateEntityFromMap(entity, updates);
        StudentEntity saved = studentRepository.save(entity);
        return userService.mapToStudentDTO(saved);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    public void deleteLanguageFromStudent(Long studentId, Long languageId) {
        StudentEntity student = getStudentEntityById(studentId);
        student.getLanguages().removeIf(lang -> lang.getId().equals(languageId));
        studentRepository.save(student);
        languageService.deleteLanguage(languageId);
    }

    private void updateEntityFromMap(StudentEntity entity, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            if (value != null) {
                switch (key) {
                    case "name" -> entity.setName((String) value);
                    case "surname" -> entity.setSurname((String) value);
                    case "email" -> entity.setEmail((String) value);
                    case "phone" -> entity.setPhone((String) value);
                    case "location" -> entity.setLocation((String) value);
                    case "description" -> entity.setDescription((String) value);
                    case "linkedinUrl" -> entity.setLinkedinUrl((String) value);
                    case "githubUrl" -> entity.setGithubUrl((String) value);
                    case "career" -> entity.setCareer((String) value);
                    case "currentYearLevel" -> entity.setCurrentYearLevel((Integer) value);
                    case "institution" -> entity.setInstitution((String) value);
                    case "coverLetter" -> entity.setCoverLetter((String) value);
                    case "attributes" -> {
                        if (value instanceof List<?> attributeNames) {
                            List<AttributeEntity> attributes = attributeNames.stream()
                                    .map(name -> attributeService.findOrCreateAttribute((String) name))
                                    .toList();
                            entity.setAttributes(attributes);
                        }
                    }
                    case "languages" -> {
                        if (value instanceof List<?> languageRequests) {
                            List<LanguageEntity> languages = languageRequests.stream()
                                    .map(langReq -> {
                                        if (langReq instanceof Map<?, ?> langMap) {
                                            String name = (String) langMap.get("name");
                                            Integer level = (Integer) langMap.get("level");
                                            
                                            LanguageEntity existing = entity.getLanguages().stream()
                                                    .filter(lang -> lang.getName().equals(name))
                                                    .findFirst()
                                                    .orElse(null);
                                            
                                            if (existing != null) {
                                                existing.setLevel(level);
                                                return existing;
                                            } else {
                                                return languageService.createLanguage(name, level);
                                            }
                                        }
                                        return null;
                                    })
                                    .filter(Objects::nonNull)
                                    .toList();
                            entity.setLanguages(languages);
                        }
                    }
                }
            }
        });
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
