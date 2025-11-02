package org.agiles.bolsaestudiantil.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
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
        
        MinioClient client = getMinioClient();
        ensureBucketExists(client, "image");
        
        client.putObject(
                PutObjectArgs.builder()
                        .bucket("image")
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        
        log.info("Uploaded image: {}", fileName);
        return minioUrl + "/image/" + fileName;
    }

    public String uploadCurriculum(MultipartFile file) throws Exception {
        validatePdfFile(file);
        String fileName = sanitizeFileName(file.getOriginalFilename());
        
        MinioClient client = getMinioClient();
        ensureBucketExists(client, "curriculum");
        
        client.putObject(
                PutObjectArgs.builder()
                        .bucket("curriculum")
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType("application/pdf")
                        .build()
        );
        
        log.info("Uploaded CV: {}", fileName);
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
                    log.info("Deleted file: {}", fileName);
                }
            }
        } catch (Exception e) {
            log.error("Error deleting file: {}", fileUrl, e);
        }
    }

    private void ensureBucketExists(MinioClient client, String bucketName) throws Exception {
        boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            log.info("Created bucket: {}", bucketName);
        }
    }
}