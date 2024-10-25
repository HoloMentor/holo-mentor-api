package com.holomentor.holomentor.controllers;

import com.holomentor.holomentor.dto.classMaterials.ClassMaterialCreateDTO;
import com.holomentor.holomentor.dto.classMaterials.ClassMaterialUpdateDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicCreateDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicUpdateDTO;
import com.holomentor.holomentor.services.ClassMaterialService;
import com.holomentor.holomentor.services.ClassTopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/materials")
public class ClassMaterialController {

    @Autowired
    private ClassMaterialService classMaterialService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody ClassMaterialCreateDTO body) {
        return classMaterialService.create(body);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@Valid @PathVariable Long id) {
        return classMaterialService.delete(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable Long id, @Valid @RequestBody ClassMaterialUpdateDTO body){
        return classMaterialService.update(id, body);
    }

}
