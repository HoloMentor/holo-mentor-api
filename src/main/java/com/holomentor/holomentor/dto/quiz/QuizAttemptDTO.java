package com.holomentor.holomentor.dto.quiz;

public class QuizAttemptDTO {
    private Long quizId;
    private Long userId;
    private String attemptStartedAt;
    
    // Getters and setters
    public Long getQuizId() {
        return quizId;
    }
    
    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getAttemptStartedAt() {
        return attemptStartedAt;
    }
    
    public void setAttemptStartedAt(String attemptStartedAt) {
        this.attemptStartedAt = attemptStartedAt;
    }
}