package com.holomentor.holomentor.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(name = "class_tier_study_plan_tasks")
public class ClassTierStudyPlanTask {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long instituteId;
    private Long classId;
    private Long studyPlanId;
    private String title;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "description", columnDefinition = "jsonb", nullable = false)
    private JsonNode description;
    @Column(insertable = false, updatable = false)
    private String createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyPlanId", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private ClassTierStudyPlan studyPlan;
}
