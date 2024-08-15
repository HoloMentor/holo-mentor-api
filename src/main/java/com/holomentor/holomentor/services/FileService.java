package com.holomentor.holomentor.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class FileService {
    @Value("${file.path}")
    private String basePath;

    public String uploadFile(String fileName, MultipartFile multipartFile) {
        File dir = new File(basePath + fileName);

        if(dir.exists()){
            return "EXIST";
        }

        Path path = Path.of(basePath + fileName);

        try{
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return "CREATED";
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "FAILED";
    }

    public Resource downloadFile(String fileName) {
        File dir = new File(basePath + fileName);
        try{
            if(dir.exists()){
                Resource resource = new UrlResource(dir.toURI());
                return resource;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        return null;
    }
}
