package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.client.GradeProcessorClient;
import org.agiles.bolsaestudiantil.dto.request.SubjectRequestDTO;
import org.agiles.bolsaestudiantil.dto.request.update.StudentUpdateRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.StudentResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.SubjectResponseDTO;
import org.agiles.bolsaestudiantil.entity.*;
import org.agiles.bolsaestudiantil.event.RegisterUserEvent;
import org.agiles.bolsaestudiantil.mapper.StudentMapper;
import org.agiles.bolsaestudiantil.mapper.SubjectMapper;
import org.agiles.bolsaestudiantil.repository.StudentRepository;
import org.agiles.bolsaestudiantil.repository.StudentSubjectRepository;
import org.agiles.bolsaestudiantil.repository.SubjectRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;
    private final AttributeService attributeService;
    private final LanguageService languageService;
    private final StudentMapper studentMapper;
    private final SubjectRepository subjectRepository;
    private final StudentSubjectRepository studentSubjectRepository;
    private final SubjectMapper subjectMapper;
    private final GradeProcessorClient gradeProcessorClient;
    private final MinioService minioService;

    public StudentEntity getStudentEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
    }

    public StudentResponseDTO getStudentById(Long id) {
        StudentEntity entity = getStudentEntityById(id);
        return studentMapper.toResponseDTO(entity);
    }

    public StudentResponseDTO updateStudent(Long id, StudentUpdateRequestDTO request) {
        StudentEntity entity = getStudentEntityById(id);
        updateEntityFromDTO(entity, request);
        StudentEntity saved = studentRepository.save(entity);
        return studentMapper.toResponseDTO(saved);
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

    @Transactional
    public List<SubjectResponseDTO> loadSubjects(Long id, List<SubjectRequestDTO> subjects) {
        StudentEntity student = getStudentEntityById(id);
        
        student.getSubjects().clear();
        
        List<StudentSubjectEntity> newSubjects = new ArrayList<>();
        
        for (SubjectRequestDTO dto : subjects) {
            if (dto.getNote() != null && dto.getNote() >= 6) {
                SubjectEntity subject = subjectRepository.findByCode(dto.getCode())
                        .orElseGet(() -> {
                            SubjectEntity newSubject = new SubjectEntity();
                            newSubject.setCode(dto.getCode());
                            newSubject.setName(dto.getName());
                            return subjectRepository.save(newSubject);
                        });
                
                StudentSubjectEntity link = new StudentSubjectEntity();
                link.setStudent(student);
                link.setSubject(subject);
                link.setNote(dto.getNote());
                
                newSubjects.add(link);
            }
        }
        
        student.getSubjects().addAll(newSubjects);
        StudentEntity savedStudent = studentRepository.save(student);
        
        return savedStudent.getSubjects().stream()
                .map(subjectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Map<String, Object> processGradesFromExcel(Long id, MultipartFile file, String authToken) {
        getStudentEntityById(id); // Validate student exists
        return gradeProcessorClient.processGrades(file, id.toString(), authToken.replace("Bearer ", ""));
    }

    public StudentResponseDTO uploadCv(Long id, MultipartFile file) {
        try {
            StudentEntity student = getStudentEntityById(id);
            
            // Delete old CV if exists
            if (student.getCvUrl() != null) {
                minioService.deleteFile(student.getCvUrl());
            }
            
            String cvUrl = minioService.uploadCurriculum(file);
            student.setCvUrl(cvUrl);
            student.setCvFileName(file.getOriginalFilename());
            StudentEntity saved = studentRepository.save(student);
            return studentMapper.toResponseDTO(saved);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading CV: " + e.getMessage());
        }
    }

    public StudentResponseDTO deleteCv(Long id) {
        StudentEntity student = getStudentEntityById(id);
        
        if (student.getCvUrl() != null) {
            minioService.deleteFile(student.getCvUrl());
            student.setCvUrl(null);
            student.setCvFileName(null);
        }
        
        StudentEntity saved = studentRepository.save(student);
        return studentMapper.toResponseDTO(saved);
    }
}
