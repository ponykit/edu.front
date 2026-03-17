package com.edu.front.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileStorageService {

    @Value("${upload.path}")
    private String uploadPath;

    public Resource loadFileAsResource(String fileName) throws Exception {
        Path rootLocation = Paths.get(uploadPath).toAbsolutePath().normalize();
        Path filePath = rootLocation.resolve(fileName).normalize();

        // Path traversal 방지: 요청 경로가 루트 업로드 경로 내에 있는지 검증
        if (!filePath.startsWith(rootLocation)) {
            throw new SecurityException("Illegal file access attempt: " + fileName);
        }

        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new Exception("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new Exception("File not found: " + fileName);
        }
    }
}
