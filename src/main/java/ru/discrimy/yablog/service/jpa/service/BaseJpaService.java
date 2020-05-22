package ru.discrimy.yablog.service.jpa.service;

import ru.discrimy.yablog.model.BaseEntity;
import ru.discrimy.yablog.service.BaseService;
import ru.discrimy.yablog.service.jpa.repository.BaseRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BaseJpaService<T extends BaseEntity> implements BaseService<T> {
    protected BaseRepository<T> baseRepository;

    public BaseJpaService(BaseRepository<T> baseRepository) {
        this.baseRepository = baseRepository;
    }


    @Override
    public Optional<T> findById(Long id) {
        return baseRepository.findById(id);
    }

    @Override
    public Set<T> findAll() {
        return new HashSet<T>() {{
            addAll(baseRepository.findAll());
        }};
    }

    @Override
    public T save(T data) {
        return baseRepository.save(data);
    }

    @Override
    public void deleteById(Long id) {
        baseRepository.deleteById(id);
    }

    @Override
    public void delete(T data) {
        baseRepository.delete(data);
    }
}
