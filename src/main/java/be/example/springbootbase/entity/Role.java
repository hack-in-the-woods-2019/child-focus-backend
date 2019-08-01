package be.example.springbootbase.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(mappedBy = "roles")
    private Set<WebUser> webUsers;

    @Column(nullable = false, unique = true, updatable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
