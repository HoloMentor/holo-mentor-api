package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.Institute;
import com.holomentor.holomentor.models.User;
import com.holomentor.holomentor.models.UserInstitute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInstituteRepository extends JpaRepository<UserInstitute, Long> {
    List<UserInstitute> findByInstituteId(Long id);

    List<UserInstitute> findByUserId(Long id);

    List<UserInstitute> findByUserEmail(String email);
}
