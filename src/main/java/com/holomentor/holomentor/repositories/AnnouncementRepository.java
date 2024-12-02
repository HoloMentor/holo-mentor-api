package com.holomentor.holomentor.repositories;

import com.holomentor.holomentor.models.InstituteAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AnnouncementRepository extends JpaRepository<InstituteAnnouncement, Long> {

    List<InstituteAnnouncement> findByInstituteIdOrderByIdDesc(Long id);
    //List<InstituteAnnouncement> findByInstituteIdOrderByCreatedDateDesc(Long instituteId);
}
