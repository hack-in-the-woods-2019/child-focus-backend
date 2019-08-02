package be.hackinthewoods.childfocus.backend.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Poster {
    @ManyToOne
    private List<DisplayLocation> displayLocations = new ArrayList<>();

    public Poster() {
    }

    public Poster(List<DisplayLocation> displayLocations) {
        this.displayLocations = displayLocations;
    }

    public List<DisplayLocation> getDisplayLocations() {
        return Collections.unmodifiableList(displayLocations);
    }

    public void setDisplayLocations(List<DisplayLocation> displayLocations) {
        this.displayLocations = displayLocations;
    }

    public void addDisplayLocation(DisplayLocation displayLocation) {
        displayLocations.add(displayLocation);
    }

    public void removeDisplayLocation(DisplayLocation displayLocation) {
        displayLocations.removeIf(location -> location.equals(displayLocation));
    }
}
