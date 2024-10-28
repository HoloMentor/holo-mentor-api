package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Data
@Entity
@Table(name = "institute_class_tiers")
public class InstituteClassTier {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private Long instituteId;
    @Column(updatable = false)
    private Long classId;
    private String name;
    private String description;
    @Column(insertable = false, updatable = false)
    private Time createdAt;
}
