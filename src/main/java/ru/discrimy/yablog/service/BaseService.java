package ru.discrimy.yablog.service;

import ru.discrimy.yablog.model.BaseEntity;

import java.util.Optional;
import java.util.Set;

public interface BaseService<T extends BaseEntity> {
    Optional<T> findById(Long id);

    Set<T> findAll();

    T save(T data);

    void deleteById(Long id);

    void delete(T data);
}
