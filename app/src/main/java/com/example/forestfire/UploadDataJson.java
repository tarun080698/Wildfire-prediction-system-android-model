package com.example.forestfire;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadDataJson extends AsyncTask<Void, Void, Void> {

    private String data = "";
    private String singleParsed, date_time, temp, alt, humi, sm, atm;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://cloud.boltiot.com/remote/30650161-8347-44a8-90fe-716893bef308/fetchData?&deviceName=BOLT6095192");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data += line;
            }
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            singleParsed = jsonArray.get(0).toString();
//            dataParsed = singleParsed.substring(22,24);
            date_time = "Date and Time(Last alert):-\n" + singleParsed.substring(2, 21);
            temp = singleParsed.substring(24, 29) + "° C";
            humi = singleParsed.substring(32, 37) + "%";
            sm = singleParsed.substring(40, 44) + "% Vol";
            alt = singleParsed.substring(47, 54) + " feet";
            atm = singleParsed.substring(57, 65) + " pa";

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        HomeFrag.dateTime.setText(this.date_time);
        HomeFrag.temperature.setText(this.temp);
        HomeFrag.humidity.setText(this.humi);
        HomeFrag.soil_moisture.setText(this.sm);
        HomeFrag.atm_p.setText(this.atm);
        HomeFrag.altitude.setText(this.alt);
    }

}
