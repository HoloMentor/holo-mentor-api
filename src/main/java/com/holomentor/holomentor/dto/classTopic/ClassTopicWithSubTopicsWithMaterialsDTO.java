package com.holomentor.holomentor.dto.classTopic;

import com.holomentor.holomentor.dto.classSubTopic.ClassSubTopicWithMaterialsDTO;
import lombok.Data;

import java.util.List;

@Data
public class ClassTopicWithSubTopicsWithMaterialsDTO {
    private Long id;
    private String name;
    private Long instituteId;
    private Long classId;
    private String createdAt;
    private List<ClassSubTopicWithMaterialsDTO> subTopics;
}
