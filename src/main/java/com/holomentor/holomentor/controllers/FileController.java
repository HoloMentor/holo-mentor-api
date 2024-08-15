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

@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> downloadFile(@PathVariable(value = "name") String fileName){
        Resource file = fileService.downloadFile(fileName);

        if(file == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(file);
        }

    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam String fileName, @RequestParam(name = "file") MultipartFile file){
        String status = fileService.uploadFile(fileName, file);
        System.out.println(fileName);
        return "CREATED".equals(status) ? Response.generate("file uploaded", HttpStatus.CREATED) : ("EXIST".equals(status) ? Response.generate("upload directory not found", HttpStatus.NOT_MODIFIED) : Response.generate("failed to upload file", HttpStatus.EXPECTATION_FAILED));
    }
}