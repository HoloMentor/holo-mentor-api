package com.holomentor.holomentor.projections.instituteClass;

import com.holomentor.holomentor.models.InstituteClassMaterial;

import java.util.List;

public interface InstituteClassTopicsWithSubTopicsProjection {
    Long getId();
    Long getInstituteId();
    Long getClassId();
    String getName();
    String getCreatedAt();
    Long getSubTopicId();
    String getSubTopicName();
    Long getMaterialId();
    InstituteClassMaterial.ClassMaterialType getMaterialType();
    String getMaterialUrl();
}
