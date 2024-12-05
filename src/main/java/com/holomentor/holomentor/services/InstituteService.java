package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.institute.InstituteCreateDTO;
import com.holomentor.holomentor.dto.institute.InstituteUpdateDTO;
import com.holomentor.holomentor.models.*;
import com.holomentor.holomentor.projections.institute.InstituteDetailsProjection;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassProjection;
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

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class InstituteService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserInstituteRepository userInstituteRepository;
    @Autowired
    private InstituteRepository instituteRepository;
    @Autowired
    private UserInvitationRepository userInvitationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;
    @Autowired
    private Environment environment;
    @Autowired
    private InstituteClassRepository instituteClassRepository;


    public ResponseEntity<Object> create(InstituteCreateDTO body) throws IOException {
//        create institute
        Institute institute = new Institute();
        institute.setName(body.getName());
        institute.setCity(body.getCity());
        institute.setAddress(body.getAddress());
        institute.setRegistrationNumber(body.getRegistrationNumber());
        institute.setEstablishedDate(body.getEstablishedDate());

        instituteRepository.save(institute);

//        check if admin account exists
        Optional<User> userExists = userRepository.findByEmail(body.getAdminEmail());

//        if user account does not exist create and account
        User adminUser = new User();
        if (userExists.isEmpty()) {
            User newAdminUser = new User();
            newAdminUser.setFirstName(body.getAdminFirstName());
            newAdminUser.setLastName(body.getAdminLastName());
            newAdminUser.setEmail(body.getAdminEmail());

//            create a random password
            String randomPassword = UUID.randomUUID().toString();
            newAdminUser.setPassword(passwordEncoder.encode(randomPassword));
//            create user account
            userRepository.save(newAdminUser);

            adminUser.setId(newAdminUser.getId());
            adminUser.setFirstName(newAdminUser.getFirstName());
            adminUser.setLastName(newAdminUser.getLastName());
            adminUser.setEmail(newAdminUser.getEmail());
        } else {
            adminUser.setId(userExists.get().getId());
            adminUser.setFirstName(userExists.get().getFirstName());
            adminUser.setLastName(userExists.get().getLastName());
            adminUser.setEmail(userExists.get().getEmail());
        }

//        create user institute record
        UserInstitute userInstitute = new UserInstitute();
        userInstitute.setInstituteId(institute.getId());
        userInstitute.setUserId(adminUser.getId());
        userInstitute.setRole(UserInstitute.RoleTypes.INSTITUTE);

        userInstituteRepository.save(userInstitute);

//        create user invitation
        String invitationToken = UUID.randomUUID().toString();

        UserInvitation invitation = new UserInvitation();
        invitation.setToken(invitationToken);
        invitation.setIsValid(true);
        invitation.setUserId(adminUser.getId());
        invitation.setInstituteId(institute.getId());
        invitation.setUserInstituteId(userInstitute.getId());

        userInvitationRepository.save(invitation);

//        prepare mail dynamic data
        HashMap<String, String> dynamicData = new HashMap<>();
        String redirectLink = String.format("%sinvitation?token=%s&reset=%s", environment.getProperty("env.holomentor.client_url"), invitationToken, userExists.isEmpty());
        dynamicData.put("redirect_link", redirectLink);
        System.out.println(adminUser.getEmail());
//        send mail to the user account
        mailService.sendMail(
                SendGridMail.TemplateNames.INSTITUTE_REGISTRATION,
                adminUser.getEmail(),
                "Invitation to Register to the Institute",
                dynamicData
        );

        return Response.generate("New institute created and admin registration mail has been sent.", HttpStatus.OK);
    }

    public ResponseEntity<Object> get(Long id) {
        Optional<Institute> institute = instituteRepository.findById(id);
        if (institute.isEmpty()) {
            return Response.generate("institute details not found", HttpStatus.NOT_FOUND);
        }

        return Response.generate("institute details found", HttpStatus.OK, institute);
    }

    public ResponseEntity<Object> getAll(String search, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<InstituteDetailsProjection> institutes = instituteRepository.getInstituteDetailsByNameAndIsDeleted(search, false, pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("pages", institutes.getTotalPages());
        data.put("data", institutes.getContent());

        return Response.generate("institutes.", HttpStatus.OK, data);
    }

    public ResponseEntity<Object> delete(Long id) {
        Optional<Institute> instituteResult = instituteRepository.findById(id);
        if (instituteResult.isEmpty()) {
            return Response.generate("institute details not found", HttpStatus.NOT_FOUND);
        }
        Institute institute = instituteResult.get();
        institute.setIsDeleted(true);

        instituteRepository.save(institute);
        return Response.generate("institute details have been deleted.", HttpStatus.OK);
    }

    public ResponseEntity<Object> update(Long id, InstituteUpdateDTO body) {
        Optional<Institute> instituteResult = instituteRepository.findById(id);
        if (instituteResult.isEmpty()) {
            return Response.generate("institute details not found", HttpStatus.NOT_FOUND);
        }
        Institute institute = instituteResult.get();
        institute.setName(body.getName());
        institute.setCity(body.getCity());
        institute.setAddress(body.getAddress());
        institute.setRegistrationNumber(body.getRegistrationNumber());
        institute.setEstablishedDate(body.getEstablishedDate());

        instituteRepository.save(institute);
        return Response.generate("institute details have been updated.", HttpStatus.OK);
    }

    public ResponseEntity<Object> getGroupedClassesByTeacherAndInstitute(Long teacherId, Long instituteId) {
        // call queary
        List<InstituteClassProjection> classes = instituteClassRepository.findClassesGroupedByTeacherAndInstitute(teacherId, instituteId);

        if (classes.isEmpty()) {
            return Response.generate("No classes found for the specified teacher and institute.", HttpStatus.NOT_FOUND);
        }

        // Grouping classes by subject name
        Map<String, List<InstituteClassProjection>> groupedClasses = classes.stream()
                .collect(Collectors.groupingBy(InstituteClassProjection::getSubjectName));

        return Response.generate("Grouped classes retrieved successfully.", HttpStatus.OK, groupedClasses);
    }


}
