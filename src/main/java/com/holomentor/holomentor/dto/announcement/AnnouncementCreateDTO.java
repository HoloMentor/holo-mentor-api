package com.holomentor.holomentor.dto.announcement;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnnouncementCreateDTO {
    @NotNull(message = "institute id required")
    private String instituteId;
    @NotNull(message = "title required")
    private String title;
    @NotNull(message = "announcement required")
    private String announcement;
}
