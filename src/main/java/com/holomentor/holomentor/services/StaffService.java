package com.holomentor.holomentor.services;

import com.holomentor.holomentor.models.Institute;
import com.holomentor.holomentor.models.TeacherStaff;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.repositories.StaffRepository;
import com.holomentor.holomentor.repositories.UserRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public ResponseEntity<Object> get(Long id) {
        Optional<User> staff = staffRepository.findById(id);
        if (staff.isEmpty()) {
            return Response.generate("staff details not found", HttpStatus.NOT_FOUND);
        }

        return Response.generate("staff details found", HttpStatus.OK, staff);
    }

    public ResponseEntity<Object> getInstituteTeacherStaff(Long instituteId, Long teacherId, String search, Integer page, Integer size) {

        //i need to change this by adding query to get staff members related to institute and specific teacher
        return Response.generate("staff found", HttpStatus.OK, "This is a dummy response because it gives error");
    }



}
