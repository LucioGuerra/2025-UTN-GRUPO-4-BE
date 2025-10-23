package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.internal.ApplyFilter;
import org.agiles.bolsaestudiantil.dto.request.ApplyRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.ApplyResponseDTO;
import org.agiles.bolsaestudiantil.entity.ApplyEntity;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.repository.ApplyRepository;
import org.agiles.bolsaestudiantil.specification.ApplySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final StudentService studentService;
    private final OfferService offerService;
    private final UserService userService;

    public ApplyResponseDTO createApply(ApplyRequestDTO request) {
        ApplyEntity apply = new ApplyEntity();
        
        OfferEntity offer = offerService.getOfferEntityById(request.getOfferId());
        apply.setOffer(offer);
        
        StudentEntity student = studentService.getStudentEntityById(request.getStudentId());
        apply.setStudent(student);
        
        apply.setCustomCoverLetter(request.getCustomCoverLetter());
        
        ApplyEntity saved = applyRepository.save(apply);
        return mapToDTO(saved);
    }

    public Page<ApplyResponseDTO> getAllApplies(ApplyFilter filter, Pageable pageable) {
        Page<ApplyEntity> applies = applyRepository.findAll(ApplySpecification.withFilters(filter), pageable);
        return applies.map(this::mapToDTO);
    }

    public ApplyResponseDTO updateApply(Long id, Map<String, Object> updates) {
        ApplyEntity entity = getApplyEntityById(id);
        if (updates.containsKey("customCoverLetter")) {
            entity.setCustomCoverLetter((String) updates.get("customCoverLetter"));
        }
        ApplyEntity saved = applyRepository.save(entity);
        return mapToDTO(saved);
    }

    public void deleteApply(Long id) {
        if (!applyRepository.existsById(id)) {
            throw new EntityNotFoundException("Apply not found with id: " + id);
        }
        applyRepository.deleteById(id);
    }

    private ApplyEntity getApplyEntityById(Long id) {
        return applyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apply not found with id: " + id));
    }

    private ApplyResponseDTO mapToDTO(ApplyEntity entity) {
        ApplyResponseDTO dto = new ApplyResponseDTO();
        dto.setCustomCoverLetter(entity.getCustomCoverLetter());
        dto.setStudent(userService.mapToStudentDTO(entity.getStudent()));
        return dto;
    }
}
