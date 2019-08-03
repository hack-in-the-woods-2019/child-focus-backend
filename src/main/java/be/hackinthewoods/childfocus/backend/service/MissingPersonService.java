package be.hackinthewoods.childfocus.backend.service;

import be.hackinthewoods.childfocus.backend.entity.MissingPerson;

import java.util.List;
import java.util.Optional;

public interface MissingPersonService {
    public List<MissingPerson> findAll();

    public Optional<MissingPerson> findById(long id);
}
