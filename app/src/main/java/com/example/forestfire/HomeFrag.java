package com.example.forestfire;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFrag extends Fragment {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_home, container, false);

        TextView intensity_result = v.findViewById(R.id.intensity);
        final TextView dateTime = v.findViewById(R.id.dateAndTime);
        TextView alertsOpen = v.findViewById(R.id.open_alerts);
        alertsOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AlertsActivity.class));
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("*yyyy-MM-dd hh:mm a", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        dateTime.setText(currentDateAndTime);


        Thread t = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(600000);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                SimpleDateFormat sdf = new SimpleDateFormat("*yyyy-MM-dd hh:mm a", Locale.getDefault());
                                String currentDateAndTime = sdf.format(new Date());
                                dateTime.setText(currentDateAndTime);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
        //        intesnity deciding rules
        int intensity = 55;
        if (intensity < 30) {
            intensity_result.setText(R.string.low);
            intensity_result.setBackgroundResource(R.color.low);
        } else if (intensity > 30 && intensity < 60) {
            intensity_result.setText(R.string.med);
            intensity_result.setBackgroundResource(R.color.medium);
        } else if (intensity >= 60 && intensity < 75) {
            intensity_result.setText(R.string.hig);
            intensity_result.setBackgroundResource(R.color.high);
        } else if (intensity >= 75 && intensity < 95) {
            intensity_result.setText(R.string.vhigh);
            intensity_result.setBackgroundResource(R.color.very_high);
        }
        //        intensity deciding rules
        return v;
    }

}
