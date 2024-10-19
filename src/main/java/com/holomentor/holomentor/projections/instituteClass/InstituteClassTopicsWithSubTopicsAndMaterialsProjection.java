package com.holomentor.holomentor.projections.instituteClass;

import com.holomentor.holomentor.models.InstituteClassMaterial;
import com.holomentor.holomentor.models.InstituteClassSubTopic;

import java.util.List;

public interface InstituteClassTopicsWithSubTopicsAndMaterialsProjection {
    Long getId();
    Long getInstituteId();
    Long getClassId();
    String getName();
    String getCreatedAt();
    List<InstituteClassSubTopic> getSubTopics();
    List<InstituteClassMaterial> getMaterials();
}
