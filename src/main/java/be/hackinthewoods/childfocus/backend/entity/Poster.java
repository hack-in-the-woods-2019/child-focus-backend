package be.hackinthewoods.childfocus.backend.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Poster {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(
      targetEntity = DisplayLocation.class,
      cascade = CascadeType.ALL
    )
    private List<DisplayLocation> displayLocations;

    public Poster() {
        displayLocations = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DisplayLocation> getDisplayLocations() {
        return Collections.unmodifiableList(displayLocations);
    }

    public void addDisplayLocation(DisplayLocation displayLocation) {
        displayLocations.add(displayLocation);
    }

    public void removeDisplayLocation(DisplayLocation displayLocation) {
        displayLocations.removeIf(location -> location.equals(displayLocation));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poster poster = (Poster) o;
        return Objects.equals(id, poster.id) &&
          displayLocations.equals(poster.displayLocations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, displayLocations);
    }

    @Override
    public String toString() {
        return "Poster{" +
          "id=" + id +
          ", displayLocations=" + displayLocations +
          '}';
    }
}
