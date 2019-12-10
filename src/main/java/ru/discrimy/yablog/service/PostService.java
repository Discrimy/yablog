package ru.discrimy.yablog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.discrimy.yablog.model.Post;

public interface PostService extends BaseService<Post> {
    Page<Post> findAll(Pageable pageable);
}
