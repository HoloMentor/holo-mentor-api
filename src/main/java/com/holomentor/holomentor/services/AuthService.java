package com.holomentor.holomentor.services;

import com.holomentor.holomentor.config.JwtGenerator;
import com.holomentor.holomentor.dto.auth.AuthLoginDTO;
import com.holomentor.holomentor.dto.auth.AuthRegisterDTO;
import com.holomentor.holomentor.models.Institute;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.models.UserInstitute;
import com.holomentor.holomentor.repositories.InstituteRepository;
import com.holomentor.holomentor.repositories.UserInstituteRepository;
import com.holomentor.holomentor.repositories.UserRepository;
import com.holomentor.holomentor.utils.Response;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.NotActiveException;
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
    private Environment env;
    @Autowired
    private InstituteRepository instituteRepository;

    public ResponseEntity<Object> create(AuthRegisterDTO userDTO) {
        try {
            User user = new User();
            user.setFirstName(userDTO.getFirst_name());
            user.setLastName(userDTO.getLast_name());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            userRepository.save(user);

            return Response.generate("user registration successful", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Error("failed to register user");
        }
    }

    public ResponseEntity<Object> createSuperAdmin() {
        try {
            Optional<User> userExists = userRepository.findByEmail(env.getProperty("env.super.email"));
//           check if the user exists
            if(userExists.isPresent()){
                return Response.generate("user already exists", HttpStatus.CONFLICT);
            }

            Institute institute = new Institute();
            institute.setName(env.getProperty("env.super.institute"));
            instituteRepository.save(institute);

            User user = new User();
            user.setFirstName(env.getProperty("env.super.first_name"));
            user.setLastName(env.getProperty("env.super.last_name"));
            user.setEmail(env.getProperty("env.super.email"));
            user.setRole(User.RoleTypes.SUPER_ADMIN);
            user.setPassword(passwordEncoder.encode(env.getProperty("env.super.password")));
            userRepository.save(user);

            UserInstitute userInstitute = new UserInstitute();
            userInstitute.setInstituteId(institute.getId());
            userInstitute.setUserId(user.getId());
            userInstitute.setRole(UserInstitute.RoleTypes.INSTITUTE);
            userInstituteRepository.save(userInstitute);

            return Response.generate("super admin creation successful", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Error("failed to create super admin");
        }
    }

    public ResponseEntity<Object> login(AuthLoginDTO request, HttpServletResponse response) throws UsernameNotFoundException, NotActiveException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserInstituteID(),
                        request.getPassword()));

        Optional<UserInstitute> userInstitute = userInstituteRepository.findById(request.getUserInstituteID());
        if(userInstitute.isEmpty()){
            throw new UsernameNotFoundException("user is not registered to the institute");
        }
//        is user is not active
        if(!userInstitute.get().getIsActive()){
            throw new NotActiveException("user account is not active");
        }

        var accessToken = jwtGenerator.generateAccessToken(userInstitute.get());
        var refreshToken = jwtGenerator.generateRefreshToken(userInstitute.get());

//        set refresh token cookie
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        response.addCookie(refreshCookie);

        Map<String,String> data = new HashMap<String, String>();
        data.put("user_institute_id", userInstitute.get().getId().toString());
        data.put("user_id", userInstitute.get().getUser().getId().toString());
        data.put("institute_id", userInstitute.get().getInstitute().getId().toString());
        data.put("first_name", userInstitute.get().getUser().getFirstName());
        data.put("last_name", userInstitute.get().getUser().getLastName());
        data.put("email", userInstitute.get().getUser().getEmail());
        data.put("user_role", userInstitute.get().getUser().getRole().toString());
        data.put("institute_role", userInstitute.get().getRole().toString());
        data.put("access_token", accessToken);

        return Response.generate("login successful", HttpStatus.OK, data);
    }

    public ResponseEntity<Object> authorize(Long id){
        Optional<UserInstitute> userInstitute = userInstituteRepository.findById(id);
        if(userInstitute.isEmpty()){
            throw new UsernameNotFoundException("user is not registered to the institute");
        }

        Map<String,String> data = new HashMap<String, String>();
        data.put("user_institute_id", userInstitute.get().getId().toString());
        data.put("user_id", userInstitute.get().getUser().getId().toString());
        data.put("institute_id", userInstitute.get().getInstitute().getId().toString());
        data.put("first_name", userInstitute.get().getUser().getFirstName());
        data.put("last_name", userInstitute.get().getUser().getLastName());
        data.put("email", userInstitute.get().getUser().getEmail());
        data.put("user_role", userInstitute.get().getUser().getRole().toString());
        data.put("institute_role", userInstitute.get().getRole().toString());

        return Response.generate("user institute details found", HttpStatus.OK, data);
    }

    public ResponseEntity<Object> institutes(String email) {
        Optional<User> user = userRepository.findByEmail(email);
//        check if the user exists
        if(user.isEmpty()){
            return Response.generate("user not registered to the system", HttpStatus.NOT_FOUND);
        }
//        get user registered institutes
        List<UserInstitute> userInstitutes = userInstituteRepository.findByUserEmail(email);

        Map<String, Object> data = new HashMap<>();
        data.put("institutes", userInstitutes.stream().map(src -> {
            Map<String, Object> inner = new HashMap<>();
            inner.put("id", src.getId());
            inner.put("instituteId", src.getInstituteId());
            inner.put("name", src.getInstitute().getName());

            return inner;
        }));

        return Response.generate("success", HttpStatus.OK, data);
    }
}
