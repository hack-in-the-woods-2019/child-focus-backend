package be.hackinthewoods.childfocus.backend.repository;

import be.hackinthewoods.childfocus.backend.entity.PickupPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupPointRepository extends JpaRepository<PickupPoint, Long> {
}
