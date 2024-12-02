package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "institute_announcements")
public class InstituteAnnouncement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "institute_id", nullable = false)
    private Integer instituteId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "announcement", nullable = false)
    private String announcement;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
