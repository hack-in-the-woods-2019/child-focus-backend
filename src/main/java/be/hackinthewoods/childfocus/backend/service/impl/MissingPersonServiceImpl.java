package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.MissingPerson;
import be.hackinthewoods.childfocus.backend.repository.MissingPersonRepository;
import be.hackinthewoods.childfocus.backend.service.MissingPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MissingPersonServiceImpl implements MissingPersonService {

    private MissingPersonRepository missingPersonRepository;

    @Autowired
    public MissingPersonServiceImpl(MissingPersonRepository missingPersonRepository) {
        this.missingPersonRepository = missingPersonRepository;
    }

    @Override
    public List<MissingPerson> findAll() {
        return missingPersonRepository.findAll();
    }

    @Override
    public Optional<MissingPerson> findById(long id) {
        return missingPersonRepository.findById(id);
    }
}
