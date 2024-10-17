package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.classTopic.ClassTopicCreateDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicUpdateDTO;
import com.holomentor.holomentor.dto.classes.ClassCreateDTO;
import com.holomentor.holomentor.dto.classes.ClassUpdateDTO;
import com.holomentor.holomentor.services.ClassService;
import com.holomentor.holomentor.services.ClassTopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public class ClassTopicController {

    @Autowired
    private ClassTopicService classTopicService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody ClassTopicCreateDTO body) {
        return classTopicService.create(body);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@Valid @PathVariable Long id) {
        return classTopicService.delete(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable Long id, @Valid @RequestBody ClassTopicUpdateDTO body){
        return classTopicService.update(id, body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return classTopicService.get(id);
    }

    @GetMapping("/class/{id}")
    public ResponseEntity<Object> getClassTopics(@Valid @PathVariable Long id) {
        return classTopicService.getClassTopics(id);
    }

}
