package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.update.StudentUpdateRequestDTO;
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

    public StudentResponseDTO updateStudent(Long id, StudentUpdateRequestDTO request) {
        StudentEntity entity = getStudentEntityById(id);
        updateEntityFromDTO(entity, request);
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

    private void updateEntityFromDTO(StudentEntity entity, StudentUpdateRequestDTO request) {
        if (request.getName() != null) entity.setName(request.getName());
        if (request.getSurname() != null) entity.setSurname(request.getSurname());
        if (request.getEmail() != null) entity.setEmail(request.getEmail());
        if (request.getPhone() != null) entity.setPhone(request.getPhone());
        if (request.getLocation() != null) entity.setLocation(request.getLocation());
        if (request.getDescription() != null) entity.setDescription(request.getDescription());
        if (request.getLinkedinUrl() != null) entity.setLinkedinUrl(request.getLinkedinUrl());
        if (request.getGithubUrl() != null) entity.setGithubUrl(request.getGithubUrl());
        if (request.getCareer() != null) entity.setCareer(request.getCareer());
        if (request.getCurrentYearLevel() != null) entity.setCurrentYearLevel(request.getCurrentYearLevel());
        if (request.getInstitution() != null) entity.setInstitution(request.getInstitution());
        if (request.getSkills() != null) entity.setSkills(request.getSkills());
        if (request.getIncomeDate() != null) entity.setIncomeDate(request.getIncomeDate());
        if (request.getDateOfBirth() != null) entity.setDateOfBirth(request.getDateOfBirth());
        if (request.getCvUrl() != null) entity.setCvUrl(request.getCvUrl());
        if (request.getCvFileName() != null) entity.setCvFileName(request.getCvFileName());
        if (request.getCoverLetter() != null) entity.setCoverLetter(request.getCoverLetter());
        
        if (request.getAttributes() != null) {
            List<AttributeEntity> attributes = request.getAttributes().stream()
                    .map(attributeService::findOrCreateAttribute)
                    .toList();
            entity.setAttributes(attributes);
        }
        
        if (request.getLanguages() != null) {
            List<LanguageEntity> languages = request.getLanguages().stream()
                    .map(langReq -> {
                        LanguageEntity existing = entity.getLanguages().stream()
                                .filter(lang -> lang.getName().equals(langReq.getName()))
                                .findFirst()
                                .orElse(null);
                        
                        if (existing != null) {
                            existing.setLevel(langReq.getLevel());
                            return existing;
                        } else {
                            return languageService.createLanguage(langReq.getName(), langReq.getLevel());
                        }
                    })
                    .toList();
            entity.setLanguages(languages);
        }
    }

    private void updateEntityFromMap(StudentEntity entity, Map<String, Object> updates) {
        // Deprecated - use updateEntityFromDTO instead
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
