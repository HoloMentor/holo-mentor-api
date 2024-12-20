package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Data
@Entity
@Table(name = "institute_class_tier_students")
public class InstituteClassTierStudent {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private Long instituteId;
    @Column(updatable = false)
    private Long classId;
    @Column(updatable = false)
    private Long studentId;
    @Column(insertable = false, updatable = false)
    private Time createdAt;
    private String tier;
}
