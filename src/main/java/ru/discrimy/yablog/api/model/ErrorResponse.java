package ru.discrimy.yablog.api.model;

import lombok.Data;

@Data
public class ErrorResponse {
    private final String type;
    private final Long code;
    private final String reason;
}
