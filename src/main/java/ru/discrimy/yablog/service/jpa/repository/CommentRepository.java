package ru.discrimy.yablog.service.jpa.repository;

import org.springframework.stereotype.Component;
import ru.discrimy.yablog.model.Comment;

@Component
public interface CommentRepository extends BaseRepository<Comment> {
}
