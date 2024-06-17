package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.user.UserCreateDTO;
import com.holomentor.holomentor.dto.user.UserLoginDTO;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.repositories.UserRepository;
import com.holomentor.holomentor.utils.BcryptHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public String login(UserLoginDTO request){

        Optional<User>  TempUser = userRepository.findByEmail(request.getUsername());
        if(TempUser.isEmpty()){
            throw new IllegalStateException("Email not exist");
        }

        User user = TempUser.get();
        BcryptHash bcryptHash = new BcryptHash(request.getPassword());

        if(!bcryptHash.verify(user.getPassword()))
        {
            throw new IllegalStateException("Invalid password");
        }
        return "login success";
    }
}
