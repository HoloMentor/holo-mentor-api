package com.holomentor.holomentor.dto.classes;

import com.holomentor.holomentor.models.InstituteClass;
import lombok.Data;
import java.sql.Time;

@Data
public class ClassUpdateDTO {
    private String className;
    private Long subjectId;
    private Long teacherId;
    private Time startTime;
    private Time endTime;
    private InstituteClass.DayOfWeek dayOfWeek;
}
