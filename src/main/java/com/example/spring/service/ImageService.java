package com.example.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.image.bucket:/Users/paul/Desktop/Java Разработчик/spring-starter/images}")
    private final String bucket;

    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        Path fullImagePath = Path.of(bucket, imagePath);

        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> load(String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);

        return Files.exists(fullImagePath) ? Optional.of(Files.readAllBytes(fullImagePath)) : Optional.empty();
    }
}