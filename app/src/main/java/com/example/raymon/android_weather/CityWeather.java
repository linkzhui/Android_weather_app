package com.example.raymon.android_weather;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.raymon.android_weather.MainActivity.unit;

public class CityWeather extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vpager;
    private CityWeatherAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("City Weather life cycle","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String data = intent.getStringExtra("city name");
        mAdapter = new CityWeatherAdapter(getSupportFragmentManager());
        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(Integer.parseInt(data));
        vpager.addOnPageChangeListener(this);
        mAdapter.updatePage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.setting,menu);
        return true;
    }

    private boolean changeUnit(String unit,Integer different)
    {
        if(CityData.city_list!=null&&CityData.city_list.size()>0)
        {
            for(int i = 0;i<CityData.city_list.size();i++)
            {
                City city = CityData.city_list.get(i);
                city.setUnit(unit);
                DecimalFormat df = new DecimalFormat("0.00");
                city.setTemperature(df.format(Double.parseDouble(city.getTemperature())-different)+"");
                List<Temp_Weather> daily = CityData.forecast.get(i);
                for(int j = 0;j<daily.size();j++)
                {
                    daily.get(j).max_temp-=different;
                    daily.get(j).min_temp-=different;
                }
                daily = CityData.next_forecast.get(i);
                for(int z = 0;z<daily.size();z++)
                {
                    daily.get(z).max_temp-=different;
                    daily.get(z).min_temp-=different;
                }

            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.setting_C:
                if(unit == "metric")
                {
                    Toast.makeText(this, "The Unit is already Celsius", Toast.LENGTH_SHORT).show();
                }
                else if(unit == "imperial"){
                    unit = "metric";
                    changeUnit(unit,32);
                    mAdapter.updatePage();
                    Toast.makeText(this, "Change to Celsius (C) successful!", Toast.LENGTH_SHORT);
                }

                return true;
            case R.id.setting_F:
                if(unit == "metric")
                {
                    unit = "imperial";
                    changeUnit(unit,-32);
                    mAdapter.updatePage();
                    Toast.makeText(this, "Change to Fahrenheit (F) successful!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "The Unit is already Fahrenheit", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onResume() {
        Log.e("City Weather cycle", "We are at onResume()");
        super.onResume();
    }
}

