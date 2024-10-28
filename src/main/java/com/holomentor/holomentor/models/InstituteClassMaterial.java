package com.holomentor.holomentor.models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Time;


@Data
@Entity
@Table(name = "institute_class_materials")
public class InstituteClassMaterial {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "class_id", nullable = false)
    private Long classId;
    @Column(name = "institute_id", nullable = false)
    private Long instituteId;
    @Column(name = "topic_id", nullable = false)
    private Long topicId;
    @Column(name = "sub_topic_id", nullable = false)
    private Long subTopicId;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type", nullable = false)
    private ClassMaterialType type;
    private String url;
    private String description;
    @Column(insertable = false, updatable = false)
    private String createdAt;


    public enum ClassMaterialType {
        URL,
        VIDEO,
        PDF,
        FILE
    }
}


