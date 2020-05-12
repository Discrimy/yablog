package ru.discrimy.yablog.service.jpa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import ru.discrimy.yablog.exceptions.PostNotFoundException;
import ru.discrimy.yablog.model.Downvote;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.model.Upvote;
import ru.discrimy.yablog.model.User;
import ru.discrimy.yablog.service.PostService;
import ru.discrimy.yablog.service.jpa.repository.PostRepository;

import java.util.List;

@Service
public class PostJpaService extends BaseJpaService<Post> implements PostService {
    public PostJpaService(PostRepository repository) {
        super(repository);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Post> findAllByAuthor(Pageable pageable, User user) {
        return ((PostRepository) repository).findAllByAuthor(pageable, user);
    }

    @Override
    public List<Post> findAllPinned() {
        return ((PostRepository) repository).findAllPinned();
    }

    @Override
    public boolean upvote(Post post, User user) {
        if (post.getUpvotes().stream()
                .anyMatch(upvote -> upvote.getUser().equals(user))) {
            return false;
        }
        Upvote upvote = new Upvote(post, user);
        post.getUpvotes().add(upvote);
        this.save(post);
        return true;
    }

    @Override
    public boolean unupvote(Post post, User user) {
        boolean removed = post.getUpvotes()
                .removeIf(upvote -> upvote.getUser().equals(user));
        this.save(post);
        return removed;
    }

    @Override
    public boolean downvote(Post post, User user) {
        if (post.getDownvotes().stream()
                .anyMatch(downvote -> downvote.getUser().equals(user))) {
            return false;
        }
        Downvote downvote = new Downvote(post, user);
        post.getDownvotes().add(downvote);
        this.save(post);
        return true;
    }

    @Override
    public boolean undownvote(Post post, User user) {
        boolean removed = post.getDownvotes()
                .removeIf(downvote -> downvote.getUser().equals(user));
        this.save(post);
        return removed;
    }
}
