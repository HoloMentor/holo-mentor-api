package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.classMaterials.ClassSubTopicMaterialDTO;
import com.holomentor.holomentor.dto.classSubTopic.ClassSubTopicDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicCreateDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicUpdateDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicWithSubTopicsDTO;
import com.holomentor.holomentor.models.*;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassTopicsWithSubTopicsProjection;
import com.holomentor.holomentor.repositories.InstituteClassMaterialRepository;
import com.holomentor.holomentor.repositories.InstituteClassSubTopicRepository;
import com.holomentor.holomentor.repositories.InstituteClassTopicRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class ClassTopicService {
    @Autowired
    private InstituteClassTopicRepository instituteClassTopicRepository;
    @Autowired
    private InstituteClassMaterialRepository instituteClassMaterialRepository;
    @Autowired
    private InstituteClassSubTopicRepository instituteClassSubTopicRepository;

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

        instituteClassMaterialRepository.deleteByTopicId(topicId);
        instituteClassSubTopicRepository.deleteByTopicId(topicId);
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
            List<ClassTopicWithSubTopicsDTO> data = formatClassSubTopicMaterialsStructure(instituteClassTopics, true);

            return Response.generate("class topics", HttpStatus.OK, data);
        } else {
            List<InstituteClassTopicsWithSubTopicsProjection> instituteClassTopics = instituteClassTopicRepository.findAllClassTopicsWithSubTopics(classId);
            List<ClassTopicWithSubTopicsDTO> data = formatClassSubTopicMaterialsStructure(instituteClassTopics, false);

            return Response.generate("class topics", HttpStatus.OK, data);
        }
    }
//    format the nested structure of topics -> subtopics -> materials
    private List<ClassTopicWithSubTopicsDTO> formatClassSubTopicMaterialsStructure(List<InstituteClassTopicsWithSubTopicsProjection> instituteClassTopics, Boolean withMaterials) {
        Collection<List<InstituteClassTopicsWithSubTopicsProjection>> groupedByTopic = instituteClassTopics.stream().collect(Collectors.groupingBy(InstituteClassTopicsWithSubTopicsProjection::getId)).values();
        List<ClassTopicWithSubTopicsDTO> classTopicsWithSubTopics = groupedByTopic.stream().map(topicData -> getClassTopic(topicData, withMaterials)).collect(Collectors.toList());

        return classTopicsWithSubTopics;
    }

//    group the topic's sub topics
    private ClassTopicWithSubTopicsDTO getClassTopic(List<InstituteClassTopicsWithSubTopicsProjection> topicData, Boolean withMaterials) {
        ClassTopicWithSubTopicsDTO classTopicWithSubTopics = new ClassTopicWithSubTopicsDTO();
        List<ClassSubTopicDTO> classSubTopics = getClassTopicSubTopics(topicData, withMaterials);

        classTopicWithSubTopics.setId(topicData.get(0).getId());
        classTopicWithSubTopics.setInstituteId(topicData.get(0).getInstituteId());
        classTopicWithSubTopics.setClassId(topicData.get(0).getClassId());
        classTopicWithSubTopics.setName(topicData.get(0).getName());
        classTopicWithSubTopics.setSubTopics(classSubTopics);

        return classTopicWithSubTopics;
    }

//    extract the subtopics related to a topic
    private List<ClassSubTopicDTO> getClassTopicSubTopics(List<InstituteClassTopicsWithSubTopicsProjection> topicData, Boolean withMaterials) {
        Collection<List<InstituteClassTopicsWithSubTopicsProjection>> subTopics =  topicData.stream().filter(record -> record.getSubTopicId() != null).collect(Collectors.groupingBy(InstituteClassTopicsWithSubTopicsProjection::getSubTopicId)).values();

        List<ClassSubTopicDTO> classSubTopics = subTopics.stream().map(subTopicData -> {
            ClassSubTopicDTO classSubTopic = new ClassSubTopicDTO();
            classSubTopic.setId(subTopicData.get(0).getSubTopicId());
            classSubTopic.setName(subTopicData.get(0).getSubTopicName());

            if(withMaterials) {
                List<ClassSubTopicMaterialDTO> classSubTopicMaterials = getClassSubTopicMaterials(subTopicData);
                classSubTopic.setMaterials(classSubTopicMaterials);
            }

            return classSubTopic;
        }).collect(Collectors.toList());

        return classSubTopics;
    }

//    extract the materials related to a sub topic
    private List<ClassSubTopicMaterialDTO> getClassSubTopicMaterials(List<InstituteClassTopicsWithSubTopicsProjection> subTopicData) {
        List<ClassSubTopicMaterialDTO> classSubTopicMaterials = subTopicData.stream().filter(record -> record.getMaterialId() != null).map(subTopicMaterial -> {
            ClassSubTopicMaterialDTO classSubTopicMaterial = new ClassSubTopicMaterialDTO();
            classSubTopicMaterial.setId(subTopicMaterial.getMaterialId());
            classSubTopicMaterial.setType(subTopicMaterial.getMaterialType());
            classSubTopicMaterial.setUrl(subTopicMaterial.getMaterialUrl());

            return classSubTopicMaterial;
        }).collect(Collectors.toList());

        return classSubTopicMaterials;
    }
}
