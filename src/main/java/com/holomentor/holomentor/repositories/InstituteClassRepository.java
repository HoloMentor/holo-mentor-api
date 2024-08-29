package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface InstituteClassRepository extends JpaRepository<InstituteClass, Long> {

    Optional<InstituteClass> findByClassName(String name);

    Optional<InstituteClass> findById(Long id);

}
