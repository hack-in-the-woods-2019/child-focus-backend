package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.service.BroadcastService;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class BroadcastServiceImplTest {

    private BroadcastService broadcastService;

    @Before
    public void beforeEach() throws Exception {
        broadcastService = new BroadcastServiceImpl("", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void broadcast_nullPayLoad() {
        broadcastService.broadcast(null, "test-topic");
    }

    @Test(expected = IllegalArgumentException.class)
    public void broadcast_nullTopic() {
        broadcastService.broadcast(Map.of(), null);
    }
}