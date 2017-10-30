package com.example.raymon.android_weather;


import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityWeatherFragment extends Fragment {


    private City city;
    public CityWeatherFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CityWeatherFragment(City City_name) {
        // Required empty public constructor
        city = City_name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = (ViewGroup) inflater.inflate(R.layout.fragment_city_weather, container, false);
        TextView city_name = (TextView) rootView.findViewById(
                R.id.city_name);
        TextView city_temperature =  (TextView)rootView.findViewById(
                R.id.city_temperature);
        TextView city_weather = (TextView)rootView.findViewById(
                R.id.city_weather);
        TextView city_day = (TextView)rootView.findViewById(
                R.id.city_time);
        TextView forecast_1_temp = (TextView)rootView.findViewById(
                R.id.forecast_1_temp);
        TextView forecast_1_wea = (TextView)rootView.findViewById(
                R.id.forecast_1_weather);
        TextView forecast_2_temp = (TextView)rootView.findViewById(
                R.id.forecast_2_temp);
        TextView forecast_2_wea = (TextView)rootView.findViewById(
                R.id.forecast_2_weather);
        TextView forecast_3_temp = (TextView)rootView.findViewById(
                R.id.forecast_3_temp);
        TextView forecast_3_wea = (TextView)rootView.findViewById(
                R.id.forecast_3_weather);
        TextView forecast_4_temp = (TextView)rootView.findViewById(
                R.id.forecast_4_temp);
        TextView forecast_4_wea = (TextView)rootView.findViewById(
                R.id.forecast_4_weather);
        TextView forecast_5_temp = (TextView)rootView.findViewById(
                R.id.forecast_5_temp);
        TextView forecast_5_wea = (TextView)rootView.findViewById(
                R.id.forecast_5_weather);
        TextView forecast_6_temp = (TextView)rootView.findViewById(
                R.id.forecast_6_temp);
        TextView forecast_6_wea = (TextView)rootView.findViewById(
                R.id.forecast_6_weather);
        TextView forecast_7_temp = (TextView)rootView.findViewById(
                R.id.forecast_7_temp);
        TextView forecast_7_wea = (TextView)rootView.findViewById(
                R.id.forecast_7_weather);
        TextView forecast_8_temp = (TextView)rootView.findViewById(
                R.id.forecast_8_temp);
        TextView forecast_8_wea = (TextView)rootView.findViewById(
                R.id.forecast_8_weather);
        TextView day_1  = (TextView)rootView.findViewById(
                R.id.day_1);
        TextView day_1_temp  = (TextView)rootView.findViewById(
                R.id.day_1_temp);
        TextView day_1_wea  = (TextView)rootView.findViewById(
                R.id.day_1_weather);
        TextView day_2  = (TextView)rootView.findViewById(
                R.id.day_2);
        TextView day_2_temp  = (TextView)rootView.findViewById(
                R.id.day_2_temp);
        TextView day_2_wea  = (TextView)rootView.findViewById(
                R.id.day_2_weather);
        TextView day_3  = (TextView)rootView.findViewById(
                R.id.day_3);
        TextView day_3_temp  = (TextView)rootView.findViewById(
                R.id.day_3_temp);
        TextView day_3_wea  = (TextView)rootView.findViewById(
                R.id.day_3_weather);
        TextView day_4  = (TextView)rootView.findViewById(
                R.id.day_4);
        TextView day_4_temp  = (TextView)rootView.findViewById(
                R.id.day_4_temp);
        TextView day_4_wea  = (TextView)rootView.findViewById(
                R.id.day_4_weather);
        TextView day_5  = (TextView)rootView.findViewById(
                R.id.day_5);
        TextView day_5_temp  = (TextView)rootView.findViewById(
                R.id.day_5_temp);
        TextView day_5_wea  = (TextView)rootView.findViewById(
                R.id.day_5_weather);
//        CityData city_data = new CityData();
//        if(city_data.currentCity(r.getCity()))
//        {
//            TextView current_city = rootView.findViewById(
//                    R.id.current_city);
//            current_city.setText("this is current city");
//        }
        city_name.setText(city.getCity());
        Log.e("city weather fragment on create cycle",city.getTemperature());
        int index = CityData.map.get(city.getCity());
        String Unit = city.getUnit()=="metric"? " \u2103":" \u2109";
        city_temperature.setText(city.getTemperature().toString()+Unit);
        city_weather.setText(city.getWeather());
        city_day.setText(city.getDay());
        Temp_Weather tempWeather;
        tempWeather = CityData.forecast.get(index).get(0);
        forecast_1_wea.setText(tempWeather.weather);
        forecast_1_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        tempWeather = CityData.forecast.get(index).get(1);
        forecast_2_wea.setText(tempWeather.weather);
        forecast_2_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        tempWeather = CityData.forecast.get(index).get(2);
        forecast_3_wea.setText(tempWeather.weather);
        forecast_3_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        tempWeather = CityData.forecast.get(index).get(3);
        forecast_4_wea.setText(tempWeather.weather);
        forecast_4_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        tempWeather = CityData.forecast.get(index).get(4);
        forecast_5_wea.setText(tempWeather.weather);
        forecast_5_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        tempWeather = CityData.forecast.get(index).get(5);
        forecast_6_wea.setText(tempWeather.weather);
        forecast_6_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        tempWeather = CityData.forecast.get(index).get(6);
        forecast_7_wea.setText(tempWeather.weather);
        forecast_7_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        tempWeather = CityData.forecast.get(index).get(7);
        forecast_8_wea.setText(tempWeather.weather);
        forecast_8_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        tempWeather = CityData.next_forecast.get(index).get(0);
        day_1.setText(tempWeather.date);
        day_1_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        day_1_wea.setText(tempWeather.weather);
        tempWeather = CityData.next_forecast.get(index).get(1);
        day_2.setText(tempWeather.date);
        day_2_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        day_2_wea.setText(tempWeather.weather);
        tempWeather = CityData.next_forecast.get(index).get(2);
        day_3.setText(tempWeather.date);
        day_3_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        day_3_wea.setText(tempWeather.weather);
        tempWeather = CityData.next_forecast.get(index).get(3);
        day_4.setText(tempWeather.date);
        day_4_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        day_4_wea.setText(tempWeather.weather);
        tempWeather = CityData.next_forecast.get(index).get(4);
        day_5.setText(tempWeather.date);
        day_5_temp.setText((tempWeather.min_temp+tempWeather.max_temp)/2+Unit);
        day_5_wea.setText(tempWeather.weather);
        return rootView;
    }

}
