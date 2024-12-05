package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.student.StudentCreateDTO;
import com.holomentor.holomentor.dto.student.StudentTierDTO;
import com.holomentor.holomentor.models.*;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassStudentProjection;
import com.holomentor.holomentor.projections.subject.InstituteSubjectProjection;
import com.holomentor.holomentor.repositories.*;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInstituteRepository userInstituteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Environment environment;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserInvitationRepository userInvitationRepository;
    @Autowired
    private InstituteClassStudentRepository instituteClassStudentRepository;



    public ResponseEntity<Object> create(StudentCreateDTO body) throws IOException {

        Optional<User> userExists = userRepository.findByEmail(body.getEmail());

        User studentUser = new User();

        if(userExists.isEmpty()){
            studentUser.setEmail(body.getEmail());
            studentUser.setFirstName(body.getFirstName());
            studentUser.setLastName(body.getLastName());

            String randomPassword = UUID.randomUUID().toString();
            studentUser.setPassword(passwordEncoder.encode(randomPassword));

            userRepository.save(studentUser);
        } else {
            studentUser.setId(userExists.get().getId());
            studentUser.setEmail(body.getEmail());
            studentUser.setFirstName(body.getFirstName());
            studentUser.setLastName(body.getLastName());
        }

        UserInstitute userInstitute = new UserInstitute();
        userInstitute.setInstituteId(body.getInstituteId());
        userInstitute.setUserId(studentUser.getId());
        userInstitute.setRole(UserInstitute.RoleTypes.STUDENT);

        userInstituteRepository.save(userInstitute);

        InstituteClassStudent instituteClassStudent = new InstituteClassStudent();
        instituteClassStudent.setStudentId(studentUser.getId());
        instituteClassStudent.setInstituteId(body.getInstituteId());
        instituteClassStudent.setClassId(body.getClassId());
        instituteClassStudent.setInstituteStudentId(userInstitute.getId());
        instituteClassStudent.setRegistrationNumber(body.getRegistrationNo());

        instituteClassStudentRepository.save(instituteClassStudent);

        String invitationToken = UUID.randomUUID().toString();
        UserInvitation invitation = new UserInvitation();
        invitation.setToken(invitationToken);
        invitation.setIsValid(true);
        invitation.setUserId(studentUser.getId());
        invitation.setInstituteId(body.getInstituteId());
        invitation.setUserInstituteId(userInstitute.getId());

        userInvitationRepository.save(invitation);

        HashMap<String, String> dynamicData = new HashMap<>();
        String redirectLink = String.format("%sinvitation?token=%s&reset=%s", environment.getProperty("env.holomentor.client_url"), invitationToken, userExists.isEmpty());
        dynamicData.put("redirect_link", redirectLink);

        mailService.sendMail(
                SendGridMail.TemplateNames.STUDENT_REGISTRATION,
                studentUser.getEmail(),
                "Invitation to Register as a Student",
                dynamicData
        );

        return Response.generate("new student created", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> findStudentsByClassId(Long id, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<InstituteClassStudentProjection> instituteClassStudents = instituteClassStudentRepository.findStudentsByClassId(id, pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("pages", instituteClassStudents.getTotalPages());
        data.put("data", instituteClassStudents.getContent());

        return Response.generate("teacher's classes", HttpStatus.OK, data);
    }

    public ResponseEntity<Object> getAllInstituteStudentClasses(Long classId, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<InstituteClassStudentProjection>  instituteClassStudents = instituteClassStudentRepository.finAllStudentsByClassId(classId, pageable);


        //data
        Map<String, Object> data = new HashMap<>();
        data.put("pages", instituteClassStudents.getTotalPages());
        data.put("totalElements", instituteClassStudents.getTotalElements());
        data.put("currentPage", page);
        data.put("data", instituteClassStudents.getContent());

        // Return the response with a custom success message
        return Response.generate("Students in the class retrieved successfully", HttpStatus.OK, data);
    }





}
