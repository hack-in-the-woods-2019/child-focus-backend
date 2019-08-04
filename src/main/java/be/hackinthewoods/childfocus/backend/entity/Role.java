package be.hackinthewoods.childfocus.backend.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(mappedBy = "roles")
    private Set<WebUser> webUsers;

    @Column(nullable = false, unique = true, updatable = false)
    private String authority;

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
