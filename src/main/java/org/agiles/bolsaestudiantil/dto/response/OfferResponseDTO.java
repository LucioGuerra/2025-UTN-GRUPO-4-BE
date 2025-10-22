package org.agiles.bolsaestudiantil.dto.response;

import jakarta.persistence.*;
import lombok.Data;
import org.agiles.bolsaestudiantil.entity.ApplyEntity;
import org.agiles.bolsaestudiantil.entity.UserEntity;

import java.util.List;

@Data
public class OfferResponseDTO {
    private Long id;

    private String title;

    private String description;

    private String requirements;

    private String modality;

    private String location;

    private String estimatedPayment;

    private List<ApplyResponseDTO> applyList;

    private UserResponseDTO bidder;

    public OfferResponseDTO(Long id, String title, String description, String location, String estimatedPayment, String requirements, String modality, List<ApplyResponseDTO> applyList, UserResponseDTO bidder) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.estimatedPayment = estimatedPayment;
        this.requirements = requirements;
        this.modality = modality;
        this.applyList = applyList;
        this.bidder = bidder;
    }
}
