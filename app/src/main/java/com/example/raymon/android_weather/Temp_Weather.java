package com.example.raymon.android_weather;

/**
 * Created by raymon on 10/27/17.
 */

public class Temp_Weather {
    int min_temp;
    int max_temp;
    String weather;
    String date;
    public Temp_Weather(int min_temp,int max_temp,String weather)
    {
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.weather = weather;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
}
