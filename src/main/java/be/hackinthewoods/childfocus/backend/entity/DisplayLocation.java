package be.hackinthewoods.childfocus.backend.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class DisplayLocation {
    @OneToOne
    private Coordinate coordinate;
}
