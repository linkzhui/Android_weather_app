package com.example.raymon.android_weather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raymon on 10/27/17.
 */

public class CityWeatherAdapter extends FragmentStatePagerAdapter {
    List<City> city;
    public CityWeatherAdapter(FragmentManager fm) {
        super(fm);
        city = CityData.city_list;
    }

    @Override
    public Fragment getItem(int position) {
        City city_name = city.get(position);
        return new CityWeatherFragment(city_name);
    }

    @Override
    public int getCount() {
        Log.e("get forecast size",CityData.next_forecast.size()+"");
        return city.size();
    }

    public void updatePage()
    {
        notifyDataSetChanged();
    }


    @Override
    public int getItemPosition(Object object)
    {
        return PagerAdapter.POSITION_NONE;
    }
}
