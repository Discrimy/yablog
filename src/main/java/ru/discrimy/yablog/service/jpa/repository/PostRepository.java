package ru.discrimy.yablog.service.jpa.repository;

import org.springframework.stereotype.Component;
import ru.discrimy.yablog.model.Post;

import java.util.List;

@Component
public interface PostRepository extends BaseRepository<Post> {
    List<Post> findByPinned(boolean pinned);

    default List<Post> findAllPinned() {
        return this.findByPinned(true);
    }
}
