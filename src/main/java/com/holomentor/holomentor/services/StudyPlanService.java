package com.holomentor.holomentor.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holomentor.holomentor.dto.classes.ClassCreateDTO;
import com.holomentor.holomentor.dto.classes.ClassUpdateDTO;
import com.holomentor.holomentor.dto.studyPlan.StudyPlanCreateDTO;
import com.holomentor.holomentor.dto.studyPlan.StudyPlanUpdateDTO;
import com.holomentor.holomentor.models.*;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassProjection;
import com.holomentor.holomentor.repositories.*;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudyPlanService {

    @Autowired
    private Environment environment;

    @Autowired
    private InstituteClassStudentRepository instituteClassStudentRepository;

    @Autowired
    private ClassTierStudyPlanRepository classTierStudyPlanRepository;

    @Autowired
    private ClassTierStudyPlanTaskRepository classTierStudyPlanTaskRepository;

    @Autowired
    private StudyPlanTaskSubmissionRepository studyPlanTaskSubmissionRepository;

    public ResponseEntity<Object> createTiers(Integer classId, Integer marksOutOf, MultipartFile file)
            throws IOException {
        String url = String.format("%s/%s", environment.getProperty("env.holomentor.tiers_url"), "/upload");

        // prepare the header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        // prepare the body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("class_id", classId);
        body.add("marks_out_of", marksOutOf);
        body.add("file", fileAsResource);

        // prepare the request
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // send the request
        RestTemplate restTemplate = new RestTemplate();
        Object result = restTemplate.postForEntity(url, requestEntity, Object.class);
        System.out.println(result);
        return Response.generate("student details are being processed, tiers will be allocated shortly", HttpStatus.OK);
    }

    public ResponseEntity<byte[]> getCSV(Long classId) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("student_class_id,marks").append("\n");

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

    public ResponseEntity<Object> create(StudyPlanCreateDTO body) {
        ClassTierStudyPlan classTierStudyPlan = new ClassTierStudyPlan();

        classTierStudyPlan.setInstituteId(body.getInstituteId());
        classTierStudyPlan.setClassId(body.getClassId());
        classTierStudyPlan.setTier(body.getTier());
        classTierStudyPlan.setName(body.getName());
        classTierStudyPlan.setDescription(body.getDescription());

        classTierStudyPlanRepository.save(classTierStudyPlan);

        List<ClassTierStudyPlanTask> classTierStudyPlanTasks = body.getTasks().stream().map(studyPlan -> {
            ClassTierStudyPlanTask task = new ClassTierStudyPlanTask();

            task.setClassId(body.getClassId());
            task.setInstituteId(body.getInstituteId());
            task.setStudyPlanId(classTierStudyPlan.getId());
            task.setTitle(studyPlan.getTitle());
            task.setDescription(studyPlan.getDescription());

            return task;
        }).collect(Collectors.toList());
        classTierStudyPlanTaskRepository.saveAll(classTierStudyPlanTasks);

        return Response.generate("class study tier plan created", HttpStatus.OK);
    }

    public ResponseEntity<Object> getTierPlans(Long classId, Integer tier) {
        List<ClassTierStudyPlan> studyPlans = classTierStudyPlanRepository.findByClassIdAndTierWithTasks(classId, tier);

        return Response.generate("class tier study plans", HttpStatus.OK, studyPlans);
    }

    public ResponseEntity<Object> delete(Long studyPlanId) {
        Optional<ClassTierStudyPlan> classTierStudyPlan = classTierStudyPlanRepository.findById(studyPlanId);
        if (classTierStudyPlan.isEmpty()) {
            return Response.generate("class tier study plans not found", HttpStatus.NOT_FOUND);
        }

        classTierStudyPlanRepository.delete(classTierStudyPlan.get());

        return Response.generate("class tier study plans details deleted", HttpStatus.OK);
    }

    public ResponseEntity<Object> update(Long id, StudyPlanUpdateDTO body) {
        Optional<ClassTierStudyPlan> studyPlanResult = classTierStudyPlanRepository.findById(id);
        if (studyPlanResult.isEmpty()) {
            return Response.generate("class tier study plan not found", HttpStatus.NOT_FOUND);
        }
        ClassTierStudyPlan studyPlan = studyPlanResult.get();
        studyPlan.setName(body.getName());
        studyPlan.setDescription(body.getDescription());

        classTierStudyPlanRepository.save(studyPlan);

        return Response.generate("class tier study plan details have been updated.", HttpStatus.OK);
    }

    // getStudyPlans(classId, studentId);
    public ResponseEntity<Object> getStudyPlans(Long classId, Long studentId) {

        // get student tier from InstituteClassStudents table
        InstituteClassStudent instituteClassStudent = instituteClassStudentRepository.findByClassIdAndStudentId(classId,
                studentId);
        if (instituteClassStudent == null)
            return Response.generate("student not found in the class", HttpStatus.NOT_FOUND);

        Integer tier = instituteClassStudent.getTier();

        // get study plans for the student's tier
        List<ClassTierStudyPlan> studyPlans = classTierStudyPlanRepository.findByClassIdAndTierWithTasks(classId, tier);
        return Response.generate("study plans for the student", HttpStatus.OK, studyPlans);
    }

    // // add submission to study plan tasks
    // // /study-plan/task/submit/${userId}/${classId}/${studyPlanId}/${taskId}
    // // @Valid @RequestBody StudentCreateDTO body

    // //
    // [{"id":"1733136529517","url":"http://localhost:8080/api/v1/files/1733136529492/A_Form_with_translations.pdf","name":"A_Form_with_translations.pdf"},{"id":"1733136532976","url":"http://localhost:8080/api/v1/files/1733136532916/A_Form_with_translations
    // (1).pdf","name":"A_Form_with_translations (1).pdf"}]

    // table for study plan task submission
    // CREATE TABLE IF NOT EXISTS "study_plan_task_submissions" (
    // id serial primary key,
    // task_id serial constraint fk_class_tier_study_plan_task references
    // class_tier_study_plan_tasks,
    // student_id serial constraint fk_users references users,
    // institute_id serial constraint fk_institute references institutes,
    // class_id serial constraint fk_institute_class references institute_classes,
    // submission_date timestamp null,
    // submission json not null,
    // created_at timestamp default CURRENT_TIMESTAMP
    // );

    public ResponseEntity<Object> submitStudyPlanTask(Long userId, Long classId, Long studyPlanId, Long taskId,
            String materials) {
        // get the task
        Optional<ClassTierStudyPlanTask> taskResult = classTierStudyPlanTaskRepository.findById(taskId);
        if (taskResult.isEmpty()) {
            return Response.generate("task not found", HttpStatus.NOT_FOUND);
        }
        // ClassTierStudyPlanTask task = taskResult.get();

        // get the student
        InstituteClassStudent instituteClassStudent = instituteClassStudentRepository.findByClassIdAndStudentId(classId,
                userId);
        if (instituteClassStudent == null)
            return Response.generate("student not found in the class", HttpStatus.NOT_FOUND);

        // get the study plan
        Optional<ClassTierStudyPlan> studyPlanResult = classTierStudyPlanRepository.findById(studyPlanId);
        if (studyPlanResult.isEmpty()) {
            return Response.generate("study plan not found", HttpStatus.NOT_FOUND);
        }
        // ClassTierStudyPlan studyPlan = studyPlanResult.get();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode materialsJson = mapper.readTree(materials);

            // check if submission already exists
            StudyPlanTaskSubmission existingSubmission = studyPlanTaskSubmissionRepository
                    .findByTaskIdAndStudentId(taskId, userId);

            if (existingSubmission != null) {
                // update the submission
                existingSubmission.setSubmission(materialsJson);
                studyPlanTaskSubmissionRepository.save(existingSubmission);
                return Response.generate("study plan task submission updated", HttpStatus.OK);
            }

            StudyPlanTaskSubmission submission = new StudyPlanTaskSubmission();
            submission.setTaskId(taskId);
            submission.setStudentId(userId);
            submission.setInstituteId(instituteClassStudent.getInstituteId());
            submission.setClassId(classId);
            submission.setSubmission(materialsJson);

            studyPlanTaskSubmissionRepository.save(submission);

            return Response.generate("study plan task submitted", HttpStatus.OK);
        } catch (Exception e) {
            return Response.generate("Error processing submission: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<Object> getStudyPlanTaskMaterials(Long userId, Long classId, Long studyPlanId, Long taskId) {
        // get the task
        Optional<ClassTierStudyPlanTask> taskResult = classTierStudyPlanTaskRepository.findById(taskId);
        if (taskResult.isEmpty())
            return Response.generate("task not found", HttpStatus.NOT_FOUND);
        // ClassTierStudyPlanTask task = taskResult.get();

        // get student
        InstituteClassStudent instituteClassStudent = instituteClassStudentRepository.findByClassIdAndStudentId(classId,
                userId);
        if (instituteClassStudent == null)
            return Response.generate("student not found in the class", HttpStatus.NOT_FOUND);

        // get study plan
        Optional<ClassTierStudyPlan> studyPlanResult = classTierStudyPlanRepository.findById(studyPlanId);
        if (studyPlanResult.isEmpty())
            return Response.generate("study plan not found", HttpStatus.NOT_FOUND);
        // ClassTierStudyPlan studyPlan = studyPlanResult.get();

        // get submission
        StudyPlanTaskSubmission submission = studyPlanTaskSubmissionRepository.findByTaskIdAndStudentId(taskId, userId);
        if (submission == null)
            return Response.generate("submission not found", HttpStatus.NOT_FOUND);

        return Response.generate("study plan task submission", HttpStatus.OK, submission);
    }

}