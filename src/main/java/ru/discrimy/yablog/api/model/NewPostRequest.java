package ru.discrimy.yablog.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class NewPostRequest {
    private String title;
    private String text;
    private boolean pinned;
    private Set<String> tags;
}
