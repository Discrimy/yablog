package ru.discrimy.yablog.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"author", "comments"})
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "text", nullable = false)
    @Lob
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    @Column(name = "created_at")
    LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Upvote> upvotes = new HashSet<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Downvote> downvotes = new HashSet<>();

    public Post(String title, String text, User author) {
        this.title = title;
        this.text = text;
        this.author = author;
    }
}
