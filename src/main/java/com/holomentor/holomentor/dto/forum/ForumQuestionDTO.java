package com.holomentor.holomentor.dto.forum;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class ForumQuestionDTO {
    private Long id;
    private Long classId;
    private int voteCount;
    private String topic;
    private String subTopic;
    private JsonNode question;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;

    // Constructor matching the specified fields
    public ForumQuestionDTO(Long id, Long classId, int voteCount, String topic, String subTopic,  JsonNode question, Long userId, String firstName, String lastName, String email) {
        this.id = id;
        this.classId = classId;
        this.voteCount = voteCount;
        this.topic = topic;
        this.subTopic = subTopic;
        this.question = question;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


}
