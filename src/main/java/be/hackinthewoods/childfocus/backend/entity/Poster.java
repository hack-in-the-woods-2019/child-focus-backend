package be.hackinthewoods.childfocus.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Poster {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = DisplayLocation.class)
    private List<DisplayLocation> displayLocations = new ArrayList<>();

    public Poster() {
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
        return displayLocations.equals(poster.displayLocations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayLocations);
    }

    @Override
    public String toString() {
        return "Poster{" +
                "displayLocations=" + displayLocations +
                '}';
    }
}
