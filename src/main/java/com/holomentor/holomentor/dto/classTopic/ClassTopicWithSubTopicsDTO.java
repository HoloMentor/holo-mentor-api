package com.holomentor.holomentor.dto.classTopic;

import com.holomentor.holomentor.dto.classSubTopic.ClassSubTopicDTO;
import com.holomentor.holomentor.models.InstituteClassSubTopic;
import lombok.Data;

import java.util.List;

@Data
public class ClassTopicWithSubTopicsDTO {
    private Long id;
    private String name;
    private Long instituteId;
    private Long classId;
    private String createdAt;
    private List<ClassSubTopicDTO> subTopics;
}
