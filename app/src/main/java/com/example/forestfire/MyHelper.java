package com.example.forestfire;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyHelper extends SQLiteOpenHelper {
    private static final String dbname = "alerts.db";
    private static final int version = 1;

    private static final String TABLE_NAME = "past_alerts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_D_T = "date_time";
    private static final String COLUMN_TEMP = "temperature";
    private static final String COLUMN_HUMIDITY = "humidity";
    private static final String COLUMN_SM = "soil_moisture";
    private static final String COLUMN_ATM_P = "Atmospheric_pressure";
    private static final String COLUMN_ALT = "Altitude";
    private static final String COLUMN_INTENSITY_RES = "Intensity_result";
    private static final String COLUMN_INTENSITY = "Intensity";

    MyHelper(Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                COLUMN_D_T + " TEXT NOT NULL," +
                COLUMN_TEMP + " REAL NOT NULL," +
                COLUMN_HUMIDITY + " REAL NOT NULL," +
                COLUMN_SM + " REAL NOT NULL," +
                COLUMN_ATM_P + " REAL NOT NULL," +
                COLUMN_ALT + " REAL NOT NULL," +
                COLUMN_INTENSITY_RES + " TEXT NOT NULL," +
                COLUMN_INTENSITY + " REAL NOT NULL)";
        db.execSQL(sqlCreateTable);

        insertData(33, 22, 22, 22, 22, "low", 22, db);
    }

    void insertData(double temp, double humidity, double soil_moisture, double atm_p, double altitude, String iResult, double intensity, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("*yyyy-MM-dd hh:mm a", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
//        String mDate ="2016-11-16";/*currentTime.toString();*/
        values.put(COLUMN_D_T, currentDateAndTime/*mDate*/);
        values.put(COLUMN_TEMP, temp);
        values.put(COLUMN_HUMIDITY, humidity);
        values.put(COLUMN_SM, soil_moisture);
        values.put(COLUMN_ATM_P, atm_p);
        values.put(COLUMN_ALT, altitude);
        values.put(COLUMN_INTENSITY_RES, iResult);
        values.put(COLUMN_INTENSITY, intensity);

        db.insert("past_alerts", null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
}

