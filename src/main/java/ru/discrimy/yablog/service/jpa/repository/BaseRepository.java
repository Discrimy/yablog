package ru.discrimy.yablog.service.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.discrimy.yablog.model.BaseEntity;

public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
