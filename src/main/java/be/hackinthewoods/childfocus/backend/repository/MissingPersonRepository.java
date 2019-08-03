package be.hackinthewoods.childfocus.backend.repository;

import be.hackinthewoods.childfocus.backend.entity.MissingPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissingPersonRepository extends JpaRepository<MissingPerson, Long> {}
