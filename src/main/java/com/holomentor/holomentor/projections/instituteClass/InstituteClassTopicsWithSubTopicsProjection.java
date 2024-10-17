package com.holomentor.holomentor.projections.instituteClass;

import com.holomentor.holomentor.models.InstituteClassSubTopic;

import java.sql.Time;
import java.util.List;

public interface InstituteClassTopicsWithSubTopicsProjection {
    Long getId();
    Long getInstituteId();
    Long getClassId();
    String getName();
    String getCreatedAt();
    List<InstituteClassSubTopic> getSubTopics();
}
