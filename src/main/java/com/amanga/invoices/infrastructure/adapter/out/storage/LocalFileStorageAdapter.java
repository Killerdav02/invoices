package com.amanga.invoices.infrastructure.adapter.out.storage;

import com.amanga.invoices.application.port.out.FileStoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
@ConditionalOnProperty(name = "app.storage.provider", havingValue = "LOCAL", matchIfMissing = true)
public class LocalFileStorageAdapter implements FileStoragePort {

    private final Path baseDir;

    public LocalFileStorageAdapter(
            @Value("${app.storage.local.directory:./uploads}") String baseDirectory) {
        this.baseDir = Paths.get(baseDirectory).toAbsolutePath().normalize();
    }

    @Override
    public String upload(byte[] content, String fileName, String contentType, String bucket) {
        try {
            Path bucketDir = baseDir.resolve(bucket);
            Files.createDirectories(bucketDir);
            String storedName = UUID.randomUUID() + "_" + fileName;
            Path target = bucketDir.resolve(storedName);
            Files.write(target, content);
            return bucket + "/" + storedName;
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to store file locally", e);
        }
    }

    @Override
    public byte[] download(String filePath, String bucket) {
        try {
            Path target = baseDir.resolve(filePath);
            return Files.readAllBytes(target);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read file: " + filePath, e);
        }
    }

    @Override
    public void delete(String filePath, String bucket) {
        try {
            Path target = baseDir.resolve(filePath);
            Files.deleteIfExists(target);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to delete file: " + filePath, e);
        }
    }

    @Override
    public String generatePresignedUrl(String filePath, String bucket, int expirationMinutes) {
        return "/files/" + filePath;
    }
}
