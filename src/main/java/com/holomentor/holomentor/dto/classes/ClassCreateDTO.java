package com.holomentor.holomentor.dto.classes;

import com.holomentor.holomentor.models.InstituteClass;
import lombok.Data;

import java.sql.Time;

@Data
public class ClassCreateDTO {
    private String className;
    private Long instituteId;
    private Long subjectId;
    private Long teacherId;
    private Long instituteTeacherId;
    private Time startTime;
    private Time endTime;
    private InstituteClass.DayOfWeek dayOfWeek;
}
