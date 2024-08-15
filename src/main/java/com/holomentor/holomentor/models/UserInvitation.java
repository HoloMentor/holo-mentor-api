package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(name = "user_invitations")
public class UserInvitation {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "institute_id", nullable = false)
    private Long instituteId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "institute_id", insertable=false, updatable=false)
    private Institute institute;
    private Long userInstituteId;
    private String token;
    private Boolean isValid = false;
    @Column(insertable = false, updatable = false)
    private String createdAt;
}
