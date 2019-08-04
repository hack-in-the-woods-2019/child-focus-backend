package be.hackinthewoods.childfocus.backend.service;

import be.hackinthewoods.childfocus.backend.entity.PickupPoint;

import java.util.List;

public interface PickupPointService {
    List<PickupPoint> findAll();

    void save(PickupPoint pickupPoint);
}
