package be.hackinthewoods.childfocus.backend.repository;

import be.hackinthewoods.childfocus.backend.entity.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<WebUser, Long> {

    Optional<WebUser> findByUsername(String username);

    Optional<WebUser> findByToken(String token);
}
