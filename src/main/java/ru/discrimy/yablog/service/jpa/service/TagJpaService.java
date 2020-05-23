package ru.discrimy.yablog.service.jpa.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.discrimy.yablog.model.Tag;
import ru.discrimy.yablog.service.TagService;
import ru.discrimy.yablog.service.jpa.repository.BaseRepository;
import ru.discrimy.yablog.service.jpa.repository.TagRepository;

import java.util.Optional;

@Service
public class TagJpaService extends BaseJpaService<Tag> implements TagService {
    private final TagRepository tagRepository;

    public TagJpaService(BaseRepository<Tag> baseRepository, TagRepository tagRepository) {
        super(baseRepository);
        this.tagRepository = tagRepository;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }
}
