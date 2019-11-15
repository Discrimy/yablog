package ru.discrimy.yablog.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"post", "author"})
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    @Column(name = "text")
    private String text;
}
