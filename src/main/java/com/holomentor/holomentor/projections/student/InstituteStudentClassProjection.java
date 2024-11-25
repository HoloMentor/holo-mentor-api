package com.holomentor.holomentor.projections.student;

public interface InstituteStudentClassProjection {
    Long getId();
    String getClassName();
    String getSubjectName();
    Long getTeacherId();
    String getTeacherFirstName();
    String getTeacherLastName();
    Integer getStudentCount();
}
