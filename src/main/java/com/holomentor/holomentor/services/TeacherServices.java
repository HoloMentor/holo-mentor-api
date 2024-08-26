package com.holomentor.holomentor.services;

import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.models.UserInstitute;
import com.holomentor.holomentor.repositories.UserInstituteRepository;
import com.holomentor.holomentor.repositories.UserRepository;
import com.holomentor.holomentor.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.holomentor.holomentor.dto.teacher.TeacherCreateDTO;

import jakarta.transaction.Transactional;

import java.util.Optional;

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
            userInstitute.setUserId(user.getId()); // Explicitly set the userId
            userInstitute.setIsActive(Boolean.TRUE);
            userInstitute.setRole(UserInstitute.RoleTypes.TEACHER);

            userInstituteRepository.save(userInstitute);
        } else {
            System.out.println("User not found for email: " + email);
        }
    }

}

