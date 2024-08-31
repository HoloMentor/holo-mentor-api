package com.holomentor.holomentor.projections.teacher;

import com.holomentor.holomentor.models.Institute;
import com.holomentor.holomentor.models.InstituteClass;
import com.holomentor.holomentor.models.InstituteSubject;

import java.sql.Time;
import java.sql.Timestamp;

public interface InstituteTeacherClassProjection {
    Long getId();
    String getClassName();
    Long getTeacherId();
    Long getInstituteId();
    Long getSubjectId();
    Time getStartTime();
    Time getEndTime();
    InstituteClass.DayOfWeek getDayOfWeek();
    Timestamp getCreatedAt();
    Institute getInstitute();
    InstituteSubject getSubject();
}
