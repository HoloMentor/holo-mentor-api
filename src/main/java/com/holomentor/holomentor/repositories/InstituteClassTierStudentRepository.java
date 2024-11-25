package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteClassTierStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface InstituteClassTierStudentRepository extends JpaRepository<InstituteClassTierStudent, Long> {
    Optional<InstituteClassTierStudent> findById(Long id);

    List<InstituteClassTierStudent> findByClassId(Long id);
}
