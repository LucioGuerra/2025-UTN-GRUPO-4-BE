package org.agiles.bolsaestudiantil.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@FeignClient(
        name = "grade-processor",
        url = "${grade-processor.url:http://grade-processor:5000}"
)
public interface GradeProcessorClient {
    
    @PostMapping(value = "/procesar-notas", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Map<String, Object> processGrades(
            @RequestPart("file") MultipartFile file,
            @RequestPart("aplicante_id") String aplicanteId,
            @RequestPart("auth_token") String authToken
    );
}