package com.medical.springserver;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void scheduleNotification(String token, String title, String body, long delayInSeconds) {
        Message message = Message.builder()
                .setToken(token)
                .putData("title", title)
                .putData("body", body)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            // Manejar errores
        }
    }
}
