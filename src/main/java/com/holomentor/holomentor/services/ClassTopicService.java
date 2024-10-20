package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.classSubTopic.ClassSubTopicDTO;
import com.holomentor.holomentor.dto.classSubTopic.ClassSubTopicWithMaterialsDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicCreateDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicUpdateDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicWithSubTopicsDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicWithSubTopicsWithMaterialsDTO;
import com.holomentor.holomentor.dto.institute.InstituteCreateDTO;
import com.holomentor.holomentor.dto.institute.InstituteUpdateDTO;
import com.holomentor.holomentor.models.*;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassTopicsWithSubTopicsAndMaterialsProjection;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassTopicsWithSubTopicsProjection;
import com.holomentor.holomentor.repositories.InstituteClassSubTopicRepository;
import com.holomentor.holomentor.repositories.InstituteClassTopicRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class ClassTopicService {
    @Autowired
    private InstituteClassTopicRepository instituteClassTopicRepository;

    public ResponseEntity<Object> create(ClassTopicCreateDTO body) {
        InstituteClassTopic instituteClassTopic = new InstituteClassTopic();
        instituteClassTopic.setName(body.getName());
        instituteClassTopic.setInstituteId(body.getInstituteId());
        instituteClassTopic.setClassId(body.getClassId());

        instituteClassTopicRepository.save(instituteClassTopic);

        return Response.generate("created class topic", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> update(Long id, ClassTopicUpdateDTO body) {
        Optional<InstituteClassTopic> instituteClassTopicResult = instituteClassTopicRepository.findById(id);
        if (instituteClassTopicResult.isEmpty()) {
            return Response.generate("class topic not found", HttpStatus.NOT_FOUND);
        }
        InstituteClassTopic instituteClassTopic = instituteClassTopicResult.get();
        instituteClassTopic.setName(body.getName());

        instituteClassTopicRepository.save(instituteClassTopic);
        return Response.generate("class topic have been updated.", HttpStatus.OK);
    }

    public ResponseEntity<Object> delete(Long topicId) {
        Optional<InstituteClassTopic> instituteClassTopicResult = instituteClassTopicRepository.findById(topicId);
        if (instituteClassTopicResult.isEmpty()) {
            return Response.generate("class topic not found", HttpStatus.NOT_FOUND);
        }

        instituteClassTopicRepository.delete(instituteClassTopicResult.get());

        return Response.generate("class topic deleted", HttpStatus.OK);
    }

    public ResponseEntity<Object> get(Long topicId) {
        Optional<InstituteClassTopic> instituteClassTopicResult = instituteClassTopicRepository.findById(topicId);
        if (instituteClassTopicResult.isEmpty()) {
            return Response.generate("class topic not found", HttpStatus.NOT_FOUND);
        }

        return Response.generate("class topic deleted", HttpStatus.OK, instituteClassTopicResult.get());
    }

    public ResponseEntity<Object> getClassTopics(Long classId, Boolean withMaterials) {
        if(withMaterials) {
            List<InstituteClassTopicsWithSubTopicsProjection> instituteClassTopics = instituteClassTopicRepository.findAllClassTopicsWithSubTopicsAndMaterials(classId);
            Collection<List<InstituteClassTopicsWithSubTopicsProjection>> groupedByTopic = instituteClassTopics.stream().collect(Collectors.groupingBy(InstituteClassTopicsWithSubTopicsProjection::getId)).values();

            List<ClassTopicWithSubTopicsWithMaterialsDTO> data = groupedByTopic.stream().map(topicData -> {
                ClassTopicWithSubTopicsWithMaterialsDTO classTopicWithSubTopicsWithMaterials = new ClassTopicWithSubTopicsWithMaterialsDTO();
//                group the subtopics
                Collection<List<InstituteClassTopicsWithSubTopicsProjection>> subTopics =  topicData.stream().filter(record -> record.getSubTopicId() != null).collect(Collectors.groupingBy(InstituteClassTopicsWithSubTopicsProjection::getSubTopicId)).values();
//                group the materials in each sub topic to "materials"
                List<ClassSubTopicWithMaterialsDTO> classSubTopicsWithMaterials = subTopics.stream().map(subTopicData -> {
                    List<InstituteClassMaterial> instituteClassMaterials = subTopicData.stream().map(subTopicMaterial -> {
                        InstituteClassMaterial instituteClassMaterial = new InstituteClassMaterial();
                        instituteClassMaterial.setId(subTopicMaterial.getMaterialId());
                        instituteClassMaterial.setType(subTopicMaterial.getMaterialType());
                        instituteClassMaterial.setUrl(subTopicMaterial.getMaterialUrl());

                        return instituteClassMaterial;
                    }).collect(Collectors.toList());

                    ClassSubTopicWithMaterialsDTO classSubTopicWithMaterials = new ClassSubTopicWithMaterialsDTO();
                    classSubTopicWithMaterials.setId(subTopicData.get(0).getId());
                    classSubTopicWithMaterials.setName(subTopicData.get(0).getName());
                    classSubTopicWithMaterials.setMaterials(instituteClassMaterials);

                    return classSubTopicWithMaterials;
                }).collect(Collectors.toList());

//                System.out.println(record);
                classTopicWithSubTopicsWithMaterials.setId(topicData.get(0).getId());
                classTopicWithSubTopicsWithMaterials.setInstituteId(topicData.get(0).getInstituteId());
                classTopicWithSubTopicsWithMaterials.setClassId(topicData.get(0).getClassId());
                classTopicWithSubTopicsWithMaterials.setName(topicData.get(0).getName());
                classTopicWithSubTopicsWithMaterials.setSubTopics(classSubTopicsWithMaterials);

                return classTopicWithSubTopicsWithMaterials;
            }).collect(Collectors.toList());

            return Response.generate("class topics", HttpStatus.OK, data);
        } else {
            List<InstituteClassTopicsWithSubTopicsProjection> instituteClassTopics = instituteClassTopicRepository.findAllClassTopicsWithSubTopics(classId);
            return Response.generate("class topics", HttpStatus.OK, instituteClassTopics);
        }
    }

}
