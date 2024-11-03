package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Data
@Entity
@Table(name = "institute_class_sub_topics")
public class InstituteClassSubTopic {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long instituteId;
    private Long classId;
    private Long topicId;
    private Boolean isDone;
    private String name;
    @Column(insertable = false, updatable = false)
    private String createdAt;
}
