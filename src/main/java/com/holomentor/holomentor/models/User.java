package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Bean;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(insertable = false, updatable = false)
    private String created_at;

    private String first_name;
    private String last_name;
    private String email;
    private String password;

}
