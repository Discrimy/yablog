package ru.discrimy.yablog.service.jpa.repository;

import org.springframework.stereotype.Repository;
import ru.discrimy.yablog.model.Tag;

import java.util.Optional;

@Repository
public interface TagRepository extends BaseRepository<Tag> {
    Optional<Tag> findByName(String name);
}
