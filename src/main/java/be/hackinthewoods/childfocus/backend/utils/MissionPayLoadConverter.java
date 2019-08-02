package be.hackinthewoods.childfocus.backend.utils;

import be.hackinthewoods.childfocus.backend.entity.Mission;

import java.util.Map;

public class MissionPayLoadConverter {

    public static Map<String, String> convert(Mission mission) {
        return Map.of(
          "id", mission.getId().toString(),
          "status", mission.getStatus().name()
        );
    }
}
