package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.Institute;
import com.holomentor.holomentor.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface InstituteRepository extends JpaRepository<Institute, Long> {
}
