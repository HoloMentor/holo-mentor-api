package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.subject.SubjectCreateDTO;
import com.holomentor.holomentor.dto.subject.SubjectUpdateDTO;
import com.holomentor.holomentor.models.InstituteSubject;
import com.holomentor.holomentor.projections.subject.InstituteSubjectProjection;
import com.holomentor.holomentor.repositories.SubjectRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class SubjectServices {
    @Autowired
    private SubjectRepository subjectRepository;

    public ResponseEntity<Object> create(SubjectCreateDTO body) {
        InstituteSubject instituteSubject = new InstituteSubject();
        instituteSubject.setName(body.getName());
        instituteSubject.setInstituteId(body.getInstituteId());

        subjectRepository.save(instituteSubject);

        return Response.generate("new subject created", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> update(Long subjectId, SubjectUpdateDTO body) {
        Optional<InstituteSubject> subjectResult = subjectRepository.findById(subjectId);
        if  (subjectResult.isEmpty()) {
            return Response.generate("subject not found", HttpStatus.NOT_FOUND);
        }

        InstituteSubject instituteSubject = subjectResult.get();
        instituteSubject.setName(body.getName());

        subjectRepository.save(instituteSubject);

        return Response.generate("subject details updated", HttpStatus.OK);
    }

    public ResponseEntity<Object> delete(Long subjectId) {
        Optional<InstituteSubject> subjectResult = subjectRepository.findById(subjectId);
        if (subjectResult.isEmpty()) {
            return Response.generate("subject not found", HttpStatus.NOT_FOUND);
        }

        subjectRepository.delete(subjectResult.get());

        return Response.generate("subject details deleted", HttpStatus.OK);
    }

    public ResponseEntity<Object> getInstituteSubjects(Long instituteId, String search, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<InstituteSubjectProjection> subjects = subjectRepository.findByNameContainingIgnoreCaseAndInstituteIdWithClassCountAndStudentCount(search, instituteId, pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("pages", subjects.getTotalPages());
        data.put("data", subjects.getContent());

        return Response.generate("subjects", HttpStatus.OK, data);
    }

    public ResponseEntity<Object> get(Long subjectId) {
        Optional<InstituteSubject> subjectResult = subjectRepository.findById(subjectId);
        if (subjectResult.isEmpty()) {
            return Response.generate("subject not found", HttpStatus.NOT_FOUND);
        }

        return Response.generate("subject details found", HttpStatus.OK, subjectResult.get());
    }
}
