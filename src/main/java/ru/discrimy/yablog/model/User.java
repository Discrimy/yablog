package ru.discrimy.yablog.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "posts")
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "username")
    private String username;
    @Column(name = "hashed_password")
    private String hashedPassword;
    @OneToMany(mappedBy = "author")
    private Set<Post> posts = new HashSet<>();
}
