package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(name = "teacher-staff")
public class TeacherStaff {
    @Id
    @Column(insertable = false, updatable = false)
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
    private Boolean isDeleted = false;
    @Column(insertable = false, updatable = false)
    private String createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_staff_id", insertable=false, updatable=false)
    private User staffUser;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_teacher_id", insertable=false, updatable=false)
    private User teacherUser;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_institute_staff_id", insertable=false, updatable=false)
    private User instituteStaff;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_institute_teacher_id", insertable=false, updatable=false)
    private User instituteteacher;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "institute_id", insertable=false, updatable=false)
    private Institute institute;

    public enum RoleTypes {
        STUDENT,
        TEACHER,
        STAFF,
        INSTITUTE
    }
}