package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.user.UserSuspendDTO;
import com.holomentor.holomentor.dto.user.UserUpdateDTO;
import com.holomentor.holomentor.dto.user.UserUpdateInfoDTO;
import com.holomentor.holomentor.models.Institute;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.projections.SystemStatsProjection;
import com.holomentor.holomentor.projections.user.UserProjection;
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
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> get(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            return Response.generate("user details not found", HttpStatus.NOT_FOUND);
        }

        return Response.generate("user details found", HttpStatus.OK, user.get());
    }

    public ResponseEntity<Object> getAll(String search, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<UserProjection> users = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndIsDeleted(search, search, search, false, pageable);

        Map<String, Object> data = new HashMap<>();
        data.put("pages", users.getTotalPages());
        data.put("data", users.getContent());

        return Response.generate("institutes.", HttpStatus.OK, data);
    }

    public ResponseEntity<Object> updateUser(Long id, UserUpdateDTO body) {
        Optional<User> userResult = userRepository.findById(id);

        if(userResult.isEmpty()) {
            return Response.generate("user details not found", HttpStatus.NOT_FOUND);
        }
        User user = userResult.get();

        user.setFirstName(body.getFirstName());
        user.setLastName(body.getLastName());
        user.setImage(body.getImage());

        userRepository.save(user);

        return Response.generate("user details updated", HttpStatus.OK);
    }

    public ResponseEntity<Object> updateInfo(Long id, UserUpdateInfoDTO body) {
        Optional<User> userResult = userRepository.findById(id);

        if(userResult.isEmpty()) {
            return Response.generate("user details not found", HttpStatus.NOT_FOUND);
        }
        User user = userResult.get();

        user.setCountry(body.getCountry());
        user.setCountryCode(body.getCountryCode());
        user.setContactNumber(body.getContactNumber());

        userRepository.save(user);

        return Response.generate("user details updated", HttpStatus.OK);
    }

    public ResponseEntity<Object> suspend(Long id, UserSuspendDTO body) {
        Optional<User> userResult = userRepository.findById(id);

        if(userResult.isEmpty()) {
            return Response.generate("user details not found", HttpStatus.NOT_FOUND);
        }
        User user = userResult.get();
        user.setIsBlacklisted(body.getIsBlacklisted());

        userRepository.save(user);

        return Response.generate("user suspend state updated", HttpStatus.OK);
    }

    public ResponseEntity<Object> delete(Long id) {
        Optional<User> userResult = userRepository.findById(id);

        if(userResult.isEmpty()) {
            return Response.generate("user details not found", HttpStatus.NOT_FOUND);
        }
        User user = userResult.get();
        user.setIsDeleted(true);

        userRepository.save(user);

        return Response.generate("user account deleted", HttpStatus.OK);
    }

    public ResponseEntity<Object> getStats() {
        SystemStatsProjection systemStatsProjection = userRepository.findAllStats();

        return Response.generate("system statistic details found", HttpStatus.OK, systemStatsProjection);
    }
}
