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

import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final StudentOfferRepository studentOfferRepository;
    private final OfferMapper offerMapper;
    private final StudentService studentService;

    @Transactional
    public void applyToOffer(Long offerId, Long studentId, String coverLetter) {
        StudentEntity student = studentService.findById(studentId);
        OfferEntity offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with id: " + offerId));

        this.validatePreviousApplication(offerId, studentId);

        offer.addStudent(student, coverLetter);
        offerRepository.save(offer);
    }


    private void validatePreviousApplication(Long offerId, Long studentId) {
        Optional<StudentOfferEntity> studentOfferOpt = studentOfferRepository.findByStudentAndOffer(studentId, offerId);
        if (studentOfferOpt.isPresent()) {
            throw new UnijobsException("STUDENT_ALREADY_APPLIED", "Student with id " + studentId + " has already applied to offer with id " + offerId);
        }
    }
}
