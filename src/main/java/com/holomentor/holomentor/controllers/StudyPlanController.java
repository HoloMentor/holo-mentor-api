package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.models.InstituteClassStudent;
import com.holomentor.holomentor.repositories.InstituteClassStudentRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("study-plan")
public class StudyPlanController {
    @Autowired
    private Environment environment;

    @Autowired
    private InstituteClassStudentRepository instituteClassStudentRepository;

    @PostMapping("/tiers/create")
    public ResponseEntity<Object> createTiers(@RequestBody Object body) {
        String url = String.format("%s/%s", environment.getProperty("env.holomentor.tiers_url"), "/upload");
        RestTemplate restTemplate = new RestTemplate();

        Object result = restTemplate.postForObject(url, body, Object.class);
        System.out.println(result);
        return Response.generate("student details are being processed, tiers will be allocated shortly", HttpStatus.OK);
    }

    @GetMapping("/csv/{classId}")
    public ResponseEntity<byte[]> getCSV(@Valid @PathVariable Long classId) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("student_class_id,marks");

        List<InstituteClassStudent> instituteClassStudentList = instituteClassStudentRepository.findByClassId(classId);
        for (InstituteClassStudent instituteClassStudent : instituteClassStudentList) {
            csvContent.append(instituteClassStudent.getId()).append(",").append("\n");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "Student Details.csv");

        byte[] csvBytes = csvContent.toString().getBytes();

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}
