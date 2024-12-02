package com.holomentor.holomentor.projections.instituteClass;

public interface InstituteClassStudentCountProjection {

    Long getClassId();
    String getClassName();
    Long getSubjectId();
    Long getStudentCount();
}
