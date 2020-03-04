package com.example.forestfire;

public class AlertModel {

    private int Aid;
    private double temp, humidity, soil_moisture, atm_p, altitude, intensity;
    private String date, iResult;

    public AlertModel(int aid, String date, double temp, double humidity, double soil_moisture, double atm_p, double altitude, double intensity, String iResult) {
        Aid = aid;
        this.temp = temp;
        this.humidity = humidity;
        this.soil_moisture = soil_moisture;
        this.atm_p = atm_p;
        this.altitude = altitude;
        this.intensity = intensity;
        this.date = date;
        this.iResult = iResult;
    }

    public AlertModel() {
    }

    public int getAid() {
        return Aid;
    }

    public void setAid(int aid) {
        Aid = aid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getSoil_moisture() {
        return soil_moisture;
    }

    public void setSoil_moisture(double soil_moisture) {
        this.soil_moisture = soil_moisture;
    }

    public double getAtm_p() {
        return atm_p;
    }

    public void setAtm_p(double atm_p) {
        this.atm_p = atm_p;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public String getiResult() {
        return iResult;
    }

    public void setiResult(String iResult) {
        this.iResult = iResult;
    }
}
