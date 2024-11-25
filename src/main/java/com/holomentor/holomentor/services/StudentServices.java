package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.teacher.TeacherCreateDTO;
import com.holomentor.holomentor.dto.teacher.TeacherUpdateDTO;
import com.holomentor.holomentor.models.*;
import com.holomentor.holomentor.projections.student.InstituteStudentClassProjection;
import com.holomentor.holomentor.projections.teacher.InstituteTeacherClassProjection;
import com.holomentor.holomentor.projections.teacher.InstituteTeacherProjection;
import com.holomentor.holomentor.repositories.*;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class StudentServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInstituteRepository userInstituteRepository;
    @Autowired
    private InstituteTeacherRepository instituteTeacherRepository;
    @Autowired
    private InstituteClassRepository instituteClassRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Environment environment;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserInvitationRepository userInvitationRepository;

    public ResponseEntity<Object> create(TeacherCreateDTO body) throws IOException {
        //        check if admin account exists
        Optional<User> userExists = userRepository.findByEmail(body.getEmail());

        //        if user account does not exist create and account
        User teacherUser = new User();
        if (userExists.isEmpty()) {
            teacherUser.setFirstName(body.getFirstName());
            teacherUser.setLastName(body.getLastName());
            teacherUser.setEmail(body.getEmail());

//            create a random password
            String randomPassword = UUID.randomUUID().toString();
            teacherUser.setPassword(passwordEncoder.encode(randomPassword));

            userRepository.save(teacherUser);
        } else {
            teacherUser.setId(userExists.get().getId());
            teacherUser.setFirstName(userExists.get().getFirstName());
            teacherUser.setLastName(userExists.get().getLastName());
            teacherUser.setEmail(userExists.get().getEmail());
        }

//        create user institute record
        UserInstitute userInstitute = new UserInstitute();
        userInstitute.setInstituteId(body.getInstituteId());
        userInstitute.setUserId(teacherUser.getId());
        userInstitute.setRole(UserInstitute.RoleTypes.TEACHER);

        userInstituteRepository.save(userInstitute);

//        create user invitation
        String invitationToken = UUID.randomUUID().toString();
        UserInvitation invitation = new UserInvitation();
        invitation.setToken(invitationToken);
        invitation.setIsValid(true);
        invitation.setUserId(teacherUser.getId());
        invitation.setInstituteId(body.getInstituteId());
        invitation.setUserInstituteId(userInstitute.getId());

        userInvitationRepository.save(invitation);

        HashMap<String, String> dynamicData = new HashMap<>();
        String redirectLink = String.format("%sinvitation?token=%s&reset=%s", environment.getProperty("env.holomentor.client_url"), invitationToken, userExists.isEmpty());
        dynamicData.put("redirect_link", redirectLink);

//        send mail to the user account
        mailService.sendMail(
                SendGridMail.TemplateNames.TEACHER_REGISTRATION,
                teacherUser.getEmail(),
                "Invitation to Register as a Teacher",
                dynamicData
        );

        return Response.generate("teacher user invitation has been sent", HttpStatus.OK);
    }

    public ResponseEntity<Object> getTeachersByInstituteId(Long instituteId, String search, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<InstituteTeacherProjection> teachers = userInstituteRepository.findByInstituteIdTeachersAndIsActive(search, instituteId, true, pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("pages", teachers.getTotalPages());
        data.put("data", teachers.getContent());

        return Response.generate("teachers Found", HttpStatus.OK, data);
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

    public ResponseEntity<Object> getTeacherById(Long id) {
        Optional<User> teacher = userRepository.findById(id);
        if (teacher.isPresent()) {
            User user = teacher.get();
            return Response.generate("teacher found", HttpStatus.OK);
        }

        return Response.generate("teacher not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> getTeacherStats(Long id) {
        InstituteTeacherStatProjection instituteTeacherStats = instituteTeacherRepository.findTeacherStatsById(id);

        return Response.generate("teacher statistics are found", HttpStatus.OK, instituteTeacherStats);
    }

    public ResponseEntity<Object> getInstituteTeacherClasses(Long id, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<InstituteTeacherClassProjection> institutesClasses = instituteClassRepository.findInstituteClassesByInstituteTeacherId(id, pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("pages", institutesClasses.getTotalPages());
        data.put("data", institutesClasses.getContent());

        return Response.generate("teacher's classes", HttpStatus.OK, data);
    }

    //    get institute student entolled classes
    public ResponseEntity<Object> getEnrolledClasses(Long studentId, String search, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUser authUser = (AuthUser) (auth).getPrincipal();

        // get classes by student id
        Long userID = authUser.getUserId();
        Page<InstituteStudentClassProjection> classes = instituteClassRepository.findClassesByStudentId(userID, pageable);

        // Group classes by teacher ID
        Map<Long, List<Map<String, Object>>> classesByTeacher = new HashMap<>();
        for (InstituteStudentClassProjection cls : classes.getContent()) {
            Long teacherId = cls.getTeacherId();

            Map<String, Object> classData = new HashMap<>();
            classData.put("id", cls.getId());
            classData.put("className", cls.getClassName());
            classData.put("studentCount", cls.getStudentCount());
            classData.put("subjectName", cls.getSubjectName());
            classData.put("teacherFirstName", cls.getTeacherFirstName());
            classData.put("teacherLastName", cls.getTeacherLastName());

            classesByTeacher.computeIfAbsent(teacherId, k -> new ArrayList<>()).add(classData);
        }

        // Format response data
        List<Map<String, Object>> teacherGroupedData = new ArrayList<>();
        for (Map.Entry<Long, List<Map<String, Object>>> entry : classesByTeacher.entrySet()) {
            Map<String, Object> teacherData = new HashMap<>();
            teacherData.put("teacherId", entry.getKey());
            teacherData.put("classes", entry.getValue());
            teacherData.put("teacherFirstName", entry.getValue().get(0).get("teacherFirstName"));
            teacherData.put("teacherLastName", entry.getValue().get(0).get("teacherLastName"));

            teacherGroupedData.add(teacherData);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("pages", classes.getTotalPages());
        data.put("data", teacherGroupedData);

        return Response.generate("classes", HttpStatus.OK, data);


//        Map<String, Object> data = new HashMap<>();
//        data.put("pages", classes.getTotalPages());
//        data.put("data", classes.getContent());
//
//        return Response.generate("classes", HttpStatus.OK, data);


//        Page<InstituteClassProjection> classes = instituteClassRepository.findByClassByInstitute(search, studentId, pageable);

//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());


//        {
//            "data": {
//            "principal": {
//                "id": 5,
//                        "userId": 4,
//                        "instituteId": 1,
//                        "firstName": "Nicholle",
//                        "lastName": "Kinghorn",
//                        "email": "student@mailinator.com",
//                        "password": "$2a$10$euioLsnb4INH2x6MqwULBOQXJ4o3a.jKsxixW.HqliQGKyFxty4D.",
//                        "role": "STUDENT",
//                        "userRole": "USER",
//                        "enabled": true,
//                        "authorities": [
//                {
//                    "authority": "STUDENT"
//                }
//            ],
//                "username": "student@mailinator.com",
//                        "accountNonLocked": true,
//                        "accountNonExpired": true,
//                        "credentialsNonExpired": true
//            },
//            "credentials": null,
//                    "name": "student@mailinator.com",
//                    "details": {
//                "remoteAddress": "0:0:0:0:0:0:0:1",
//                        "sessionId": null
//            },
//            "class": "org.springframework.security.authentication.UsernamePasswordAuthenticationToken",
//                    "email": "student@mailinator.com",
//                    "authorities": [
//            {
//                "authority": "STUDENT"
//            }
//        ]
//        },
//            "message": "classes",
//                "status": 200
//        }

//        get user id from getPrincipal


//        dump all data in SecurityContextHolder.getContext().getAuthentication() and store in a map


//        Map<String, Object> data = new HashMap<>();
//
//
//
//        String email = authUser.getEmail();
//        String userId = authUser.getUserId().toString();
//
//
////        print email
//        System.out.println(authUser.getEmail());
//        System.out.println(authUser.getUserId());
//
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        AuthUser authUser = (AuthUser) (auth).getPrincipal();
//
//
//        data.put("email", SecurityContextHolder.getContext().getAuthentication().getName());
//        data.put("principal", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        data.put("authorities", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
//        data.put("credentials", SecurityContextHolder.getContext().getAuthentication().getCredentials());
//        data.put("details", SecurityContextHolder.getContext().getAuthentication().getDetails());
//        data.put("name", SecurityContextHolder.getContext().getAuthentication().getName());
//        data.put("class", SecurityContextHolder.getContext().getAuthentication().getClass());
////        data.put("userId", ((Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).get("userId"));
////        data.put("instituteId", ((Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).get("instituteId"));
//
//
//
//
////        data.put("pages", classes.getTotalPages());
////        data.put("data", classes.getContent());
//
//        return Response.generate("classes", HttpStatus.OK, data);
    }


}

