package ru.discrimy.yablog.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"username"})
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;
    @OneToMany(mappedBy = "author")
    private Set<Post> posts = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private Set<Upvote> upvotes = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private Set<Downvote> downvotes = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_to_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities = new HashSet<>();

    public User(String username, String hashedPassword, Set<Authority> authorities) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.authorities = authorities;
    }
}
