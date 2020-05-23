package ru.discrimy.yablog.api.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.discrimy.yablog.api.model.CommentResponse;
import ru.discrimy.yablog.model.Comment;

@Component
public class CommentToCommentResponseConverter implements Converter<Comment, CommentResponse> {
    @Override
    public CommentResponse convert(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getAuthor().getId(),
                comment.getText()
        );
    }
}
