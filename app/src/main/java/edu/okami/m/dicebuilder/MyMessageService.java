package edu.okami.m.dicebuilder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by M on 4/20/2017.
 */

public class MyMessageService extends FirebaseMessagingService{
    private final String TAG = "MyMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        //TODO: Handle FCM Messages Here.
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        //Check if message contains a data payload
        if (remoteMessage.getData().size() > 0){
            Log.d(TAG, "Msg Data Payload: " + remoteMessage.getData());

            //Check if data needs to be processed by long running job
           // if(true){
                //scheduleJob();
           // } else {
                //Hand message within 10 seconds
                handleNow();
           // }

            //Check if message contains a notification payload
            if (remoteMessage.getNotification() != null){
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }

            //sendNotification method to generate own notificatiosn as a
            //result of receiving FCM message
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onDeletedMessages(){

    }

    /**
     * Schedule a job using FirebaseJobDispatcher
     */
    private void scheduleJob(){
        //Start dispatch job
        //Firebase
    }

    /**
     * Handle time allotted to BroadcastReceivers
     */
    private void handleNow(){
        Log.d(TAG, "Short lived task is done");
    }

    /**
     * Create and show a simple notification containing the received FCM Msg
     * @param messageBody
     */
    private void sendNotification(String messageBody){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                //.setSmallIcon()
        .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

}
