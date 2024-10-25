package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.classMaterials.ClassMaterialCreateDTO;
import com.holomentor.holomentor.dto.classMaterials.ClassMaterialUpdateDTO;
import com.holomentor.holomentor.models.InstituteClassMaterial;
import com.holomentor.holomentor.models.InstituteClassTopic;
import com.holomentor.holomentor.repositories.InstituteClassMaterialRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class ClassMaterialService {
    @Autowired
    private InstituteClassMaterialRepository instituteClassMaterialRepository;

    public ResponseEntity<Object> create(ClassMaterialCreateDTO body) {
        InstituteClassMaterial instituteClassMaterial = new InstituteClassMaterial();
        instituteClassMaterial.setClassId(body.getClassId());
        instituteClassMaterial.setInstituteId(body.getInstituteId());
        instituteClassMaterial.setTopicId(body.getTopicId());
        instituteClassMaterial.setSubTopicId(body.getSubTopicId());
        instituteClassMaterial.setType(body.getType());
        instituteClassMaterial.setUrl(body.getUrl());

        instituteClassMaterialRepository.save(instituteClassMaterial);

        return Response.generate("class materials are stored", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> update(Long id, ClassMaterialUpdateDTO body) {
        Optional<InstituteClassMaterial> instituteClassMaterialResult = instituteClassMaterialRepository.findById(id);
        if(instituteClassMaterialResult.isEmpty()) {
            return Response.generate("class material details are not found", HttpStatus.NOT_FOUND);
        }

        InstituteClassMaterial instituteClassMaterial = instituteClassMaterialResult.get();
        instituteClassMaterial.setTopicId(body.getTopicId());
        instituteClassMaterial.setSubTopicId(body.getSubTopicId());
        instituteClassMaterial.setType(body.getType());
        instituteClassMaterial.setUrl(body.getUrl());

        instituteClassMaterialRepository.save(instituteClassMaterial);

        return Response.generate("class materials details updated", HttpStatus.OK);
    }

    public ResponseEntity<Object> delete(Long materialId) {
        Optional<InstituteClassMaterial> instituteClassMaterialResult = instituteClassMaterialRepository.findById(materialId);
        if (instituteClassMaterialResult.isEmpty()) {
            return Response.generate("class material not found", HttpStatus.NOT_FOUND);
        }

        instituteClassMaterialRepository.delete(instituteClassMaterialResult.get());

        return Response.generate("class material deleted", HttpStatus.OK);
    }
}
