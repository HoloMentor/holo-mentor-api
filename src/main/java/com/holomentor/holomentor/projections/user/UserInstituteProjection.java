package com.holomentor.holomentor.projections.user;

import java.time.LocalDateTime;

public interface UserInstituteProjection {

    Long getUserInstituteId();
    Long getUserId();
    Long getInstituteId();
    Long getInstituteTeacherStaffId();
    String getRole();
    Boolean getIsBlacklisted();
    Boolean getIsActive();
    Boolean getIsDelete();
    LocalDateTime getCreatedAt();
}
