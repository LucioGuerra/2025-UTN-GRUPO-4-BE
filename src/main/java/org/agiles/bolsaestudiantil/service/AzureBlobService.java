package org.agiles.bolsaestudiantil.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AzureBlobService {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container.image}")
    private String imageContainer;

    @Value("${azure.storage.container.curriculum}")
    private String curriculumContainer;

    private BlobServiceClient getBlobServiceClient() {
        return new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    public String uploadProfileImage(MultipartFile file) throws Exception {
        validateImageFile(file);
        String fileName = sanitizeFileName(file.getOriginalFilename());
        
        BlobServiceClient client = getBlobServiceClient();
        BlobContainerClient containerClient = ensureContainerExists(client, imageContainer);
        
        BlobClient blobClient = containerClient.getBlobClient(fileName);
        blobClient.upload(file.getInputStream(), file.getSize(), true);
        
        log.info("Uploaded image: {}", fileName);
        return blobClient.getBlobUrl();
    }

    public String uploadCurriculum(MultipartFile file) throws Exception {
        log.info("Starting curriculum upload. File: {}, Size: {}, ContentType: {}", 
                file.getOriginalFilename(), file.getSize(), file.getContentType());
        
        validatePdfFile(file);
        String fileName = sanitizeFileName(file.getOriginalFilename());
        log.info("Generated filename: {}", fileName);
        
        BlobServiceClient client = getBlobServiceClient();
        log.info("Azure Blob client created successfully");
        
        BlobContainerClient containerClient = ensureContainerExists(client, curriculumContainer);
        log.info("Container '{}' verified/created", curriculumContainer);
        
        BlobClient blobClient = containerClient.getBlobClient(fileName);
        blobClient.upload(file.getInputStream(), file.getSize(), true);
        
        String finalUrl = blobClient.getBlobUrl();
        log.info("CV uploaded successfully. Final URL: {}", finalUrl);
        return finalUrl;
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
            if (fileUrl != null && fileUrl.contains("blob.core.windows.net")) {
                BlobServiceClient client = getBlobServiceClient();
                
                // Extract container and blob name from URL
                String[] urlParts = fileUrl.split("/");
                if (urlParts.length >= 2) {
                    String containerName = urlParts[urlParts.length - 2];
                    String blobName = urlParts[urlParts.length - 1];
                    
                    BlobContainerClient containerClient = client.getBlobContainerClient(containerName);
                    BlobClient blobClient = containerClient.getBlobClient(blobName);
                    blobClient.delete();
                    
                    log.info("Deleted file: {}", blobName);
                }
            }
        } catch (Exception e) {
            log.error("Error deleting file: {}", fileUrl, e);
        }
    }

    private BlobContainerClient ensureContainerExists(BlobServiceClient client, String containerName) {
        log.info("Checking if container '{}' exists...", containerName);
        BlobContainerClient containerClient = client.getBlobContainerClient(containerName);
        
        if (!containerClient.exists()) {
            log.info("Creating container: {}", containerName);
            containerClient = client.createBlobContainer(containerName);
            log.info("Container '{}' created successfully", containerName);
        }
        
        return containerClient;
    }
}