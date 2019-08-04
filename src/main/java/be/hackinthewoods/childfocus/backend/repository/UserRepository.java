package be.hackinthewoods.childfocus.backend.repository;

import be.hackinthewoods.childfocus.backend.entity.Role;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<WebUser, Long> {

    Optional<WebUser> findByEmail(String email);

    Optional<WebUser> findByEmailAndPassword(String email, String password);

    @Query("SELECT u FROM WebUser u INNER JOIN u.roles r WHERE r = :role")
    List<WebUser> findByRole(Role role);

    Optional<WebUser> findByToken(String token);
}
