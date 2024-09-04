package com.holomentor.holomentor.models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Time;



@Data
@Entity
@Table(name = "institute_classes")
public class InstituteClass {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long teacherId;
    @Column(name = "institute_id", nullable = false)
    private Long instituteId;
    private Long instituteTeacherId;
    @Column(name = "subject_id", nullable = false)
    private Long subjectId;
    private String className;
    private Time startTime;
    private Time endTime;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;
    @Column(insertable = false, updatable = false)
    private Time createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "institute_id", insertable=false, updatable=false)
    private Institute institute;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", insertable=false, updatable=false)
    private InstituteSubject subject;


    public enum DayOfWeek {
        MON,
        TUE,
        WED,
        THU,
        FRI,
        SAT,
        SUN
    }
}


