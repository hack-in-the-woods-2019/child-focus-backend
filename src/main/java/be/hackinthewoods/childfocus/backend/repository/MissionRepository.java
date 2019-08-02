package be.hackinthewoods.childfocus.backend.repository;

import be.hackinthewoods.childfocus.backend.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {}
