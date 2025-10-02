package org.agiles.bolsaestudiantil.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.agiles.bolsaestudiantil.dto.response.OfferDTO;
import org.agiles.bolsaestudiantil.entity.OfferEntity;
import org.agiles.bolsaestudiantil.entity.StudentEntity;
import org.agiles.bolsaestudiantil.entity.StudentOfferEntity;
import org.agiles.bolsaestudiantil.entity.StudentOfferId;
import org.agiles.bolsaestudiantil.exception.UnijobsException;
import org.agiles.bolsaestudiantil.mapper.OfferMapper;
import org.agiles.bolsaestudiantil.repository.OfferRepository;
import org.agiles.bolsaestudiantil.repository.StudentOfferRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final StudentOfferRepository studentOfferRepository;
    private final OfferMapper offerMapper;
    private final StudentService studentService;

    @Transactional
    public OfferDTO applyToOffer(Long offerId, Long studentId, String coverLetter) {
        StudentEntity student = studentService.findById(studentId);
        OfferEntity offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with id: " + offerId));

        StudentOfferId id = new StudentOfferId(student.getId(), offer.getId());

        if (studentOfferRepository.existsById(id)) {
            throw new UnijobsException("STUDENT_ALREADY_APPLIED", "The student has already applied to this offer.");
        }

        StudentOfferEntity relation = new StudentOfferEntity();
        relation.setId(id);
        relation.setStudent(student);
        relation.setOffer(offer);
        relation.setCoverLetter(coverLetter);

        studentOfferRepository.save(relation);

        offer.getStudents().add(relation);
        student.getOffers().add(relation);

        return offerMapper.toDTO(offer);
    }

}
