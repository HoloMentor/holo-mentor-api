package com.holomentor.holomentor.models;

import org.springframework.stereotype.Component;

@Component
public class SendGridMail {
    public String getTemplateID(TemplateNames templateName) {
        switch (templateName) {
            case INSTITUTE_REGISTRATION:
                return "d-b3dc10a0c47043109f85ba471b875c01";
            case TEACHER_REGISTRATION:
                return "d-548088dc648f48949eb86fe9a10125b3";
            case STAFF_REGISTRATION:
                return "d-b220d2650b964797af5a4ac92da3697a";
            case STUDENT_REGISTRATION:
                return "d-1eb9b0aa7aff41eb9127fcf08fd0e57b";
            default:
                return null;
        }
    }

    public enum TemplateNames {
        INSTITUTE_REGISTRATION,
        TEACHER_REGISTRATION,
        STAFF_REGISTRATION,
        STUDENT_REGISTRATION,
    }
}
