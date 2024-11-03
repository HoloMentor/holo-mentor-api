package com.holomentor.holomentor.dto.classMaterials;

import com.holomentor.holomentor.models.InstituteClassMaterial;
import lombok.Data;

import java.util.List;

@Data
public class ClassSubTopicMaterialDTO {
    private Long id;
    private String url;
    private InstituteClassMaterial.ClassMaterialType type;
}
