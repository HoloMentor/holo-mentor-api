package com.holomentor.holomentor.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "institutes")
public class Institute {

    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String logo = null;
    private Boolean isBlacklisted = false;
    @Column(insertable = false, updatable = false)
    private String createdAt;

}
