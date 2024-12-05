package com.holomentor.holomentor.projections.institute;

public interface InstituteDetailsProjection {
    Long getId();
    String getInstituteName();
    String getInstituteImage();
    String getInstituteCity();
    Long getClassCount();
    Long getStudentCount();
    Long getTeacherCount();
}
