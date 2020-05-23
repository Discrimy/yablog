package ru.discrimy.yablog.api.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PostResponse {
    private final Long id;
    private final Long createdAt;
    private final String title;
    private final String text;
    private final Long authorId;
    private final List<CommentResponse> comments;
    private final int upvotesCount;
    private final int downvotesCount;
    private final boolean pinned;
    private final Set<TagResponse> tags;
}
