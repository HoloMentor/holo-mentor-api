package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.user.UserUpdateDTO;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.repositories.UserRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<Object> update(Long id, UserUpdateDTO body) {
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
}
