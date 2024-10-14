package com.holomentor.holomentor.models;

import org.springframework.stereotype.Component;

@Component
public class SendGridMail {
    public String getTemplateID(TemplateNames templateName) {
        switch (templateName) {
            case INSTITUTE_REGISTRATION:
                return "d-b3dc10a0c47043109f85ba471b875c01";
            case TEACHER_REGISTRATION:
                return "d-b3dc10a0c47043109f85ba471b875c01";
            default:
                return null;
        }
    }

    public enum TemplateNames {
        INSTITUTE_REGISTRATION,
        TEACHER_REGISTRATION,
    }
}
