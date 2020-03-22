package com.example.forestfire;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import static android.view.View.VISIBLE;
import static com.example.forestfire.MyHelper.COLUMN_ALT;
import static com.example.forestfire.MyHelper.COLUMN_ATM_P;
import static com.example.forestfire.MyHelper.COLUMN_D_T;
import static com.example.forestfire.MyHelper.COLUMN_HUMIDITY;
import static com.example.forestfire.MyHelper.COLUMN_INTENSITY;
import static com.example.forestfire.MyHelper.COLUMN_INTENSITY_RES;
import static com.example.forestfire.MyHelper.COLUMN_SM;
import static com.example.forestfire.MyHelper.COLUMN_TEMP;


public class HomeFrag extends Fragment {

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private SQLiteDatabase databaseWrite;
    public static TextView dateTime, dateTimeCurrent, temperature, humidity, soil_moisture, atm_p, altitude;
    Button b1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.frag_home, container, false);

        final ProgressBar progressBar = v.findViewById(R.id.progressBar);


        //getting views of sensors text
        temperature = v.findViewById(R.id.tempCurrent);
        humidity = v.findViewById(R.id.hum_current);
        soil_moisture = v.findViewById(R.id.soil_moistCurrent);
        atm_p = v.findViewById(R.id.atmCurrent);
        altitude = v.findViewById(R.id.altCurrent);
        final TextView intensity_result = v.findViewById(R.id.intensity);
        dateTime = v.findViewById(R.id.dateAndTime);
        dateTimeCurrent = v.findViewById(R.id.dateAndTimeCurrent);

        //sending alerts to database
        TextView alertsOpen = v.findViewById(R.id.open_alerts);
        MyHelper helper = new MyHelper(getActivity());
        databaseWrite = helper.getWritableDatabase();
        alertsOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AlertsActivity.class));
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("*yyyy-MM-dd hh:mm a", Locale.getDefault());
        String currentDateAndTime = "Date and Time(present):-  \n" + sdf.format(new Date());
        dateTimeCurrent.setText(currentDateAndTime);

        Thread t = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(600000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(VISIBLE);

                                //function call to fetch data or sensor from api
                                UploadDataJson uploadDataJson = new UploadDataJson();
                                uploadDataJson.execute();

                                //date to be changed
                                SimpleDateFormat sdf = new SimpleDateFormat("*yyyy-MM-dd hh:mm a", Locale.getDefault());
                                String currentDateAndTime = "Date and Time(present):- \n" + sdf.format(new Date());
                                dateTimeCurrent.setText(currentDateAndTime);

                                //getting the intensity generated
                                Random random = new Random();
                                int intensity = random.nextInt(100 - 1) + 1;

                                //getting vales of current params of sensors
                                String dtL = dateTime.getText().toString();
                                String te = temperature.getText().toString();
                                String h = humidity.getText().toString();
                                String s = soil_moisture.getText().toString();
                                String ap = atm_p.getText().toString();
                                String al = altitude.getText().toString();

                                //checking of intensity for alert
                                if (intensity <= 25) {
                                    intensity_result.setVisibility(VISIBLE);
                                    intensity_result.setText(R.string.low);
                                    intensity_result.setBackgroundResource(R.color.low);
                                    progressBar.setVisibility(View.GONE);
                                } else if (intensity > 25 && intensity <= 60) {
                                    intensity_result.setVisibility(VISIBLE);
                                    intensity_result.setText(R.string.med);
                                    intensity_result.setBackgroundResource(R.color.medium);
                                    progressBar.setVisibility(View.GONE);
                                } else if (intensity > 60 && intensity <= 75) {
                                    intensity_result.setVisibility(VISIBLE);
                                    intensity_result.setText(R.string.hig);
                                    intensity_result.setBackgroundResource(R.color.high);
                                    insertData(dtL, te, h, s, ap, al, "High", "22", databaseWrite);
                                    progressBar.setVisibility(View.GONE);
                                    simpleNotification(v);
                                } else if (intensity > 75) {
                                    intensity_result.setVisibility(VISIBLE);
                                    intensity_result.setText(R.string.very_high);
                                    intensity_result.setBackgroundResource(R.color.very_high);
                                    insertData(dtL, te, h, s, ap, al, "Very High", "22", databaseWrite);
                                    progressBar.setVisibility(View.GONE);
                                    simpleNotification(v);
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
        return v;
    }

    private void insertData(String dt, String temp, String humidity, String soil_moisture, String atm_p, String altitude, String iResult, String intensity, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_D_T, dt);
        values.put(COLUMN_TEMP, temp);
        values.put(COLUMN_HUMIDITY, humidity);
        values.put(COLUMN_SM, soil_moisture);
        values.put(COLUMN_ATM_P, atm_p);
        values.put(COLUMN_ALT, altitude);
        values.put(COLUMN_INTENSITY_RES, iResult);
        values.put(COLUMN_INTENSITY, intensity);

        db.insert("past_alerts", null, values);

    }

    private void simpleNotification(View view) {

        final String channelId = "Channel 1";
        NotificationManager mNotificationManager = (NotificationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(Objects.requireNonNull(getActivity()), channelId)
                .setSmallIcon(R.drawable.app_logo_img)
                .setContentTitle("ALERT!!!")
                .setContentText("Received a high intensity fire alert. PLEASE CHECK ONCE!")
                .setSubText("Click to view alert.")
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId1 = "1";
            String channelName1 = "channel1";

            NotificationChannel channel = new NotificationChannel(channelId1, channelName1, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setShowBadge(true);
            channel.enableVibration(true);
            channel.enableLights(true);
            builder.setChannelId(channelId1);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        } else {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        }

        Intent intent = new Intent(getActivity(), AlertsActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(001, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        if (mNotificationManager != null) {
            mNotificationManager.notify(1, builder.build());
        }
    }
}
