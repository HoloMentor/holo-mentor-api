package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.institute.InstituteCreateDTO;
import com.holomentor.holomentor.models.Institute;
import com.holomentor.holomentor.models.SendGridMail;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.models.UserInstitute;
import com.holomentor.holomentor.repositories.InstituteRepository;
import com.holomentor.holomentor.repositories.UserInstituteRepository;
import com.holomentor.holomentor.repositories.UserRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

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
            newAdminUser.setPassword(passwordEncoder.encode("randomPassword"));
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

//        prepare mail dynamic data
        HashMap<String, String> dynamicData = new HashMap<String, String>();
        dynamicData.put("redirect_link", "https://example.com");

//        send mail to the user account
        mailService.sendMail(
                SendGridMail.TemplateNames.INSTITUTE_REGISTRATION,
                adminUser.getEmail(),
                "Invitation to Register to the Institute",
                dynamicData
        );

        return Response.generate("New institute created and admin registration mail has been sent.", HttpStatus.OK);
    }
}
