package org.agiles.bolsaestudiantil.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {

    @Value("${minio.url:https://api-minio.lafuah.com}")
    private String minioUrl;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    private MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

    public String uploadProfileImage(MultipartFile file) throws Exception {
        validateImageFile(file);
        String fileName = sanitizeFileName(file.getOriginalFilename());
        
        getMinioClient().putObject(
                PutObjectArgs.builder()
                        .bucket("image")
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        
        return minioUrl + "/image/" + fileName;
    }

    public String uploadCurriculum(MultipartFile file) throws Exception {
        validatePdfFile(file);
        String fileName = sanitizeFileName(file.getOriginalFilename());
        
        getMinioClient().putObject(
                PutObjectArgs.builder()
                        .bucket("curriculum")
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType("application/pdf")
                        .build()
        );
        
        return minioUrl + "/curriculum/" + fileName;
    }

    private void validateImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }
    }

    private void validatePdfFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (!"application/pdf".equals(contentType)) {
            throw new IllegalArgumentException("File must be a PDF");
        }
    }

    private String sanitizeFileName(String originalFilename) {
        if (originalFilename == null) {
            return UUID.randomUUID().toString();
        }
        
        String extension = "";
        int lastDot = originalFilename.lastIndexOf('.');
        if (lastDot > 0) {
            extension = originalFilename.substring(lastDot);
        }
        
        return UUID.randomUUID() + extension;
    }

    public void deleteFile(String fileUrl) {
        try {
            if (fileUrl != null && fileUrl.startsWith(minioUrl)) {
                String[] parts = fileUrl.replace(minioUrl + "/", "").split("/", 2);
                if (parts.length == 2) {
                    String bucket = parts[0];
                    String fileName = parts[1];
                    getMinioClient().removeObject(
                            RemoveObjectArgs.builder()
                                    .bucket(bucket)
                                    .object(fileName)
                                    .build()
                    );
                }
            }
        } catch (Exception e) {
            // Log error but don't fail the operation
        }
    }
}