package be.hackinthewoods.childfocus.backend.service;

import java.util.Map;

public interface BroadcastService {

    /**
     * Broadcast a pay load to a given topic
     * @throws IllegalArgumentException when {@code payLoad} is {@code null}
     * @throws IllegalArgumentException when {@code topic} is {@code null}
     */
    void broadcast(Map<String, String> payLoad, String topic);

}
