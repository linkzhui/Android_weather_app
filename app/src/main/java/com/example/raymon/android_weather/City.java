package com.example.raymon.android_weather;

/**
 * Created by raymon on 10/23/17.
 */

public class City {
    private String city;
    private String day;
    private String weather;
    private String update_time;
    private String temperature;
    private String temp_unit;
    private String lat;
    private String log;

    public void setLat(String lat)
    {
        this.lat = lat;
    }

    public void setLog(String log)
    {
        this.log = log;
    }

    public String getLat()
    {
        return lat;
    }

    public String getLog()
    {
        return log;
    }

    public City(String city)
    {
        this.city = city;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTemperature(String temperature)
    {
        this.temperature = temperature;
    }

    public void setWeatherStatus(String weather_status) {
        this.weather = weather_status;
    }

    public void setUnit(String unit) {
        this.temp_unit = unit;
    }

    public String getUnit()
    {
        return temp_unit;
    }

    public String getWeather() {
        return weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getCity() {
        return city;
    }

    public String getDay() {
        return day;
    }

    public boolean needToUpdate() {
        return true;
    }

    public void update(String update_time) {
        this.update_time = update_time;
    }

    public boolean isC() {
        return temp_unit == "C";
    }
}
