package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.internal.AttributeFilter;
import org.agiles.bolsaestudiantil.dto.response.AttributeResponseDTO;
import org.agiles.bolsaestudiantil.service.AttributeService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping
    public ResponseEntity<List<AttributeResponseDTO>> getAllAttributes(
            @RequestParam(required = false) String name) {
        
        AttributeFilter filter = new AttributeFilter();
        filter.setName(name);
        
        List<AttributeResponseDTO> response = attributeService.getAllAttributes(filter);
        return ResponseEntity.ok(response);
    }
}