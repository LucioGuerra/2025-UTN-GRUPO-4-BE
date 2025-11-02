package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.internal.ApplyFilter;
import org.agiles.bolsaestudiantil.dto.request.ApplyRequestDTO;
import org.agiles.bolsaestudiantil.dto.request.update.ApplyUpdateRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.ApplyResponseDTO;
import org.agiles.bolsaestudiantil.entity.ApplyEntity;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.repository.ApplyRepository;
import org.agiles.bolsaestudiantil.mapper.ApplyMapper;
import org.agiles.bolsaestudiantil.mapper.OfferMapper;
import org.agiles.bolsaestudiantil.mapper.StudentMapper;
import org.agiles.bolsaestudiantil.specification.ApplySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;
    private final StudentService studentService;
    private final OfferService offerService;
    private final UserService userService;
    private final ApplyMapper applyMapper;
    private final OfferMapper offerMapper;
    private final StudentMapper studentMapper;

    public ApplyResponseDTO createApply(ApplyRequestDTO request) {
        ApplyEntity apply = new ApplyEntity();
        
        OfferEntity offer = offerService.getOfferEntityById(request.getOfferId());
        apply.setOffer(offer);
        
        StudentEntity student = studentService.getStudentEntityById(request.getStudentId());
        apply.setStudent(student);
        
        apply.setCustomCoverLetter(request.getCustomCoverLetter());
        
        ApplyEntity saved = applyRepository.save(apply);
        return applyMapper.toResponseDTO(saved);
    }

    public Page<? extends Object> getAllApplies(ApplyFilter filter, Pageable pageable) {
        Page<ApplyEntity> applies = applyRepository.findAll(ApplySpecification.withFilters(filter), pageable);
        
        if (filter.getStudentId() != null && filter.getOfferId() == null) {
            // Filtering by student: return offers the student applied to
            return applies.map(applyMapper::toStudentResponseDTO);
        } else {
            // Filtering by offer or no filter: return students who applied
            return applies.map(applyMapper::toOfferResponseDTO);
        }
    }

    public ApplyResponseDTO updateApply(Long id, ApplyUpdateRequestDTO request) {
        ApplyEntity entity = getApplyEntityById(id);
        if (request.getCustomCoverLetter() != null) {
            entity.setCustomCoverLetter(request.getCustomCoverLetter());
        }
        ApplyEntity saved = applyRepository.save(entity);
        return applyMapper.toResponseDTO(saved);
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


}
