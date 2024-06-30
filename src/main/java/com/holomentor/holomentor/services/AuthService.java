package com.holomentor.holomentor.services;

import com.holomentor.holomentor.config.JwtGenerator;
import com.holomentor.holomentor.dto.auth.AuthInstitutesDTO;
import com.holomentor.holomentor.dto.auth.AuthLoginDTO;
import com.holomentor.holomentor.dto.auth.AuthRegisterDTO;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.models.UserInstitute;
import com.holomentor.holomentor.repositories.UserInstituteRepository;
import com.holomentor.holomentor.repositories.UserRepository;
import com.holomentor.holomentor.utils.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private UserInstituteRepository userInstituteRepository;
    @Autowired
    private ModelMapper modelMapper;

    public User create(AuthRegisterDTO userDTO) {
        try {
            User user = new User();
            user.setFirstName(userDTO.getFirst_name());
            user.setLastName(userDTO.getLast_name());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            return userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Error("failed to register user");
        }
    }

    public ResponseEntity<Object> login(AuthLoginDTO request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserInstituteID(),
                        request.getPassword()));

        Optional<UserInstitute> userInstitute = userInstituteRepository.findById(request.getUserInstituteID());
        if(userInstitute.isEmpty()){
            throw new UsernameNotFoundException("user is not registered to the institute");
        }

        var accessToken = jwtGenerator.generateAccessToken(userInstitute.get());
        var refreshToken = jwtGenerator.generateRefreshToken(userInstitute.get());

        Map<String,String> data = new HashMap<String, String>();
        data.put("id", userInstitute.get().getId().toString());
        data.put("first_name", userInstitute.get().getUser().getFirstName());
        data.put("last_name", userInstitute.get().getUser().getLastName());
        data.put("email", userInstitute.get().getUser().getEmail());
        data.put("access_token", accessToken);

        return Response.generate("login successful", HttpStatus.OK, data);
    }

    public ResponseEntity<Object> institutes(String email) {
        List<UserInstitute> userInstitutes = userInstituteRepository.findByUserEmail(email);

        Map<String, Object> data = new HashMap<>();
        data.put("institutes", userInstitutes.stream().map(src -> {
            Map<String, Object> inner = new HashMap<>();
            inner.put("id", src.getId().toString());
            inner.put("instituteId", src.getInstituteId().toString());
            inner.put("name", src.getInstitute().getName());

            return inner;
        }));

        return Response.generate("success", HttpStatus.FOUND, data);
    }
}
