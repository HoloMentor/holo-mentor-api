package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "class_tier_study_plans")
public class ClassTierStudyPlan {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long instituteId;
    private Long classId;
    private Integer tier;
    private String name;
    private String description;
    @Column(insertable = false, updatable = false)
    private String createdAt;

    @OneToMany(mappedBy = "studyPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClassTierStudyPlanTask> tasks;
}
