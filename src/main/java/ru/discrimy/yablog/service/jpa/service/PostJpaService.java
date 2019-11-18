package ru.discrimy.yablog.service.jpa.service;

import org.springframework.stereotype.Service;
import ru.discrimy.yablog.exceptions.PostNotFoundException;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.model.User;
import ru.discrimy.yablog.service.PostService;
import ru.discrimy.yablog.service.jpa.repository.PostRepository;

@Service
public class PostJpaService extends BaseJpaService<Post> implements PostService {
    public PostJpaService(PostRepository repository) {
        super(repository);
    }

    @Override
    public boolean hasAuthorityToEdit(User user, Post post) {
        if (post.getId() != null) {
            User originAuthor = findById(post.getId())
                    .orElseThrow(PostNotFoundException::new)
                    .getAuthor();

            return originAuthor.getUsername().equals(user.getUsername());
        }
        return true;
    }

    @Override
    public boolean hasAuthorityToDelete(User user, Post post) throws PostNotFoundException {
        if (post.getId() != null) {
            User originAuthor = findById(post.getId())
                    .orElseThrow(PostNotFoundException::new)
                    .getAuthor();

            return originAuthor.getUsername().equals(user.getUsername());
        }
        return true;
    }
}
