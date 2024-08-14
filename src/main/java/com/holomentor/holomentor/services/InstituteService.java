package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.institute.InstituteCreateDTO;
import com.holomentor.holomentor.models.*;
import com.holomentor.holomentor.repositories.InstituteRepository;
import com.holomentor.holomentor.repositories.UserInstituteRepository;
import com.holomentor.holomentor.repositories.UserInvitationRepository;
import com.holomentor.holomentor.repositories.UserRepository;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
        HashMap<String, String> dynamicData = new HashMap<String, String>();
        String redirectLink = String.format("invitation%s?token=%s&reset=%s", environment.getProperty("env.holomentor.client_url"), invitationToken, userExists.isEmpty());
        dynamicData.put("redirect_link", redirectLink);

//        send mail to the user account
        mailService.sendMail(
                SendGridMail.TemplateNames.INSTITUTE_REGISTRATION,
                adminUser.getEmail(),
                "Invitation to Register to the Institute",
                dynamicData
        );

        return Response.generate("New institute created and admin registration mail has been sent.", HttpStatus.OK);
    }

    public ResponseEntity<Object> getAll(String search, Integer page, Integer size) throws IOException {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Institute> institutes= instituteRepository.findByNameContainingIgnoreCase(search, pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("pages", institutes.getTotalPages());
        data.put("data", institutes.getContent());

        return Response.generate("All Institutes have been registered.", HttpStatus.OK, data);
    }
}