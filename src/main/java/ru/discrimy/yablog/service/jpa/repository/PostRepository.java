package ru.discrimy.yablog.service.jpa.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import ru.discrimy.yablog.model.Post;

import java.util.List;

@Component
public interface PostRepository extends BaseRepository<Post> {
    @Query(" FROM Post WHERE pinned IS TRUE ")
    List<Post> findAllPinned();
}
