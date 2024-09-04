package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.services.FileService;
import com.holomentor.holomentor.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/{folder}/{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> serveFile(@PathVariable(value = "folder") String filePath, @PathVariable(value = "name") String fileName){
        return fileService.serveFile(filePath, fileName);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam String fileName, @RequestParam(name = "file") MultipartFile file){
        String status = fileService.uploadFile(fileName, file);

        if(status.equals("FAILED")) {
            return Response.generate("failed to upload file", HttpStatus.EXPECTATION_FAILED);
        }

        Map<String, String> data = new HashMap<>();
        data.put("url", status);

        return Response.generate("file uploaded", HttpStatus.CREATED, data);
    }
}