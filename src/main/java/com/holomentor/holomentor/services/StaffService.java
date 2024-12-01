// StaffService.java
package com.holomentor.holomentor.services;

import com.holomentor.holomentor.projections.user.UserInstituteProjection;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

    //to check logs
    private static final Logger logger = LoggerFactory.getLogger(StaffService.class);
    public ResponseEntity<Object> create(StaffCreateDTO body) throws IOException {
        // Check if the staff member already exists
        Optional<User> existingUser = userRepository.findByEmail(body.getEmail());
        User staffUser = new User();

        if (existingUser.isEmpty()) {

            User newStaffUser = new User();
            newStaffUser.setFirstName(body.getFirstName());
            newStaffUser.setLastName(body.getLastName());
            newStaffUser.setEmail(body.getEmail());

            // Generate a random password
            String randomPassword = UUID.randomUUID().toString();
            newStaffUser.setPassword(passwordEncoder.encode(randomPassword));

            //create new user
            userRepository.save(newStaffUser);

            staffUser.setId(newStaffUser.getId());
            staffUser.setEmail(newStaffUser.getEmail());
            staffUser.setFirstName(newStaffUser.getFirstName());
            staffUser.setLastName(newStaffUser.getLastName());


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
        String redirectLink = String.format("%s/invitation?token=%s&reset=%s", environment.getProperty("env.holomentor.client_url"), invitationToken, existingUser.isEmpty());
        String loginLink= "http://localhost:3000/";

        //put dynamicData
        dynamicData.put("login_link",loginLink);
        dynamicData.put("redirect_link",redirectLink);

        mailService.sendMail(
                SendGridMail.TemplateNames.STAFF_REGISTRATION,
                staffUser.getEmail(),
                "Invitation to Register as a Supporting Staff Member",
                dynamicData
        );

        return Response.generate("Staff user invitation has been sent", HttpStatus.OK);
    }

    public ResponseEntity<Object> getStaffByTeacherAndInstitute(Long teacherId, Long instituteId, Integer page, Integer size) {
        // Retrieve paginated staff-teacher mappings for the given teacher and institute
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<TeacherStaff> teacherStaffPage = teacherStaffRepository
                .findAllByInstituteIdAndUserTeacherId(instituteId, teacherId, pageable);

        // Check if the page is empty
        if (teacherStaffPage.isEmpty()) {
            return Response.generate("No staff found for the given teacher and institute", HttpStatus.NOT_FOUND);
        }

        // Extract all user IDs (staff members) from the paginated mappings
        List<Long> staffUserIds = teacherStaffPage.stream()
                .map(TeacherStaff::getUserStaffId)
                .collect(Collectors.toList());


        // Retrieve all user details for the collected staff user IDs
        List<User> staffUsers = userRepository.findAllById(staffUserIds);
        logger.info("List contents: {}", staffUsers);
        // pagination information
        Map<String, Object> data = new HashMap<>();
        data.put("pages", teacherStaffPage.getTotalPages());
        data.put("totalItems", teacherStaffPage.getTotalElements());
        data.put("currentPage", teacherStaffPage.getNumber() + 1);
        data.put("data", staffUsers);
        data.put("pageSize", size);

        return Response.generate("Fetched staff members successfully", HttpStatus.OK, data);
    }

    public ResponseEntity<Object> get(Long id ) {
        // fetch the staff member

        Optional<User> user = userRepository.findById(id);

        Optional<TeacherStaff> teacherStaff = teacherStaffRepository.findById(id);

        Map<String, Object> response = new HashMap<>();

        response.put("staffId", id);

        response.put("message", "Fetched staff data successfully.");
        return Response.generate("support staff member Found", HttpStatus.OK, response);
    }

    public ResponseEntity<Object> getTeacherData(Long userId , Long instituteId ) {
        // fetch the staff member

        Optional<User> user = userRepository.findById(userId);

        List<UserInstituteProjection> teacherStaff = teacherStaffRepository.findDetailsByUserIdAndInstituteId(userId, instituteId);
        Map<String, Object> response = new HashMap<>();

        response.put("staffTeacher",teacherStaff);
        response.put("staffUserId", userId);
        response.put("message", "Fetched staff data successfully.");
        return Response.generate("support staff member Found", HttpStatus.OK, response);
    }

    public ResponseEntity<Object> delete(Long staffId) {
        // Fetch the user by ID
        Optional<User> userOptional = userRepository.findById(staffId);
        if (userOptional.isEmpty()) {
            return Response.generate("Staff member not found", HttpStatus.NOT_FOUND);
        }

        // Update the `isDeleted` field to true
        User user = userOptional.get();
        user.setIsDeleted(true);
        userRepository.save(user);

        // Log the deletion
        logger.info("Staff member with ID {} marked as deleted.", staffId);

        // Return response
        return Response.generate("Staff member deleted successfully", HttpStatus.OK);
    }
}
