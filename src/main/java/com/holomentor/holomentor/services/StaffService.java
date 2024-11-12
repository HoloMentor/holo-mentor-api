// StaffService.java
package com.holomentor.holomentor.services;

import org.springframework.stereotype.Service;

import java.util.*;

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
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    @Autowired
    private UserInvitationRepository userInvitationRepository;

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
        teacherStaff.setUserStaffId(staffUser.getId()); // user_id
        teacherStaff.setUserTeacherId(body.getTeacherId()); // teacher_id
        teacherStaff.setInstituteId(body.getInstituteId());
        teacherStaffRepository.save(teacherStaff);

        //create user invitation
        String invitationToken = UUID.randomUUID().toString();
        UserInvitation invitation = new UserInvitation();
        invitation.setToken(invitationToken);
        invitation.setIsValid(true);
        invitation.setUserId(staffUser.getId());
        invitation.setInstituteId(body.getInstituteId());
        invitation.setUserInstituteId(userInstitute.getId());

        userInvitationRepository.save(invitation);

        // Send registration email
        HashMap<String, String> dynamicData = new HashMap<>();
        String redirectLink = String.format("%sinvitation?token=%s&reset=%s", environment.getProperty("env.holomentor.client_url"), invitationToken, existingUser.isEmpty());
        dynamicData.put("redirect_link", redirectLink);

        mailService.sendMail(
                SendGridMail.TemplateNames.STAFF_REGISTRATION,
                staffUser.getEmail(),
                "Invitation to Register as a Supporting Staff Member",
                dynamicData
        );

        return Response.generate("Staff user invitation has been sent", HttpStatus.OK);
    }

    public ResponseEntity<Object> getStaffByTeacherAndInstitute(Long teacherId, Long instituteId, Integer page, Integer size) {
        // Step 1: Retrieve paginated staff-teacher mappings for the given teacher and institute
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<TeacherStaff> teacherStaffPage = teacherStaffRepository
                .findAllByInstituteIdAndUserTeacherId(instituteId, teacherId, pageable);

        // Step 2: Check if the page is empty
        if (teacherStaffPage.isEmpty()) {
            return Response.generate("No staff found for the given teacher and institute", HttpStatus.NOT_FOUND);
        }

        // Step 3: Extract all user IDs (staff members) from the paginated mappings
        List<Long> staffUserIds = teacherStaffPage.stream()
                .map(TeacherStaff::getUserStaffId)
                .collect(Collectors.toList());

        // Step 4: Retrieve all user details for the collected staff user IDs
        List<User> staffUsers = userRepository.findAllById(staffUserIds);

        // Step 5: Prepare the response with user details and pagination information
        Map<String, Object> data = new HashMap<>();
        data.put("pages", teacherStaffPage.getTotalPages());
        data.put("totalItems", teacherStaffPage.getTotalElements());
        data.put("currentPage", teacherStaffPage.getNumber() + 1);
        data.put("data", staffUsers);
        data.put("pageSize", size);

        return Response.generate("Fetched staff members successfully", HttpStatus.OK, data);
    }

    public ResponseEntity<Object> get(Long id) {
        // fetch the staff member
        Map<String, Object> response = new HashMap<>();
        response.put("staffId", id);
        response.put("message", "Fetched staff data successfully.");
        return Response.generate("support staff member Found", HttpStatus.OK, response);
    }
}
