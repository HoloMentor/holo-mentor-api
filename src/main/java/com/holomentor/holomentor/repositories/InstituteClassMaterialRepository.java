package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteClassMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface InstituteClassMaterialRepository extends JpaRepository<InstituteClassMaterial, Long> {
    Optional<InstituteClassMaterial> findById(Long id);

    void deleteByTopicId(Long id);

    void deleteBySubTopicId(Long id);
}
