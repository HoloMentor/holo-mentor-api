package com.holomentor.holomentor.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class FileService {

    @Value("${file.path}")
    private String basePath;

    @Value("${env.holomentor.api_url}")
    private String apiUrl;

    @Value("${server.servlet.contextPath}")
    private String contextPath;


    public String uploadFile(String fileName, MultipartFile multipartFile) {
        try{
            long directoryName = System.currentTimeMillis();
            Files.createDirectories(Path.of(basePath + "/" + directoryName));

            String filePath = String.format("%s/%s", directoryName, fileName);
            Path path = Path.of(basePath + "/" + filePath);

            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return String.format("%s%s/%s/%s", apiUrl, contextPath, "files", filePath);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "FAILED";
    }

    public ResponseEntity<Resource> serveFile(String filePath, String fileName) {
        File dir = new File(String.format("%s/%s/%s", basePath, filePath, fileName));

        try {
            Resource resource = new UrlResource(dir.toURI());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found " + fileName, e);
        }
    }
}
