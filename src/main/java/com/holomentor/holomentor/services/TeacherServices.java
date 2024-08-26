package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.teacher.TeacherUpdateDTO;
import com.holomentor.holomentor.models.InstituteSubject;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.models.UserInstitute;
import com.holomentor.holomentor.projections.teacher.InstituteTeacherProjection;
import com.holomentor.holomentor.repositories.UserInstituteRepository;
import com.holomentor.holomentor.repositories.UserRepository;
import com.holomentor.holomentor.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import com.holomentor.holomentor.dto.teacher.TeacherCreateDTO;

import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeacherServices {

    private final UserRepository userRepository;
    private final UserInstituteRepository userInstituteRepository;

    @Autowired
    public TeacherServices(UserRepository userRepository, UserInstituteRepository userInstituteRepository) {
        this.userRepository = userRepository;
        this.userInstituteRepository = userInstituteRepository;
    }

    public ResponseEntity<Object> createTeacher(TeacherCreateDTO teacher) {
        User user = new User();

        user.setFirstName(teacher.getFirstName());
        user.setLastName(teacher.getLastName());
        user.setEmail(teacher.getEmail());
        user.setPassword("$2a$10$euioLsnb4INH2x6MqwULBOQXJ4o3a.jKsxixW.HqliQGKyFxty4D.");

        userRepository.save(user);
        createUserInstitute(teacher.getEmail(),teacher.getInstituteId());

        return ResponseEntity.ok("Teacher created successfully");
    }

    public void createUserInstitute(String email, Long instituteId) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserInstitute userInstitute = new UserInstitute();

            userInstitute.setInstituteId(instituteId);
            userInstitute.setUser(user);
            userInstitute.setUserId(user.getId());
            userInstitute.setIsActive(Boolean.TRUE);
            userInstitute.setRole(UserInstitute.RoleTypes.TEACHER);

            userInstituteRepository.save(userInstitute);
        } else {
            System.out.println("User not found for email: " + email);
        }
    }

    public ResponseEntity<Object> fetchTeachersByInstituteId(Long instituteId,String search, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<InstituteTeacherProjection> teachers = userInstituteRepository.findByInstituteIdTeachers(search, instituteId, pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("pages", teachers.getTotalPages());
        data.put("data", teachers.getContent());

        return Response.generate("Teachers Found", HttpStatus.OK, data);
    }

    public ResponseEntity<Object> delete(Long teacherId) {

        List<UserInstitute> teacherResult = userInstituteRepository.findByUserId(teacherId);
        Optional<User> optionalUser = userRepository.findById(teacherId);

        if (teacherResult.isEmpty()) {
            return Response.generate("teacher not found", HttpStatus.NOT_FOUND);
        }

        userInstituteRepository.delete(teacherResult.get(0));
        optionalUser.ifPresent(userRepository::delete);

        return Response.generate("teacher details deleted", HttpStatus.OK);
    }

    public ResponseEntity<Object> update(Long teacherId, TeacherUpdateDTO teacher) {
        Optional<User> optionalUser = userRepository.findById(teacherId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(teacher.getFirstName());
            user.setLastName(teacher.getLastName());
            user.setEmail(teacher.getEmail());

            userRepository.save(user);

            return Response.generate("teacher details updated", HttpStatus.OK);
        }
        return Response.generate("teacher not found", HttpStatus.NOT_FOUND);
    }
}

