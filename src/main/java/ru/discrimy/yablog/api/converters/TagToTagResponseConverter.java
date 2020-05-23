package ru.discrimy.yablog.api.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.discrimy.yablog.api.model.TagResponse;
import ru.discrimy.yablog.model.Tag;

@Component
public class TagToTagResponseConverter implements Converter<Tag, TagResponse> {
    @Override
    public TagResponse convert(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }
}
