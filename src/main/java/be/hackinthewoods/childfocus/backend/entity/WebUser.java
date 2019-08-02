package be.hackinthewoods.childfocus.backend.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class WebUser {

    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
