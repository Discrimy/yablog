package ru.discrimy.yablog.model;

import lombok.*;

import javax.persistence.*;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_to_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities = new HashSet<>();
}
