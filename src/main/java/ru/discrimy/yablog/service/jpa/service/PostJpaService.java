package ru.discrimy.yablog.service.jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.service.PostService;
import ru.discrimy.yablog.service.jpa.repository.PostRepository;

@Service
public class PostJpaService extends BaseJpaService<Post> implements PostService {
    public PostJpaService(PostRepository repository) {
        super(repository);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
