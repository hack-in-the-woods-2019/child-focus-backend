package be.hackinthewoods.childfocus.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PickupPoint {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Coordinate coordinate;

    public PickupPoint() {
    }

    public PickupPoint(Long id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
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
        PickupPoint that = (PickupPoint) o;
        return id.equals(that.id) &&
                coordinate.equals(that.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coordinate);
    }

    @Override
    public String toString() {
        return "PickupPoint{" +
                "id=" + id +
                ", coordinate=" + coordinate +
                '}';
    }
}
