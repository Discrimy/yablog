package ru.discrimy.yablog.service.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.model.User;

import java.util.List;

@Component
public interface PostRepository extends BaseRepository<Post> {
    List<Post> findByPinned(boolean pinned);

    default List<Post> findAllPinned() {
        return this.findByPinned(true);
    }

    Page<Post> findAllByAuthor(Pageable pageable, User user);
}
