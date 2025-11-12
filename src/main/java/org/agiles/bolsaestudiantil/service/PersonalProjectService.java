package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.PersonalProjectRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.PersonalProjectResponseDTO;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.entity.PersonalProjectEntity;
import org.agiles.bolsaestudiantil.mapper.PersonalProjectMapper;
import org.agiles.bolsaestudiantil.repository.PersonalProjectRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonalProjectService {

    private final PersonalProjectRepository personalProjectRepository;
    private final PersonalProjectMapper personalProjectMapper;
    private final StudentService studentService;

    public PersonalProjectResponseDTO create(PersonalProjectRequestDTO request){
        StudentEntity student = studentService.getStudentEntityById(request.getStudentId());

        PersonalProjectEntity entity = personalProjectMapper.requestToEntity(request);

        entity.setStudent(student);
        PersonalProjectEntity saved = personalProjectRepository.save(entity);

        return personalProjectMapper.toResponseDTO(saved);
    }

    public PersonalProjectResponseDTO update(Long id, PersonalProjectRequestDTO request) {
        PersonalProjectEntity entity = getEntityById(id);
        personalProjectMapper.updateEntityFromRequest(request, entity);
        PersonalProjectEntity saved = personalProjectRepository.save(entity);
        return personalProjectMapper.toResponseDTO(saved);
    }

    public void delete(Long id) {
        if (!personalProjectRepository.existsById(id)) {
            throw new EntityNotFoundException("PersonalProject not found with id: " + id);
        }
        personalProjectRepository.deleteById(id);
    }

    private PersonalProjectEntity getEntityById(Long id) {
        return personalProjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PersonalProject not found with id: " + id));
    }
}
