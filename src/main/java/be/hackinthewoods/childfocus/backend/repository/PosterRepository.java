package be.hackinthewoods.childfocus.backend.repository;

import be.hackinthewoods.childfocus.backend.entity.Poster;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PosterRepository extends JpaRepository<Poster, Long> {
    @Query("select p from Poster p where exists (select m from Mission m where m.webUser = :user and m.missingPerson = p.missingPerson)")
    List<Poster> findAllByUser(WebUser user);
}
