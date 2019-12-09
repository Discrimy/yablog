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
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    @Column(name = "text", nullable = false)
    @Lob
    private String text;
}
