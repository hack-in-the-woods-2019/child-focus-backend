package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.entity.PickupPoint;
import be.hackinthewoods.childfocus.backend.repository.PickupPointRepository;
import be.hackinthewoods.childfocus.backend.service.PickupPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PickupPointServiceImpl implements PickupPointService {

    private PickupPointRepository pickupPointRepository;

    @Autowired
    public PickupPointServiceImpl(PickupPointRepository pickupPointRepository) {
        this.pickupPointRepository = pickupPointRepository;
    }

    @Override
    public List<PickupPoint> findAll() {
        return pickupPointRepository.findAll();
    }

    @Override
    public void save(PickupPoint pickupPoint) {
        pickupPointRepository.save(pickupPoint);
    }
}
