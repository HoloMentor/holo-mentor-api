package com.holomentor.holomentor.dto.classSubTopic;

import com.holomentor.holomentor.models.InstituteClassMaterial;
import lombok.Data;

import java.util.List;

@Data
public class ClassSubTopicWithMaterialsDTO {
    private Long id;
    private String name;
    private List<InstituteClassMaterial> materials;
}
