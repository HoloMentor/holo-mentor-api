package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Data
@Entity
@Table(name = "institute_subjects")
public class InstituteSubject {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(insertable = false, updatable = false)
    private Long instituteId;
    private String name;
    @Column(insertable = false, updatable = false)
    private Time createdAt;
}
