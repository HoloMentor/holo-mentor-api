package com.holomentor.holomentor.dto.studyPlanTask;

import com.fasterxml.jackson.databind.JsonNode;

public class StudyPlanTaskSubmission {

    private Long taskId;
    private String taskTitle;
    private JsonNode taskDescription;
    private String taskCreatedAt;
    private Long submissionId;
    private JsonNode submissionData;
    private String submissionCreatedAt;
}
