package ru.discrimy.yablog.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"author", "comments"})
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "text")
    @Lob
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
