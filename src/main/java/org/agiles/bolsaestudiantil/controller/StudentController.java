package org.agiles.bolsaestudiantil.controller;

import lombok.RequiredArgsConstructor;
import org.agiles.bolsaestudiantil.dto.request.SubjectRequestDTO;
import org.agiles.bolsaestudiantil.dto.request.update.StudentUpdateRequestDTO;
import org.agiles.bolsaestudiantil.dto.response.StudentResponseDTO;
import org.agiles.bolsaestudiantil.dto.response.SubjectResponseDTO;
import org.agiles.bolsaestudiantil.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        StudentResponseDTO response = studentService.getStudentById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateRequestDTO request) {
        StudentResponseDTO response = studentService.updateStudent(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{studentId}/languages/{languageId}")
    public ResponseEntity<Void> deleteLanguageFromStudent(@PathVariable Long studentId, @PathVariable Long languageId) {
        studentService.deleteLanguageFromStudent(studentId, languageId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/subjects")
    public ResponseEntity<List<SubjectResponseDTO>> loadSubjects(@PathVariable Long id, @RequestBody List<SubjectRequestDTO> subjects) {
        List<SubjectResponseDTO> loadedSubjects = studentService.loadSubjects(id, subjects);
        return ResponseEntity.ok(loadedSubjects);
    }

    @PostMapping("/{id}/process-grades")
    public ResponseEntity<Map<String, Object>> processGradesFromExcel(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestHeader("Authorization") String authToken) {
        Map<String, Object> result = studentService.processGradesFromExcel(id, file, authToken);
        return ResponseEntity.ok(result);
    }
}