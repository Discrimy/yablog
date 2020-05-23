package ru.discrimy.yablog.service;

import ru.discrimy.yablog.model.Tag;

import java.util.Optional;

public interface TagService extends BaseService<Tag> {
    Optional<Tag> findByName(String name);
}
