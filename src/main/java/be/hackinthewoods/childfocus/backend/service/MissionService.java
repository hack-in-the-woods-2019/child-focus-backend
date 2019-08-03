package be.hackinthewoods.childfocus.backend.service;

import be.hackinthewoods.childfocus.backend.entity.MissingPerson;
import be.hackinthewoods.childfocus.backend.entity.Mission;

import java.util.List;

public interface MissionService {
    public List<Mission> findByMissingPerson(MissingPerson missingPerson);
}
