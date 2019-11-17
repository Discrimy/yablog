package ru.discrimy.yablog.service.jpa.service;

import ru.discrimy.yablog.model.BaseEntity;
import ru.discrimy.yablog.service.BaseService;
import ru.discrimy.yablog.service.jpa.repository.BaseRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BaseJpaService<T extends BaseEntity> implements BaseService<T> {
    protected BaseRepository<T> repository;

    public BaseJpaService(BaseRepository<T> repository) {
        this.repository = repository;
    }


    @Override
    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Set<T> findAll() {
        return new HashSet<T>() {{
            addAll(repository.findAll());
        }};
    }

    @Override
    public T save(T data) {
        return repository.save(data);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(T data) {
        repository.delete(data);
    }
}
