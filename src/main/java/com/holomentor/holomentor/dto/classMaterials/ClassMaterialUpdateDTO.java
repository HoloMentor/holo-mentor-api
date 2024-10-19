package com.holomentor.holomentor.dto.classMaterials;

import com.holomentor.holomentor.models.InstituteClassMaterial;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClassMaterialUpdateDTO {
    @NotNull(message = "topic ID is required")
    private Long topicId;
    @NotNull(message = "sub topic ID is required")
    private Long subTopicId;
    @NotNull(message = "type of the data is required")
    private InstituteClassMaterial.ClassMaterialType type;
    @NotNull(message = "url for the resource is required")
    private String url;
}
