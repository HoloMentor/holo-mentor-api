package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.classes.ClassCreateDTO;
import com.holomentor.holomentor.dto.classes.ClassUpdateDTO;
import com.holomentor.holomentor.models.InstituteClass;
import com.holomentor.holomentor.models.UserInstitute;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassProjection;
import com.holomentor.holomentor.repositories.InstituteClassRepository;
import com.holomentor.holomentor.repositories.UserInstituteRepository;
import com.holomentor.holomentor.repositories.UserRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;




@Service
@Transactional
public class ClassService {

    private final InstituteClassRepository instituteClassRepository;
    private final UserInstituteRepository userInstituteRepository;

    @Autowired
    public ClassService(InstituteClassRepository instituteClassRepository, UserInstituteRepository userInstituteRepository, UserRepository userRepository ) {
        this.instituteClassRepository = instituteClassRepository;
        this.userInstituteRepository = userInstituteRepository;
    }

    public ResponseEntity<Object> create(ClassCreateDTO body) {

        InstituteClass instituteClass = new InstituteClass();

        instituteClass.setClassName(body.getClassName());
        instituteClass.setInstituteId(body.getInstituteId());
        instituteClass.setSubjectId(body.getSubjectId());
        instituteClass.setStartTime(body.getStartTime());
        instituteClass.setEndTime(body.getEndTime());
        instituteClass.setDayOfWeek(body.getDayOfWeek());
        instituteClass.setTeacherId(body.getTeacherId());

        Optional<UserInstitute> optionalUser = userInstituteRepository.findByUserIdAndInstituteIdAndRole(
                                        body.getTeacherId(),
                                        body.getInstituteId(),
                                        UserInstitute.RoleTypes.TEACHER);

        if (optionalUser.isPresent()) {
            instituteClass.setInstituteTeacherId(optionalUser.get().getId());
            instituteClassRepository.save(instituteClass);
            return Response.generate("Class created successfully", HttpStatus.OK);
        }
        else{
            return Response.generate("User not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> delete(Long id) {

        Optional<InstituteClass> instituteClass = instituteClassRepository.findById(id);

        instituteClass.ifPresent(instituteClassRepository::delete);
        return Response.generate("Deleted class", HttpStatus.OK);
    }

    public ResponseEntity<Object> update(Long id, ClassUpdateDTO body) {
        Optional<InstituteClass> classResult = instituteClassRepository.findById(id);
        if (classResult.isEmpty()) {
            return Response.generate("Class not found", HttpStatus.NOT_FOUND);
        }
        InstituteClass instituteClass = classResult.get();

        instituteClass.setClassName(body.getClassName());
        instituteClass.setSubjectId(body.getSubjectId());
        instituteClass.setStartTime(body.getStartTime());
        instituteClass.setEndTime(body.getEndTime());
        instituteClass.setDayOfWeek(body.getDayOfWeek());
        instituteClass.setTeacherId(body.getTeacherId());

        instituteClassRepository.save(instituteClass);

        return Response.generate("Updated", HttpStatus.OK);
    }


    public ResponseEntity<Object> findByInstituteId(Long instituteId, String search, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<InstituteClassProjection> instituteClassList = instituteClassRepository.findByClassByInstitute(search, instituteId, pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("pages", instituteClassList.getTotalPages());
        data.put("data", instituteClassList.getContent());
        return Response.generate("classes", HttpStatus.OK, data);
    }

    public ResponseEntity<Object> get(Long id) {
        Optional<InstituteClass> classResult = instituteClassRepository.findById(id);
        if (classResult.isEmpty()) {
            return Response.generate("Class not found", HttpStatus.NOT_FOUND);
        }

        return Response.generate("Class", HttpStatus.OK, classResult.get());
    }


}