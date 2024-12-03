package com.holomentor.holomentor.dto.classes;

import com.holomentor.holomentor.models.InstituteClass;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Time;

@Data
public class ClassCreateDTO {
    @NotNull(message = "class name is required")
    private String className;
    @NotNull(message = "institute ID is required")
    private Long instituteId;
    @NotNull(message = "subject ID is required")
    private Long subjectId;
    @NotNull(message = "teacher ID is required")
    private Long teacherId;
    @NotNull(message = "start time is required")
    private Time startTime;
    @NotNull(message = "end time is required")
    private Time endTime;
    @NotNull(message = "day of the week is required")
    private InstituteClass.DayOfWeek dayOfWeek;
}
