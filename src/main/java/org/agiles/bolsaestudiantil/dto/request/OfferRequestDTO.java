package org.agiles.bolsaestudiantil.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OfferRequestDTO {

    @JsonProperty("titulo")
    @NotBlank
    private String title;

    @JsonProperty("descripcion")
    @NotBlank
    private String description;

    @JsonProperty("requisitos")
    private String requirements;

    @JsonProperty("modalidad")
    @NotBlank
    private String modality;

    @JsonProperty("locacion")
    private String location;

    @JsonProperty("pagoAprox")
    private String estimatedPayment;

    @JsonProperty("atributos")
    @NotNull
    private List<String> attributes;

    private Long bidderId;
}
