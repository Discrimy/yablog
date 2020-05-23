package ru.discrimy.yablog.api.model;

import lombok.Data;

@Data
public class CommentResponse {
    private final Long id;
    private final Long authorId;
    private final String text;
}
