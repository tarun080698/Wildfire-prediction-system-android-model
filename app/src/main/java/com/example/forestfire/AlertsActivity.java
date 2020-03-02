package com.example.forestfire;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class AlertsActivity extends AppCompatActivity {

    MyHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        helper = new MyHelper(this);
        final SQLiteDatabase database = helper.getReadableDatabase();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.insertData(33, 22, 22, 22, 22, "low", 22, database);
            }
        });
        HomeFrag homeFrag = new HomeFrag();
        int i = homeFrag.intensity;


//        if(i<30){
//            inboxStyleNotification();
//        }

    }

//    public void simpleNotification(View view) {
//        /**Id for Notification**/
//        final String channelId = "Channel 1";
//
//        /**Gets an instance of the NotificationManager service**/
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        /**Get an instance of NotificationManager**/
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
//                .setSmallIcon(R.drawable.app_logo_img)
//                .setContentTitle("ALERT!!!")
//                .setContentText("Received a high intensity fire alert.\nPLEASE CHECK ONCE!\nClick to view alert.")
//                .setAutoCancel(false)
//                .setPriority(NotificationCompat.PRIORITY_HIGH);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            /**For Android Oreo or Greater**/
//            /**We need to Create Channel for Android O to Display Notification**/
//            String channelId1 = "1";
//            String channelName1 = "channel1";
//
//            /**Initialize NotificationChannel**/
//            NotificationChannel channel = new NotificationChannel(channelId1, channelName1, NotificationManager.IMPORTANCE_HIGH);
//
//            /**Enable Notify Light, Vibration, and show Badge to true**/
//            channel.enableLights(true);
//            channel.setLightColor(Color.RED);
//            channel.setShowBadge(true);
//            channel.enableVibration(true);
//            channel.enableLights(true);
//
//            /**Set the ChannelID to Notification.Builder**/
//            builder.setChannelId(channelId1);
//
//            /**Create Channel if Not null**/
//            if (mNotificationManager != null) {
//                mNotificationManager.createNotificationChannel(channel);
//            }
//        } else {
//            /**Android O or Lesser**/
//
//            /**below statement use for notification tone, notification lights, vibration**/
//            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
//        }
//
//        /**Use getIntent if you want to Open Current Activity
//         * Else Use
//         * Intent intent = new Intent(getApplicationContext, MyClass.class);
//         * Use Your Class Name instead of MyClass**/
//        Intent intent = getIntent();
//
//        /**Create a TaskStackBuilder**/
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
//
//        /**Add NextIntent**/
//        stackBuilder.addNextIntent(intent);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(001, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        /**Set a Content Intent to Open on Notification Click**/
//        builder.setContentIntent(pendingIntent);
//        /*** When you issue multiple notifications about the same type of event,
//         * it’s best practice for your app to try to update an existing notification
//         * with this new information, rather than immediately creating a new notification.
//         * If you want to update this notification at a later date, you need to assign it an ID.
//         * You can then use this ID whenever you issue a subsequent notification.
//         * If the previous notification is still visible, the system will update this existing notification,
//         * rather than create a new one. In this example, the notification’s ID is 001***/
//
//        /**Notify if not Null**/
//        if (mNotificationManager != null) {
//            mNotificationManager.notify(1, builder.build());
//        }
//    }

    public void inboxStyleNotification(View view) {
        final String channelId = "Channel 1";

        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(this, channelId).setSmallIcon(R.drawable.app_logo_img)
                .setContentTitle("ALERT!!!")
                .setContentText("Seems like it's an alert for some serious fire alerts.")
                .setSubText("Please check once!")
                .setAutoCancel(true);

        /**Set a Notification Style to Inbox**/
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        /**Set a Title When Notification Expanded**/
        inboxStyle.setBigContentTitle("It's an alert.");

        /**Add your All messages here or use Loop to generate messages**/

        inboxStyle.addLine("Message 1");
        inboxStyle.addLine("Message 2");
        inboxStyle.addLine("Message 3");
        inboxStyle.addLine("Message 4");
        inboxStyle.addLine("Message 5");

        /**Set the NotificationCompat object to builder**/

        mBuilder.setStyle(inboxStyle);
        Intent intent = getIntent();
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(intent);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId2 = "2";
            String channelName2 = "channel2";
            NotificationChannel channel = new NotificationChannel(channelId2, channelName2, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setShowBadge(true);
            channel.enableVibration(true);
            mBuilder.setChannelId(channelId2);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        } else {
            mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        }
        if (mNotificationManager != null) {
            mNotificationManager.notify(1, mBuilder.build());
        }
    }
}
