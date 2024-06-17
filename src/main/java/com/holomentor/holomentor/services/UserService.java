package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.user.UserCreateDTO;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.repositories.UserRepository;
import com.holomentor.holomentor.utils.BcryptHash;
import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User create(UserCreateDTO userDTO) {
        try {
            // hash password
            BcryptHash bcryptHash = new BcryptHash(userDTO.getPassword());

            User user = new User();
            user.setFirst_name(userDTO.getFirst_name());
            user.setLast_name(userDTO.getLast_name());
            user.setEmail(userDTO.getEmail());
            user.setPassword(bcryptHash.hash());

            return userRepository.save(user);
        } catch (Exception e) {
            throw new Error("failed to register user");
        }
    }
}
