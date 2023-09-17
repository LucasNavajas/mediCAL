package com.example.medical.FirebaseMessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.medical.InicioCalendarioActivity;
import com.example.medical.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token){
        super.onNewToken(token);
        Log.e("NEW_TOKEN", token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
        // Procesar la notificación aquí
        String notificationTitle = remoteMessage.getNotification().getTitle();
        String notificationBody = remoteMessage.getNotification().getBody();

        // Mostrar la notificación en primer plano
        showCustomNotification(notificationTitle, notificationBody);
    }
    private void showCustomNotification(String title, String body) {
        Intent intent = new Intent(this, InicioCalendarioActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // Crear el canal de notificación (necesario para dispositivos con Android 8.0 y posteriores)
        String channelId = "MiCanal"; // Debes definir un ID de canal único
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.logo_app)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Crear el canal de notificación si no existe (necesario para dispositivos con Android 8.0 y posteriores)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Nombre del Canal", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Mostrar la notificación
        notificationManager.notify(0, notificationBuilder.build());
    }
}
