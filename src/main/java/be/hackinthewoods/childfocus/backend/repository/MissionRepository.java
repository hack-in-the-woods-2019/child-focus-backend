package be.hackinthewoods.childfocus.backend.repository;

import be.hackinthewoods.childfocus.backend.entity.MissingPerson;
import be.hackinthewoods.childfocus.backend.entity.Mission;
import be.hackinthewoods.childfocus.backend.entity.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByMissingPerson(MissingPerson missingPerson);

    List<Mission> findByWebUser(WebUser user);

    List<Mission> findByWebUserAndStatus(WebUser user, Mission.Status pending);
}
