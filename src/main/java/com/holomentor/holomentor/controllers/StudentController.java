package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.services.StudentService;
import com.holomentor.holomentor.services.StudentServices;
import com.holomentor.holomentor.services.StudyPlanService;
import com.holomentor.holomentor.dto.student.StudentCreateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentServices studentServices;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudyPlanService studyPlanService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody StudentCreateDTO body) throws IOException {
        return studentService.create(body);
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<Object> getInstituteStudentClasses(
            @PathVariable Long classId,
            @RequestParam(name = "page", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "limit", defaultValue = "10") Integer pageSize) {
        return studentService.findStudentsByClassId(classId, pageNo, pageSize);
    }

    // get enrolled classes of current student
    @GetMapping("/classes")
    public ResponseEntity<Object> getEnrolledClasses() {
        return studentServices.getEnrolledClasses(1L, "", 1, 10);
    }

    // get study plans when given student id and class id
    @GetMapping("/study-plans/{classId}/{studentId}")
    public ResponseEntity<Object> getStudyPlans(@PathVariable Long classId, @PathVariable Long studentId) {
        return studyPlanService.getStudyPlans(classId, studentId);
    }

    // add submission to study plan tasks
    // /study-plan/task/submit/${userId}/${classId}/${studyPlanId}/${taskId}
    // [{"id":"1733136529517","url":"http://localhost:8080/api/v1/files/1733136529492/A_Form_with_translations.pdf","name":"A_Form_with_translations.pdf"},{"id":"1733136532976","url":"http://localhost:8080/api/v1/files/1733136532916/A_Form_with_translations (1).pdf","name":"A_Form_with_translations (1).pdf"}]
    @PostMapping("/study-plans/task/submit/{userId}/{classId}/{studyPlanId}/{taskId}")
    public ResponseEntity<Object> submitStudyPlanTask(
            @PathVariable Long userId,
            @PathVariable Long classId,
            @PathVariable Long studyPlanId,
            @PathVariable Long taskId,
            @RequestBody String materials) {
        return studyPlanService.submitStudyPlanTask(userId, classId, studyPlanId, taskId, materials);
    }

    // students/study-plans/task/materials/${userId}/${classId}/${studyPlanId}/${taskId}
    @GetMapping("/study-plans/task/materials/{userId}/{classId}/{studyPlanId}/{taskId}")
    public ResponseEntity<Object> getStudyPlanTaskMaterials(
            @PathVariable Long userId,
            @PathVariable Long classId,
            @PathVariable Long studyPlanId,
            @PathVariable Long taskId) {
        return studyPlanService.getStudyPlanTaskMaterials(userId, classId, studyPlanId, taskId);
    }
}
