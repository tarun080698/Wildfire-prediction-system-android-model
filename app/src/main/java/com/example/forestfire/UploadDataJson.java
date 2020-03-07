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
    private String dataParsed = "";
    private String singleParsed = "";

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
                data = data + line;
            }
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
//            singleParsed = jsonArray.get(0).toString();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject JO = (JSONObject) jsonArray.get(i);
//                singleParsed = JO.getString(get(0));
            }
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

        HomeFrag.data.setText(this.singleParsed);
    }

}
