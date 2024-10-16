// StaffService.java
package com.holomentor.holomentor.services;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import com.holomentor.holomentor.dto.staff.StaffCreateDTO;
import com.holomentor.holomentor.models.*;
import com.holomentor.holomentor.repositories.*;
import com.holomentor.holomentor.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;



@Service
public class StaffService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherStaffRepository teacherStaffRepository;
    @Autowired
    private UserInstituteRepository userInstituteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Environment environment;
    @Autowired
    private MailService mailService;

    public ResponseEntity<Object> create(StaffCreateDTO body) throws IOException {
        // Check if the staff member already exists
        Optional<User> existingUser = userRepository.findByEmail(body.getEmail());
        User staffUser = new User();

        if (existingUser.isEmpty()) {
            staffUser.setFirstName(body.getFirstName());
            staffUser.setLastName(body.getLastName());
            staffUser.setEmail(body.getEmail());

            // Generate a random password
            String randomPassword = UUID.randomUUID().toString();
            staffUser.setPassword(passwordEncoder.encode(randomPassword));

            userRepository.save(staffUser);
        } else {
            staffUser.setId(existingUser.get().getId());
            staffUser.setFirstName(existingUser.get().getFirstName());
            staffUser.setLastName(existingUser.get().getLastName());
            staffUser.setEmail(existingUser.get().getEmail());
        }

        // Create a user institute record for the staff
        UserInstitute userInstitute = new UserInstitute();
        userInstitute.setInstituteId(body.getInstituteId());
        userInstitute.setUserId(staffUser.getId());
        userInstitute.setRole(UserInstitute.RoleTypes.STAFF);

        userInstituteRepository.save(userInstitute);

        // Create a TeacherStaff record
        TeacherStaff teacherStaff = new TeacherStaff();
        teacherStaff.setUserStaffId(staffUser.getId());
        teacherStaff.setUserTeacherId(body.getTeacherId());
        teacherStaff.setUserInstituteStaffId(userInstitute.getId());
        teacherStaff.setUserInstituteTeacherId(body.getTeacherInstituteId());
        teacherStaff.setInstituteId(body.getInstituteId());
        teacherStaffRepository.save(teacherStaff);

        // Send registration email
        HashMap<String, String> dynamicData = new HashMap<>();
        String redirectLink = String.format("%sinvitation?token=%s&reset=%s", environment.getProperty("env.holomentor.client_url"), UUID.randomUUID().toString(), existingUser.isEmpty());
        dynamicData.put("redirect_link", redirectLink);

        mailService.sendMail(
                SendGridMail.TemplateNames.STAFF_REGISTRATION,
                staffUser.getEmail(),
                "Invitation to Register as a Staff Member",
                dynamicData
        );

        return Response.generate("Staff user invitation has been sent", HttpStatus.OK);
    }

    public Object getInstituteTeacherStaff(Long instituteId, Long teacherId, String search, Integer pageNo, Integer pageSize) {
        // Implement your actual logic to fetch teacher staff data
        Map<String, Object> response = new HashMap<>();
        response.put("instituteId", instituteId);
        response.put("teacherId", teacherId);
        response.put("search", search);
        response.put("pageNo", pageNo);
        response.put("pageSize", pageSize);
        response.put("message", "Fetched teacher staff data successfully.");
        return response;
    }

    // Method to get a specific staff member by ID
    public Object get(Long id) {
        // Implement your actual logic to fetch the staff member
        Map<String, Object> response = new HashMap<>();
        response.put("staffId", id);
        response.put("message", "Fetched staff data successfully.");
        return response;
    }
}
