package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.classSubTopic.ClassSubTopicCreateDTO;
import com.holomentor.holomentor.dto.classSubTopic.ClassSubTopicDoneStateUpdateDTO;
import com.holomentor.holomentor.dto.classSubTopic.ClassSubTopicUpdateDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicCreateDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicUpdateDTO;
import com.holomentor.holomentor.services.ClassSubTopicService;
import com.holomentor.holomentor.services.ClassTopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subtopics")
public class ClassSubTopicController {

    @Autowired
    private ClassSubTopicService classSubTopicService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody ClassSubTopicCreateDTO body) {
        return classSubTopicService.create(body);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@Valid @PathVariable Long id) {
        return classSubTopicService.delete(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable Long id, @Valid @RequestBody ClassSubTopicUpdateDTO body){
        return classSubTopicService.update(id, body);
    }

   @PatchMapping("/update/state/{id}")
    public ResponseEntity<Object> updateDoneState(@Valid @PathVariable Long id, @Valid @RequestBody ClassSubTopicDoneStateUpdateDTO body){
        return classSubTopicService.updateDoneState(id, body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return classSubTopicService.get(id);
    }

    @GetMapping("/topic/{id}")
    public ResponseEntity<Object> getTopicSubTopics(@PathVariable Long topicId) {
        return classSubTopicService.getTopicSubTopics(topicId);
    }

}
