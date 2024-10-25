package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteClassMaterial;
import com.holomentor.holomentor.models.InstituteClassTopic;
import com.holomentor.holomentor.projections.instituteClass.InstituteClassTopicsWithSubTopicsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface InstituteClassMaterialRepository extends JpaRepository<InstituteClassMaterial, Long> {
    Optional<InstituteClassMaterial> findById(Long id);
}
