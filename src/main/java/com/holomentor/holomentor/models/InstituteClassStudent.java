package com.holomentor.holomentor.models;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

// CREATE TABLE institute_class_students
//   (
//      id                   SERIAL PRIMARY KEY,
//      student_id           SERIAL CONSTRAINT fk_user REFERENCES users,
//      institute_id         SERIAL CONSTRAINT fk_institute REFERENCES institutes,
//      institute_student_id SERIAL CONSTRAINT fk_institute_user REFERENCES
//      user_institutes,
//      class_id             SERIAL CONSTRAINT fk_institute_class REFERENCES
//      institute_classes,
//      registration_number  VARCHAR(255) NOT NULL,
//      created_at           TIMESTAMP WITH time zone DEFAULT CURRENT_TIMESTAMP,
//      tier                 VARCHAR(50),
//      tier_last_updated_at TIMESTAMP
//   ); 

@Data
@Entity
@Table(name = "institute_class_students")
public class InstituteClassStudent {
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
    private String createdAt;

    private Integer tier;
}


