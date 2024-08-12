package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table(name = "institutes")
public class Institute {

    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String address;
    private String registrationNumber;
    private Date establishedDate;
    private String logo = null;
    private Boolean isBlacklisted = false;
    @Column(insertable = false, updatable = false)
    private String createdAt;

}
