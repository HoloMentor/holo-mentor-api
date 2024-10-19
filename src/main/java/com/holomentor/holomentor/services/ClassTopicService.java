package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.classTopic.ClassTopicCreateDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicUpdateDTO;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
            List<InstituteClassTopicsWithSubTopicsAndMaterialsProjection> instituteClassTopics = instituteClassTopicRepository.findAllClassTopicsWithSubTopicsAndMaterials(classId);
            return Response.generate("class topics", HttpStatus.OK, instituteClassTopics);
        } else {
            List<InstituteClassTopicsWithSubTopicsProjection> instituteClassTopics = instituteClassTopicRepository.findAllClassTopicsWithSubTopics(classId);
            return Response.generate("class topics", HttpStatus.OK, instituteClassTopics);
        }

    }
}
