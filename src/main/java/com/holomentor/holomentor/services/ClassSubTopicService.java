package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.classSubTopic.ClassSubTopicCreateDTO;
import com.holomentor.holomentor.dto.classSubTopic.ClassSubTopicUpdateDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicCreateDTO;
import com.holomentor.holomentor.dto.classTopic.ClassTopicUpdateDTO;
import com.holomentor.holomentor.models.InstituteClassSubTopic;
import com.holomentor.holomentor.repositories.InstituteClassMaterialRepository;
import com.holomentor.holomentor.repositories.InstituteClassSubTopicRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ClassSubTopicService {
    @Autowired
    private InstituteClassSubTopicRepository instituteClassSubTopicRepository;
    @Autowired
    private InstituteClassMaterialRepository instituteClassMaterialRepository;

    public ResponseEntity<Object> create(ClassSubTopicCreateDTO body) {
        InstituteClassSubTopic instituteClassSubTopic = new InstituteClassSubTopic();
        instituteClassSubTopic.setName(body.getName());
        instituteClassSubTopic.setTopicId(body.getTopicId());
        instituteClassSubTopic.setInstituteId(body.getInstituteId());
        instituteClassSubTopic.setClassId(body.getClassId());

        instituteClassSubTopicRepository.save(instituteClassSubTopic);

        return Response.generate("created sub class topic", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> update(Long id, ClassSubTopicUpdateDTO body) {
        Optional<InstituteClassSubTopic> instituteClassSubTopicResult = instituteClassSubTopicRepository.findById(id);
        if (instituteClassSubTopicResult.isEmpty()) {
            return Response.generate("class sub topic not found", HttpStatus.NOT_FOUND);
        }
        InstituteClassSubTopic instituteClassSubTopic = instituteClassSubTopicResult.get();
        instituteClassSubTopic.setName(body.getName());

        instituteClassSubTopicRepository.save(instituteClassSubTopic);
        return Response.generate("class sub topic have been updated.", HttpStatus.OK);
    }

    public ResponseEntity<Object> delete(Long subTopicId) {
        Optional<InstituteClassSubTopic> instituteClassSubTopicResult = instituteClassSubTopicRepository.findById(subTopicId);
        if (instituteClassSubTopicResult.isEmpty()) {
            return Response.generate("class sub topic not found", HttpStatus.NOT_FOUND);
        }

        instituteClassMaterialRepository.deleteBySubTopicId(subTopicId);
        instituteClassSubTopicRepository.delete(instituteClassSubTopicResult.get());

        return Response.generate("class sub topic deleted", HttpStatus.OK);
    }

    public ResponseEntity<Object> get(Long topicId) {
        Optional<InstituteClassSubTopic> instituteClassSubTopicResult = instituteClassSubTopicRepository.findById(topicId);
        if (instituteClassSubTopicResult.isEmpty()) {
            return Response.generate("class sub topic not found", HttpStatus.NOT_FOUND);
        }

        return Response.generate("class sub topic", HttpStatus.OK, instituteClassSubTopicResult.get());
    }

    public ResponseEntity<Object> getTopicSubTopics(Long topicId) {
        List<InstituteClassSubTopic> instituteClassSubTopicResult = instituteClassSubTopicRepository.findAllByTopicId(topicId);

        return Response.generate("class topic's sub topics", HttpStatus.OK, instituteClassSubTopicResult);
    }
}
