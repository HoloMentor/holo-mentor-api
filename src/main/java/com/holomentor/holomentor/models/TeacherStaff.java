package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "teacher_staff")
@Data
public class TeacherStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_staff_id", nullable = false)
    private Long userStaffId;

    @Column(name = "user_teacher_id", nullable = false)
    private Long userTeacherId;

    @Column(name = "user_institute_staff_id", nullable = false)
    private Long userInstituteStaffId;

    @Column(name = "user_institute_teacher_id", nullable = false)
    private Long userInstituteTeacherId;

    @Column(name = "institute_id", nullable = false)
    private Long instituteId;

    @Column(name = "is_delete", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDelete = false;

    @Column(name = "created_at", columnDefinition = "timestamp with time zone default CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
