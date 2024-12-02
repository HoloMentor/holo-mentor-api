package com.holomentor.holomentor.models;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Data;

// import java.sql.Time;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

// CREATE TABLE IF NOT EXISTS "study_plan_task_submissions" (
//     id serial primary key,
//     task_id serial constraint fk_class_tier_study_plan_task references class_tier_study_plan_tasks,
//     student_id serial constraint fk_users references users,
//     institute_id serial constraint fk_institute references institutes,
//     class_id serial constraint fk_institute_class references institute_classes,
//     submission json not null,
//     created_at timestamp default CURRENT_TIMESTAMP
// );


@Data
@Entity
@Table(name = "study_plan_task_submissions")
public class StudyPlanTaskSubmission {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "class_id", nullable = false)
    private Long classId;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "institute_id", nullable = false)
    private Long instituteId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "submission", columnDefinition = "jsonb", nullable = false)
    private JsonNode submission;

}
