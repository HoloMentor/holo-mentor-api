package com.holomentor.holomentor.models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Time;


@Data
@Entity
@Table(name = "institute_class_students")
public class InstituteClassStudents {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private Long instituteId;
    private Long instituteStudentId;
    private Long classId;
    private String registrationNumber;
    @Column(insertable = false, updatable = false)
    private Time createdAt;
}


