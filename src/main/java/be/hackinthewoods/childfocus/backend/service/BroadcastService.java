package be.hackinthewoods.childfocus.backend.service;

import java.util.List;
import java.util.Map;

public interface BroadcastService {

    /**
     * Subscribes tokens to a given topic
     * @throws IllegalArgumentException when {@code tokens} is {@code null} or empty
     * @throws IllegalArgumentException when {@code topic} is {@code null}
     */
    void subscribe(List<String> tokens, String topic);

    /**
     * Broadcast a pay load to a given topic
     * @throws IllegalArgumentException when {@code payLoad} is {@code null}
     * @throws IllegalArgumentException when {@code topic} is {@code null}
     */
    void broadcast(Map<String, String> payLoad, String topic);

}
