package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Data
@Entity
@Table(name = "institute_class_topics")
public class InstituteClassTopic {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long instituteId;
    private Long classId;
    private String name;
    @Column(insertable = false, updatable = false)
    private String createdAt;
}
