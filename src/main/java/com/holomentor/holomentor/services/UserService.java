package com.holomentor.holomentor.services;

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

    public User create(User user) {
        // hash password
        BcryptHash bcryptHash = new BcryptHash(user.getPassword());
        user.setPassword(bcryptHash.hash());

        return userRepository.save(user);
    }
}
