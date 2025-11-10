package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.WorkExperienceRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.WorkExperienceResponseDTO;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.entity.WorkExperienceEntity;
import org.agiles.bolsaestudiantil.mapper.WorkExperienceMapper;
import org.agiles.bolsaestudiantil.repository.WorkExperienceRepository;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceMapper workExperienceMapper;
    private final StudentService studentService;

    public WorkExperienceResponseDTO create(WorkExperienceRequestDTO request) {
        StudentEntity student = studentService.getStudentEntityById(request.getStudentId());

        WorkExperienceEntity entity = workExperienceMapper.requestToEntity(request);

        entity.setStudent(student);
        WorkExperienceEntity saved = workExperienceRepository.save(entity);

        return workExperienceMapper.toResponseDTO(saved);
    }

    public WorkExperienceResponseDTO update(Long id, WorkExperienceRequestDTO request) {
        WorkExperienceEntity entity = getEntityById(id);
        workExperienceMapper.updateEntityFromRequest(request, entity);
        WorkExperienceEntity saved = workExperienceRepository.save(entity);
        return workExperienceMapper.toResponseDTO(saved);
    }

    public void delete(Long id) {
        if (!workExperienceRepository.existsById(id)) {
            throw new EntityNotFoundException("WorkExperience with id: " + id + " not found");
        }
        workExperienceRepository.deleteById(id);
    }

    private WorkExperienceEntity getEntityById(Long id) {
        return workExperienceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("WorkExperience with id: " + id));
    }
}
