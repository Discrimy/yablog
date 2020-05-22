package ru.discrimy.yablog.service.jpa.service;

import org.springframework.stereotype.Service;
import ru.discrimy.yablog.model.Comment;
import ru.discrimy.yablog.service.CommentService;
import ru.discrimy.yablog.service.jpa.repository.CommentRepository;

@Service
public class CommentJpaService extends BaseJpaService<Comment> implements CommentService {
    public CommentJpaService(CommentRepository commentRepository) {
        super(commentRepository);
    }
}
