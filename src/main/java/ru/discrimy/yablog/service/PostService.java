package ru.discrimy.yablog.service;

import ru.discrimy.yablog.exceptions.PostNotFoundException;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.model.User;

public interface PostService extends BaseService<Post> {
    boolean hasAuthorityToEdit(User user, Post post) throws PostNotFoundException;
}
