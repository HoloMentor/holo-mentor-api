package com.holomentor.holomentor.projections.instituteClass;

public interface ClassDetailsProjection {
    Long getClassId();
    String getClassName();
    String getTeacherFirstName();
    String getTeacherLastName();
    String getInstituteName();
    Long getStudentCount();
}
