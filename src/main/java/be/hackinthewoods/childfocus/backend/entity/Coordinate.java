package be.hackinthewoods.childfocus.backend.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Coordinate {
    @Id
    @GeneratedValue
    private Long id;

    @Column(precision = 12, scale = 6)
    private BigDecimal latitude;
    @Column(precision = 12, scale = 6)
    private BigDecimal longitude;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    public Coordinate() {}

    public Coordinate(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Nullable
    public Address getAddress() {
        return address;
    }

    public void setAddress(@Nullable Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(id, that.id) &&
          latitude.equals(that.latitude) &&
          longitude.equals(that.longitude) &&
          Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude, address);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
          "id=" + id +
          ", latitude=" + latitude +
          ", longitude=" + longitude +
          ", address=" + address +
          '}';
    }
}
