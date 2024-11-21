package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(name = "user_institutes")
public class UserInstitute {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "institute_id", nullable = false)
    private Long instituteId;
    private Boolean isActive = false;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "role", nullable = false)
    private RoleTypes role = RoleTypes.STUDENT;
    @Column(insertable = false, updatable = false)
    private String createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "institute_id", insertable=false, updatable=false)
    private Institute institute;


    public enum RoleTypes {
        STUDENT,
        TEACHER,
        STAFF,
        INSTITUTE,

    }
}
