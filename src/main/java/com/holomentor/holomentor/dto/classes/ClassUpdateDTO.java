package com.holomentor.holomentor.dto.classes;

import com.holomentor.holomentor.models.InstituteClass;

import java.sql.Time;

public class ClassUpdateDTO {
    private String className;
    private Long subjectId;
    private Long teacherId;
    private Time startTime;
    private Time endTime;
    private InstituteClass.DayOfWeek dayOfWeek;
}
