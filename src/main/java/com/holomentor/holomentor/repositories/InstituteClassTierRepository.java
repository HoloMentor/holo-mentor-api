package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteClassTier;
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
public interface InstituteClassTierRepository extends JpaRepository<InstituteClassTier, Long> {
    Optional<InstituteClassTier> findById(Long id);

    List<InstituteClassTier> findByClassId(Long id);
}
