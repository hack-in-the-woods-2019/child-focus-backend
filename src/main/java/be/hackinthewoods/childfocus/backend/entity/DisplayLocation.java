package be.hackinthewoods.childfocus.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class DisplayLocation {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Coordinate coordinate;

    public DisplayLocation() {
    }

    public DisplayLocation(Long id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayLocation that = (DisplayLocation) o;
        return id.equals(that.id) &&
                coordinate.equals(that.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coordinate);
    }
}
