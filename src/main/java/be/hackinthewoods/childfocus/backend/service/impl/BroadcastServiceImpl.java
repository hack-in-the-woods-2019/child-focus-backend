package be.hackinthewoods.childfocus.backend.service.impl;

import be.hackinthewoods.childfocus.backend.service.BroadcastService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@Service
public class BroadcastServiceImpl implements BroadcastService {

    private final String firebaseKeyFilePath;
    private final String firebaseUrl;

    BroadcastServiceImpl(
      @Value("${firebase.key.file}") String firebaseKeyFilePath,
      @Value("${firebase.url}") String firebaseUrl) {
        this.firebaseKeyFilePath = firebaseKeyFilePath;
        this.firebaseUrl = firebaseUrl;
    }

    @PostConstruct
    void init() {
        try (FileInputStream serviceAccount = new FileInputStream(firebaseKeyFilePath)) {
            FirebaseOptions options = new FirebaseOptions.Builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount))
              .setDatabaseUrl(firebaseUrl)
              .build();

            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("No firebase key file found at " + firebaseKeyFilePath);
        } catch (IOException e) {
            throw new IllegalStateException("An error occurred while reading file " + firebaseKeyFilePath);
        }
    }

    @Override
    public void broadcast(Map<String, String> payLoad, String topic) {
        Assert.notNull(payLoad, "The pay load mustn't be null");
        Assert.hasText(topic, "The topic mustn't be empty");

        Message message = Message.builder()
          .putAllData(payLoad)
          .setTopic(topic)
          .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new IllegalStateException("Lol?", e);
        }
    }
}
