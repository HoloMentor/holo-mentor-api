package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.teacher.TeacherCreateDTO;
import com.holomentor.holomentor.dto.teacher.TeacherUpdateDTO;
import com.holomentor.holomentor.services.TeacherServices;
import com.holomentor.holomentor.services.StudentServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private TeacherServices teacherServices;

    @Autowired
    private StudentServices studentServices;

    //    test
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    //    get enrolled classes of current student
    @GetMapping("/classes")
    public ResponseEntity<Object> getEnrolledClasses() {
        return studentServices.getEnrolledClasses(1L, "", 1, 10);
    }

//    get class topics when given class id
//    @GetMapping("/class/{id}/topics")
//    public ResponseEntity<Object> getClassTopics(@PathVariable Long id) {
//        return studentServices.getClassTopics(id);
//    }

//    @PostMapping("/create")
//    public ResponseEntity<Object> create(@Valid @RequestBody TeacherCreateDTO body) throws IOException {
//        return teacherServices.create(body);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Object> delete(@PathVariable Long id) {
//        return teacherServices.delete(id);
//    }
//
//    @PatchMapping("/update/{id}")
//    public ResponseEntity<Object> update(@Valid @PathVariable Long id, @Valid @RequestBody TeacherUpdateDTO body) {
//        return teacherServices.update(id, body);
//    }
//
//    @GetMapping("/fetch/{id}")
//    public ResponseEntity<Object> getTeacher(@Valid @PathVariable Long id) {
//        return teacherServices.getTeacherById(id);
//    }
//
//    @GetMapping("/stats/{id}")
//    public ResponseEntity<Object> getTeacherStats(@Valid @PathVariable Long id) {
//        return teacherServices.getTeacherStats(id);
//    }
//
//    @GetMapping("/institute/classes/{id}")
//    public ResponseEntity<Object> getInstituteTeacherClasses(@PathVariable Long id,
//                                                    @RequestParam(name="page", defaultValue = "1") Integer pageNo,
//                                                    @RequestParam(name="limit", defaultValue = "10") Integer pageSize) {
//        return teacherServices.getInstituteTeacherClasses(id, pageNo, pageSize);
//    }
//
//    @GetMapping("/institute/{id}")
//    public ResponseEntity<Object> institute(
//            @PathVariable Long id,
//            @RequestParam(name="search", defaultValue = "") String search,
//            @RequestParam(name="page", defaultValue = "1") Integer pageNo,
//            @RequestParam(name="limit", defaultValue = "10") Integer pageSize) {
//        return teacherServices.getTeachersByInstituteId(id, search, pageNo, pageSize);
//    }

}
