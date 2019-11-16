package ru.discrimy.yablog.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.discrimy.yablog.model.Comment;
import ru.discrimy.yablog.model.Post;
import ru.discrimy.yablog.model.User;
import ru.discrimy.yablog.service.CommentService;
import ru.discrimy.yablog.service.PostService;
import ru.discrimy.yablog.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;

@Slf4j
@Component
public class DataDevBootstrap implements CommandLineRunner {
    private PostService postService;
    private CommentService commentService;
    private UserService userService;

    public DataDevBootstrap(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("Loading test data...");

        User admin = new User("admin", "hashed_password", new HashSet<>());
        User user1 = new User("user1", "hashed_1234", new HashSet<>());

        Post post1 = new Post("First post!", "# Refugit inane\n" +
                "\n" +
                "## Quoque Est est saepe generosam inmemor vincinaque\n" +
                "\n" +
                "Lorem markdownum funesto poterat honorum i serta, texitur fontibus adfuit. Vir\n" +
                "cum, o arcere ut pectore *Cytherea amborum suprema*: tantum procul.\n" +
                "\n" +
                "## Quem vanum in gerunt quo vapore huius\n" +
                "\n" +
                "Talaria visaeque. Undas plangorem aversa necem sis foliis quam, ille quae gente\n" +
                "dicta albus. Foedera nymphae flammas: ardet nescio manet, ad campo. Facta bos\n" +
                "tempestiva effugere aestu: cum plurima, *flammis nascentur* cepit! In signa,\n" +
                "*corpus mihi*, sine invisosque nomen.\n" +
                "\n" +
                "Inque lacesse medullis austroque in undam terga. Quod accinctus, in ignis.\n" +
                "Cessant frustra umbrarumque regem, cave silet cum it ad. Rem disponunt sub.\n" +
                "\n" +
                "## Mihi fluctibus puer libentius\n" +
                "\n" +
                "Modo et nescit parcere tum tu scelus inrita erat, ostendere dives versi! Sit\n" +
                "fletus, proceresque haec sorores scopulum Myrrha per marmoris sic ortus durum;\n" +
                "hunc, secum non Perseus. *Vacuum invia ipse* tellus, perque, Aeolidis traharis:\n" +
                "sic et iterum vitiataque arguit fecit, Erinys nam angues. Solem eburno Circes.\n" +
                "Manibus loco opem ex mutabile [ego novi vulnere](http://ab.io/qui.html) corpora\n" +
                "placatoque cessura *signa dilectae*!\n" +
                "\n" +
                "## Reliquit poscuntque recto inpavidus exiguo capiat quoque\n" +
                "\n" +
                "Humanam non *est* pedes quodque fulmen quidem laniataque astris, ait. Laeva arva\n" +
                "quam ab exierit tantae vultus quoque Procrin stridore Delphos adhaerent feris\n" +
                "**manu genitus superos** obsidis adversaque? Amnis praemia. Insisto erat,\n" +
                "positoris nate, quot certe, an erat est! Advertite ait manus traderet quoniam\n" +
                "cognosse hanc, flavusque terribili quae, iam latrare silvas Neleius videri.\n" +
                "\n" +
                "> Sub genitor pronos ego Sisyphe putat invisosque lumine tenuere colorque ebur\n" +
                "> iunctarum Echo umbra, iam. Agmine ab mirabile, gratia effreno **sed sunt\n" +
                "> tamen** loquentem sanguine videbar fugam, tamen! Opem interrita vertentia\n" +
                "> antri et virosque instruit qua; Cupidinis corporibus lateo omen nisi **senatus\n" +
                "> nos**! Et avidamque domini nunc hodierna et\n" +
                "> [solacia](http://www.nunc-est.io/monitusque-flores) ipsaque: digna adspicis in\n" +
                "> venti. Dixit edocuit notus capillos blandas maestus meumque, excutit\n" +
                "> Proserpina domo bimarem oculos.\n" +
                "\n" +
                "Maeoniam facto ut habitantque Bacchus de commonuit gravi, quaerit filia penna\n" +
                "dammas redituraque tractu, tum? Motu exclamat imbre, armentum horrifer\n" +
                "**nomina** ulciscere quot *Tereus sinistro*! In inter niveis imago mundi fama\n" +
                "sentit Priami, Iphis natura, nostraque tectis parens.", admin, new ArrayList<>());
        Post post2 = new Post("Second post", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin turpis risus, dapibus at augue ac, vulputate consequat lacus. Sed id sollicitudin dolor. Praesent pellentesque, dolor eu placerat placerat, risus nisi varius massa, et ornare lacus nisi sed tortor. Nulla nec sollicitudin eros, eget dapibus leo. Ut in egestas lorem. Vivamus eget fringilla purus. Nullam interdum ipsum eu est rhoncus condimentum. Etiam finibus, erat quis accumsan dapibus, ex dolor bibendum elit, non accumsan ligula arcu nec est. Nam egestas diam at finibus maximus. Vestibulum fringilla quis tortor non maximus. Aenean efficitur, nulla a molestie volutpat, mauris orci interdum urna, vitae sollicitudin tellus metus non orci. Phasellus pretium est orci, non pretium odio tristique vitae. Donec et tellus urna. Curabitur auctor blandit ipsum, pretium sodales nibh ultrices at.\n" +
                "\n" +
                "Vivamus nec odio urna. Vivamus semper vel justo sed rhoncus. Pellentesque id enim quis arcu finibus vulputate. Fusce leo arcu, elementum maximus ex at, rutrum efficitur arcu. Fusce vestibulum sem a turpis convallis, quis ullamcorper mauris egestas. Suspendisse eu tempus nibh. Pellentesque sit amet orci sit amet leo fermentum congue in non arcu. Suspendisse potenti. Aliquam tincidunt tincidunt libero, nec eleifend lacus commodo ac. Integer dui orci, consectetur et posuere sit amet, ultrices eu justo.\n" +
                "\n" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam neque lorem, elementum non ipsum sed, tristique iaculis eros. Sed in dolor pulvinar, mattis tellus vitae, tempor ante. Sed id blandit turpis. Morbi dictum odio ut magna tempor, quis facilisis augue dapibus. Mauris volutpat ac sapien in tempor. Cras sed bibendum massa. Duis in ullamcorper libero. Curabitur feugiat metus id ex aliquam vulputate. Donec tellus augue, commodo nec mauris eget, egestas sollicitudin magna. Sed ac maximus purus, ut dapibus neque. Vestibulum sodales leo nec bibendum lobortis.\n" +
                "\n" +
                "Nullam feugiat viverra odio, non fringilla dui. Proin sit amet metus sit amet arcu mattis sollicitudin nec vel libero. Suspendisse sed ultrices nulla. Phasellus auctor molestie orci non imperdiet. Duis sollicitudin mauris in augue aliquet, id consectetur dui eleifend. Nullam congue magna ut sagittis eleifend. Nulla consectetur odio non dictum finibus. Integer elementum molestie nulla quis blandit.\n" +
                "\n" +
                "Integer vulputate erat sed sodales ultrices. Aenean efficitur aliquet orci vitae sollicitudin. Nullam libero erat, varius eget hendrerit in, cursus sed nibh. Duis venenatis, lacus ut iaculis elementum, tellus leo malesuada lectus, sed vulputate metus diam vel mi. Integer consectetur ante sed arcu condimentum, eu sollicitudin nisi rhoncus. In magna elit, congue at diam sit amet, malesuada pretium libero. Quisque nulla tellus, porta ac porta imperdiet, lacinia finibus magna. Sed feugiat a tortor non viverra. Phasellus nec egestas sem. Etiam porta malesuada libero at volutpat. Aliquam pulvinar tincidunt feugiat.", admin, new ArrayList<>());

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

        log.debug("Loading finished");
    }
}
