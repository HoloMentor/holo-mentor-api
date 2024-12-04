package com.holomentor.holomentor.services;

import com.holomentor.holomentor.dto.announcement.AnnouncementCreateDTO;
import com.holomentor.holomentor.models.InstituteAnnouncement;
import com.holomentor.holomentor.repositories.AnnouncementRepository;
import com.holomentor.holomentor.utils.Response;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    public ResponseEntity<Object> create(AnnouncementCreateDTO body) throws IOException {
        InstituteAnnouncement instituteAnnouncement = new InstituteAnnouncement();

        instituteAnnouncement.setAnnouncement(body.getAnnouncement());
        instituteAnnouncement.setTitle(body.getTitle());
        instituteAnnouncement.setInstituteId(Integer.valueOf(body.getInstituteId()));
        instituteAnnouncement.setCreatedAt(LocalDateTime.now());

        announcementRepository.save(instituteAnnouncement);

        return Response.generate("announcement added successfully.", HttpStatus.OK);
    }

    public ResponseEntity<Object> getAllbyInstituteId(Long instituteId){
        List<InstituteAnnouncement> announcements = announcementRepository.findByInstituteIdOrderByIdDesc(instituteId);
        return Response.generate("announcements found.", HttpStatus.OK, announcements);
    }

    public ResponseEntity<Object> deleteById(Long id){
        announcementRepository.deleteById(id);
        return Response.generate("announcement deleted successfully.", HttpStatus.OK);
    }
}
