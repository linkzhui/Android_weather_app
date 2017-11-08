package com.example.raymon.android_weather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by raymon on 10/23/17.
 */

public class CityAdapter extends BaseAdapter {

    Context context;
    List<City> city_list;

    public CityAdapter(Context context)
    {
        this.context = context;
        city_list = CityData.getData();
    }
    @Override
    public int getCount() {
        return city_list.size();
    }

    @Override
    public Object getItem(int i) {

        return city_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return the ListView of city list
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.city_item,
                    parent, false);
        }
        TextView city_name = (TextView) convertView.findViewById(
                R.id.city_name);
        TextView city_temperature =  (TextView)convertView.findViewById(
                R.id.city_temperature);
        TextView city_weather = (TextView)convertView.findViewById(
                R.id.city_weather);
        TextView city_day = (TextView)convertView.findViewById(
                R.id.city_time);



        City r = city_list.get(position);
        CityData city_data = new CityData();
        if(city_data.isThisCurrentCity(r.getCity()))
        {
            TextView current_city = convertView.findViewById(
                    R.id.current_city);
            current_city.setText("this is current city");
        }
        else{
            TextView current_city = convertView.findViewById(
                    R.id.current_city);
            current_city.setText("");
        }
        city_name.setText(r.getCity());
        String Unit = r.getUnit()=="metric"? " \u2103":" \u2109";
        city_temperature.setText(r.getTemperature()+Unit);
        city_weather.setText(r.getWeather());
        city_day.setText(r.getDay());
        return convertView;
    }


}
