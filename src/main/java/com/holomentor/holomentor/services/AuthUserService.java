package com.holomentor.holomentor.services;

import com.holomentor.holomentor.models.AuthUser;
import com.holomentor.holomentor.models.UserInstitute;
import com.holomentor.holomentor.repositories.UserInstituteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthUserService implements UserDetailsService {
    @Autowired
    private UserInstituteRepository userInstituteRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        System.out.println(Long.parseLong(id));
        UserInstitute userInstitute = userInstituteRepository.findById(Long.parseLong(id)).orElseThrow();

        AuthUser authUser = new AuthUser();
        authUser.setId(userInstitute.getId());
        authUser.setUserId(userInstitute.getUserId());
        authUser.setInstituteId(userInstitute.getInstituteId());
        authUser.setEmail(userInstitute.getUser().getEmail());
        authUser.setFirstName(userInstitute.getUser().getFirstName());
        authUser.setLastName(userInstitute.getUser().getLastName());
        authUser.setPassword(userInstitute.getUser().getPassword());
        authUser.setRole(String.valueOf(userInstitute.getRole()));

        return authUser;
    }
}
