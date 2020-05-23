package ru.discrimy.yablog.api.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.discrimy.yablog.api.model.CommentResponse;
import ru.discrimy.yablog.api.model.PostResponse;
import ru.discrimy.yablog.api.model.TagResponse;
import ru.discrimy.yablog.model.Post;

import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Component
public class PostToPostResponseConverter implements Converter<Post, PostResponse> {
    private final CommentToCommentResponseConverter commentConverter;
    private final TagToTagResponseConverter tagConverter;

    public PostToPostResponseConverter(
            CommentToCommentResponseConverter commentConverter,
            TagToTagResponseConverter tagConverter) {
        this.commentConverter = commentConverter;
        this.tagConverter = tagConverter;
    }

    @Override
    public PostResponse convert(Post post) {
        return new PostResponse(
                post.getId(),
                post.getCreatedAt().toEpochSecond(ZoneOffset.UTC),
                post.getTitle(),
                post.getText(),
                post.getAuthor().getId(),
                post.getComments().stream()
                        .map(commentConverter::convert)
                        .collect(Collectors.toList()),
                post.getUpvotes().size(),
                post.getDownvotes().size(),
                post.isPinned(),
                post.getTags().stream()
                        .map(tagConverter::convert)
                        .collect(Collectors.toSet())
        );
    }
}
