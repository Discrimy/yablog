package ru.discrimy.yablog.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.discrimy.yablog.model.*;
import ru.discrimy.yablog.service.CommentService;
import ru.discrimy.yablog.service.PostService;
import ru.discrimy.yablog.service.UserService;

import java.util.Set;

@Slf4j
@Component
@Profile("dev")
public class DataDevBootstrap implements CommandLineRunner {
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public DataDevBootstrap(PostService postService, CommentService commentService, UserService userService, PasswordEncoder passwordEncoder) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        log.debug("Loading test data...");

        Authority roleAdmin = new Authority("ROLE_ADMIN");

        User admin = new User(
                "admin",
                passwordEncoder.encode("admin123"),
                Set.of(roleAdmin));
        User user1 = new User(
                "user1",
                passwordEncoder.encode("user1123"),
                Set.of());

        Post post1 = new Post("First post!", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent aliquam scelerisque ipsum. Aenean porta urna vel mauris bibendum dictum. Morbi pretium convallis odio sed convallis. Ut in justo id augue consequat varius. Mauris lobortis varius ullamcorper. Mauris dignissim ex a erat dignissim, eget bibendum lacus luctus. Etiam malesuada augue id ullamcorper posuere. Nunc ipsum ligula, efficitur vel diam id, cursus accumsan metus. Aenean volutpat velit non arcu faucibus, nec pretium ipsum porta. Ut bibendum ut risus sit amet efficitur.\n" +
                "\n" +
                "Phasellus fermentum, leo a tincidunt tristique, ante nisi lobortis quam, ac facilisis turpis nunc nec odio. Morbi vulputate tortor ut mauris tempus, suscipit ultrices neque eleifend. Nulla placerat, tellus sed vulputate rutrum, erat tortor varius ligula, non rhoncus nisi orci eu nunc. Cras purus velit, rutrum vel mattis quis, vehicula eget est. Ut iaculis pellentesque enim, sed viverra tortor tempus eu. Vivamus vel hendrerit dui. Etiam dapibus a mauris in gravida. Aliquam vitae felis a dui rhoncus gravida. Vivamus ut convallis neque, non dictum urna. Vivamus placerat pulvinar auctor. Vestibulum ac aliquet nulla. Donec commodo lorem erat, vel eleifend lectus aliquam sed. Mauris massa justo, iaculis sed justo rutrum, varius laoreet arcu. Suspendisse efficitur leo quis nisl tempor pretium. Cras dapibus dolor vitae ex interdum, efficitur cursus libero venenatis. Nulla lacinia velit in nunc feugiat, ac ultrices mauris auctor.\n" +
                "\n" +
                "Vestibulum a ante purus. Ut vel eros ut velit dapibus tincidunt. Curabitur pellentesque purus eu ex hendrerit, quis auctor augue tempus. Etiam felis libero, facilisis et nulla id, scelerisque eleifend magna. Maecenas in posuere magna, gravida feugiat erat. Ut ut porttitor libero. Nulla vel luctus lacus. Sed nulla quam, pellentesque vitae erat vel, porta dictum risus. Curabitur sapien eros, eleifend a pellentesque in, eleifend ac nibh. Maecenas cursus, arcu at mattis lobortis, magna libero mattis nisi, ut eleifend magna felis et risus.\n" +
                "\n" +
                "Maecenas ut cursus ipsum. Morbi facilisis facilisis magna ac semper. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vestibulum id gravida lectus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Morbi ultrices feugiat arcu, id aliquam tortor. Nulla vitae enim id ex bibendum tempus. Nam dignissim magna a nunc porttitor, id rutrum enim euismod. Fusce euismod semper ante a aliquet. Nam vitae ligula nec erat molestie porttitor. Praesent consequat elementum dui dictum pellentesque. Quisque bibendum nisi quis erat interdum, eget vulputate nisl luctus. Aenean gravida est ligula, vitae pharetra lorem aliquet id.\n" +
                "\n" +
                "Mauris sed erat non urna sodales varius quis in nunc. Nullam et nulla enim. Phasellus tincidunt feugiat odio, et porta nibh egestas venenatis. Praesent ac laoreet arcu, vitae convallis justo. Donec fermentum bibendum ante eu volutpat. Nam at elementum massa, id ultrices orci. Aliquam feugiat pretium turpis, vel faucibus leo elementum vitae. Donec posuere justo ut sem pellentesque ultricies. Mauris a egestas massa, quis posuere augue. Vivamus egestas orci arcu, vel mollis ligula hendrerit non. Morbi suscipit dolor feugiat nisl accumsan, eget facilisis metus accumsan. Proin vehicula dui tristique, ultrices lectus sed, euismod diam.",
                admin);
        post1.setPinned(true);
        Post post2 = new Post("Second post", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin turpis risus, dapibus at augue ac, vulputate consequat lacus. Sed id sollicitudin dolor. Praesent pellentesque, dolor eu placerat placerat, risus nisi varius massa, et ornare lacus nisi sed tortor. Nulla nec sollicitudin eros, eget dapibus leo. Ut in egestas lorem. Vivamus eget fringilla purus. Nullam interdum ipsum eu est rhoncus condimentum. Etiam finibus, erat quis accumsan dapibus, ex dolor bibendum elit, non accumsan ligula arcu nec est. Nam egestas diam at finibus maximus. Vestibulum fringilla quis tortor non maximus. Aenean efficitur, nulla a molestie volutpat, mauris orci interdum urna, vitae sollicitudin tellus metus non orci. Phasellus pretium est orci, non pretium odio tristique vitae. Donec et tellus urna. Curabitur auctor blandit ipsum, pretium sodales nibh ultrices at.\n" +
                "\n" +
                "Vivamus nec odio urna. Vivamus semper vel justo sed rhoncus. Pellentesque id enim quis arcu finibus vulputate. Fusce leo arcu, elementum maximus ex at, rutrum efficitur arcu. Fusce vestibulum sem a turpis convallis, quis ullamcorper mauris egestas. Suspendisse eu tempus nibh. Pellentesque sit amet orci sit amet leo fermentum congue in non arcu. Suspendisse potenti. Aliquam tincidunt tincidunt libero, nec eleifend lacus commodo ac. Integer dui orci, consectetur et posuere sit amet, ultrices eu justo.\n" +
                "\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam neque lorem, elementum non ipsum sed, tristique iaculis eros. Sed in dolor pulvinar, mattis tellus vitae, tempor ante. Sed id blandit turpis. Morbi dictum odio ut magna tempor, quis facilisis augue dapibus. Mauris volutpat ac sapien in tempor. Cras sed bibendum massa. Duis in ullamcorper libero. Curabitur feugiat metus id ex aliquam vulputate. Donec tellus augue, commodo nec mauris eget, egestas sollicitudin magna. Sed ac maximus purus, ut dapibus neque. Vestibulum sodales leo nec bibendum lobortis.\n" +
                "\n" +
                "Nullam feugiat viverra odio, non fringilla dui. Proin sit amet metus sit amet arcu mattis sollicitudin nec vel libero. Suspendisse sed ultrices nulla. Phasellus auctor molestie orci non imperdiet. Duis sollicitudin mauris in augue aliquet, id consectetur dui eleifend. Nullam congue magna ut sagittis eleifend. Nulla consectetur odio non dictum finibus. Integer elementum molestie nulla quis blandit.\n" +
                "\n" +
                "Integer vulputate erat sed sodales ultrices. Aenean efficitur aliquet orci vitae sollicitudin. Nullam libero erat, varius eget hendrerit in, cursus sed nibh. Duis venenatis, lacus ut iaculis elementum, tellus leo malesuada lectus, sed vulputate metus diam vel mi. Integer consectetur ante sed arcu condimentum, eu sollicitudin nisi rhoncus. In magna elit, congue at diam sit amet, malesuada pretium libero. Quisque nulla tellus, porta ac porta imperdiet, lacinia finibus magna. Sed feugiat a tortor non viverra. Phasellus nec egestas sem. Etiam porta malesuada libero at volutpat. Aliquam pulvinar tincidunt feugiat.",
                user1);

        Comment comment11 = new Comment(post1, user1, "Awo1");
        Comment comment12 = new Comment(post1, user1, "Awo2");
        Comment comment13 = new Comment(post1, user1, "Awo3");
        Comment comment14 = new Comment(post1, user1, "Awo4");
        Comment comment15 = new Comment(post1, user1, "Awo5");

        userService.save(admin);
        userService.save(user1);

        postService.save(post1);
        postService.save(post2);

        commentService.save(comment11);
        commentService.save(comment12);
        commentService.save(comment13);
        commentService.save(comment14);
        commentService.save(comment15);

        for (int i = 0; i < 30; i++) {
            Post post = new Post(
                    "Lorem",
                    "Lorem ipsum dolor sit amet consectetur adipisicing elit. Sint autem sed tenetur in suscipit nam vero modi fugit ad quod.",
                    user1);
            post.getUpvotes().add(new Upvote(post, admin));
            postService.save(post);
        }

        log.debug("Loading finished");
    }
}
