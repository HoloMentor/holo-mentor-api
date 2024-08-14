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
    private Long userId;
    private Long instituteId;
    private Long userInstituteId;
    private String token;
    private Boolean isValid = false;
    @Column(insertable = false, updatable = false)
    private String createdAt;
}
