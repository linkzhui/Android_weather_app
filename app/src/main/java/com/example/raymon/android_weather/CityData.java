package com.example.raymon.android_weather;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by raymon on 10/23/17.
 */

public class CityData {

    public static List<City> city_list = new ArrayList<>();

    public static HashMap<String,Integer> map = new HashMap<>();
    public static String current_city = "";
    public static List<List<Temp_Weather>> forecast = new ArrayList<>();
    public static List<List<Temp_Weather>> next_forecast = new ArrayList<>();

    public static List<City> getData(){
        return city_list;
    }


    public boolean isThisCurrentCity(String city_name){

        return current_city.equals(city_name);
    }

    public void setCurrent_city(String city_name)
    {
        current_city = city_name;
    }
    public boolean exist(String city_name)
    {
        return map.containsKey(city_name);
    }

    public boolean add(String city_name)
    {
        if(map.containsKey(city_name) || city_list.size()==10)
        {
            return false;
        }

        map.put(city_name,city_list.size());
        city_list.add(new City(city_name));
        return true;
    }



    public void addForecast(List<Temp_Weather> nestList)
    {
        forecast.add(nestList);
    }

    public boolean addNextForecast(List<Temp_Weather> nestList){

        return next_forecast.add(nestList);
    }
    public boolean remove(String city_name)
    {
        if(!map.containsKey(city_name))
        {
            return false;
        }

        //step1: get the removed element's index in arraylist (int index)
        //step2: update the last element's value to removed element's index position
        //step3: remove the element from the hashmap and remove the last element from the arraylist
        int index = map.get(city_name);
        int size = city_list.size()-1;
        if(city_list.size()>=1)
        {
            City temp_city = city_list.get(size);
            city_list.set(index,temp_city);
            List<Temp_Weather> temp_list = forecast.get(size);
            forecast.set(index,temp_list);
            temp_list = next_forecast.get(size);
            next_forecast.set(index,temp_list);
            map.put(temp_city.getCity(),index);
        }

        city_list.remove(size);
        forecast.remove(size);
        next_forecast.remove(size);
        map.remove(city_name);
        return true;
    }

    public int size()
    {
        return city_list.size();
    }
}
