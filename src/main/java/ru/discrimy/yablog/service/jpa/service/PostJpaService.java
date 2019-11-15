package ru.discrimy.yablog.service.jpa.service;

import org.springframework.stereotype.Service;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.service.PostService;
import ru.discrimy.yablog.service.jpa.repository.PostRepository;

@Service
public class PostJpaService extends BaseJpaService<Post> implements PostService {
    public PostJpaService(PostRepository repository) {
        super(repository);
    }
}
