package com.example.raymon.android_weather;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PlaceSelectionListener, LocationListener {
    private LocationManager locationManager;
    private String provider;
    private static CityAdapter adapter;
    public static String unit = "metric";  //For temperature in Fahrenheit use units=imperial   For temperature in Celsius use units=metric
    private String current_unit = "metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("life cycle test", "onCreate stage");
        ListView listView = findViewById(R.id.city_list_view);
        adapter = new CityAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                Intent intent = new Intent(getApplicationContext(),CityWeather.class);
                intent.putExtra("city name",""+position);
                startActivity(intent);
            }
        });
        registerForContextMenu(listView);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setTypeFilter(5).build();


        // Retrieve the PlaceAutocompleteFragment.
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setOnPlaceSelectedListener(this);


        getLocation();



        adapter.notifyDataSetChanged();

    }

    public Location getLocation() {
        Location location = null;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        provider = LocationManager.NETWORK_PROVIDER;
        Log.i("isNetworkEnabled", isNetworkEnabled + "");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            Log.i("permission", "granted");
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                System.out.println("Provider " + provider + " has been selected.");
                onLocationChanged(location);
            } else {
                Log.e("local error", "location not founded");
            }
        }
        return location;
    }

    @Override
    public void onPlaceSelected(Place place) {
        Log.i("onPlaceSelected", "Place Selected: " + place.getName());

        CityData city_data = new CityData();
        if(city_data.add(place.getName().toString()))
        {
            new LoadCurrentWeatherAsync().execute(place.getName().toString());
        }


//        if (json_weather != null)
//        {
//            Log.i("The Json Object received from the open weather","Not Null!");
//        }
//        else {
//            Log.i("The Json Object received from the open weather", "Null");
//        }
//        // Format the returned place's details and display them in the TextView.
//        mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(), place.getId(),
//                place.getAddress(), place.getPhoneNumber(), place.getWebsiteUri()));
//        Log.e("onPlaceSelected",place.getName().toString());
//        CharSequence attributions = place.getAttributions();
//        if (!TextUtils.isEmpty(attributions)) {
//            mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
//        } else {
//            mPlaceAttribution.setText("");
//        }
    }

    private static JSONObject getJSON(String city) throws MalformedURLException {
        Log.e("current in getJSON life cycle",city);
        String[] city_name = city.split(" ");
        StringBuilder sb = new StringBuilder();
        String Prefix = "";
        for(String element:city_name)
        {
            sb.append(Prefix);
            sb.append(element);
            Prefix = "_";
        }
        final String OPEN_WEATHER_MAP_API_FORECAST =
                "http://api.openweathermap.org/data/2.5/forecast?q=%s&units=%s&APPID=56b7d028c2024a76ca8be575a4123b82";
        final String timezonedb_api  = "http://api.timezonedb.com/v2/get-time-zone?key=059VHEU07NYS&format=json&by=position&lat=37.34&lng=-121.89";
        try {
//            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, sb.toString(),unit));
//            HttpURLConnection connection =
//                    (HttpURLConnection)url.openConnection();
//
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream()));
//
//            StringBuffer json = new StringBuffer(1024);
//            String tmp="";
//            while((tmp=reader.readLine())!=null)
//                json.append(tmp).append("\n");
//            reader.close();
//
//            //Log.i("Received Json Object",json.toString());
//            JSONObject data = new JSONObject(json.toString());
//
//            // This value will be 404 if the request was not
//            // successful
//            if(data.getInt("cod") != 200){
//                return null;
//            }
//
//            //update the weather
//            update_weather(city,data);

            //update the forecast data
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API_FORECAST, sb.toString(),unit));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(2048);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();
            JSONObject data = new JSONObject(json.toString());
//            if(data.getInt("cod") != 200){
//                return null;
//            }
            Log.e("jsonobject data",data.toString());
            update_forecast(city,data);

            Log.e("after the forecast","here");
            int index = CityData.map.get(city);
            City cur_city = CityData.city_list.get(index);
            String lat = cur_city.getLat();
            String log = cur_city.getLog();
            //update_time

            url = new URL(String.format(timezonedb_api,lat,log));
//            String new_ur = String.format(timezonedb_api,lat,log);
//            Log.e("new url",url.toString());
            //url = new URL(timezonedb_api);

            connection =
                    (HttpURLConnection)url.openConnection();

            reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            json = new StringBuffer(512);
            String time = "";
            while((time=reader.readLine())!=null)
                json.append(time).append("\n");
            reader.close();

            //Log.i("Received Json Object",json.toString());
            data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
//            if(data.getString("status")=="OK")
//            {
//                Log.e("the server repsonse","OK");
//            }
//            else{
//                Log.e("the server repsonse","FAIL");
//                Log.e("why fail",data.toString());
//            }
            String date = data.getString("formatted");
            Log.e("current date","this is "+date);
            cur_city.setDay(date);


            return data;
        }catch(Exception e){
            return null;
        }
    }



    private static class LoadCurrentWeatherAsync extends AsyncTask<String, Void, Void>
    {

        @Override
        protected Void doInBackground(String... city) {
            try {
                Log.e("begin to get json object","in asynctask cycle");
                getJSON(city[0]);
//
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            Log.i("", "onPostExecute(Result result) called");
            adapter.notifyDataSetChanged();
        }

    }

    private static void update_forecast(String city,JSONObject json) throws JSONException {
        Log.e("begin to update the forecast","begin");
        List<Temp_Weather> temp_list = new ArrayList<>();
        JSONArray list = json.getJSONArray("list");
        List<City> city_list = CityData.city_list;
        HashMap<String,Integer> map = CityData.map;
        Integer index= map.get(city);
        Log.i("city name",city);

        CityData cityData = new CityData();



        for(int i = 0 ;i<8;i++)
        {
            JSONObject temp_object = list.getJSONObject(i).getJSONObject("main");
            JSONObject temp_array = list.getJSONObject(i).getJSONArray("weather").getJSONObject(0);
            int min_temp  = temp_object.getInt("temp_min");
            int max_temp= temp_object.getInt("temp_max");
            String weather_status = temp_array.getString("main");
            temp_list.add(new Temp_Weather(min_temp,max_temp,weather_status));
            //Log.i("update_forecast","min: "+min_temp+" max: "+max_temp+" weather: "+weather_status);
        }

        //first

        cityData.addForecast(temp_list);

        List<Temp_Weather> temp_list_forcast = new ArrayList<>();
        for(int i = 2 ;i<list.length();i+=8)
        {
            JSONObject temp_object = list.getJSONObject(i).getJSONObject("main");
            JSONObject temp_array = list.getJSONObject(i).getJSONArray("weather").getJSONObject(0);
            int min_temp  = temp_object.getInt("temp_min");
            int max_temp= temp_object.getInt("temp_max");
            String weather_status = temp_array.getString("main");
            String date = list.getJSONObject(i).getString("dt_txt").split(" ")[0];
            Temp_Weather new_temp = new Temp_Weather(min_temp,max_temp,weather_status);
            new_temp.setDate(date);
            temp_list_forcast.add(new_temp);
            Log.i("update_forecast","min: "+new_temp.min_temp+" max: "+new_temp.max_temp+" weather: "+new_temp.weather+" date: "+new_temp.date);
        }
        cityData.addNextForecast(temp_list_forcast);

        City cur = city_list.get(index);
        String lat = json.getJSONObject("city").getJSONObject("coord").getString("lat");
        if(lat!=null)
        {
            Log.e("Latitude",lat);
        }
        else{
            Log.e("Latitude","not founded");
        }

        cur.setLat(lat);
        String log = json.getJSONObject("city").getJSONObject("coord").getString("lon");
        if(log!=null)
        {
            Log.e("log",log);
        }
        else{
            Log.e("log","not founded");
        }
        cur.setLog(log);
        int cur_temp = (list.getJSONObject(3).getJSONObject("main").getInt("temp_min")+list.getJSONObject(3).getJSONObject("main").getInt("temp_max"))/2;
        String cur_weather_status = list.getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main");
        Log.e("current weather status",cur_weather_status);
        Log.e("current temp",cur_temp+"");
        cur.setTemperature(cur_temp+"");
        cur.setWeatherStatus(cur_weather_status);
    }

    private void update_weather(String city,JSONObject jsonObject) throws JSONException, MalformedURLException {
        List<City> city_list = CityData.city_list;
        HashMap<String,Integer> map = CityData.map;
        Integer index= map.get(city);
        Log.i("city name",city);
        City cur = city_list.get(index);
        cur.setTemperature(jsonObject.getJSONObject("main").getString("temp"));
        cur.setUnit(unit);
        current_unit = unit;
//        cur.setDay(Now_date);
        cur.setWeatherStatus(jsonObject.getJSONArray("weather").getJSONObject(0).getString("main"));
        String lat = jsonObject.getJSONObject("coord").getString("lat");
//        Log.e("Latitude",lat);
        cur.setLat(lat);
        String log = jsonObject.getJSONObject("coord").getString("lon");
//        Log.e("Logitude",log);
        cur.setLog(log);
    }

    @Override
    public void onError(Status status) {
        Log.e("onError", "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

//    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
//                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
//        Log.e("formatPlaceDetails", res.getString(R.string.place_details, name, id, address, phoneNumber,
//                websiteUri));
//        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
//                websiteUri));
//    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Life cycle test", "We are at onResume()");
        if(CityData.city_list!=null && CityData.city_list.size()>0)
        {
            if(!current_unit.equals(unit))
            {
                Log.e("main activity unit",current_unit);
                Log.e("current unit",unit);
                if(current_unit.equals("metric"))
                {
                    current_unit = "imperial";
                    adapter.notifyDataSetChanged();

                }
                else{
                    current_unit = "metric";
                    adapter.notifyDataSetChanged();

                }
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View v, ContextMenu.ContextMenuInfo menuInfo)
    {

        if(v.getId()==R.id.city_list_view)
        {
            menu.add("Delete");

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        CityData citydata = new CityData();
        City city = CityData.city_list.get(info.position);
        boolean result = citydata.remove(city.getCity());
        Log.i("onContextItemSelected delete result",result+"");
        if(result)
        {
            adapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    private void changeUnit(String unit, Integer different)
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
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Life cycle test", "We are at onPause()");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Life cycle test", "We are at onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Life cycle test", "We are at onDestroy()");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.setting,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //metric = celsius
        //Fahrenheit = imperial
        switch (item.getItemId())
        {
            case R.id.setting_C:
                if(unit.equals("metric"))
                {
                    Toast.makeText(this, "The Unit is already Celsius", Toast.LENGTH_SHORT).show();
                }
                else if(unit.equals("imperial")){
                    unit = "metric";
                    current_unit = unit;
                    changeUnit(unit,32);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "Change to Celsius (C) successful!", Toast.LENGTH_SHORT).show();
                }

                return true;
            case R.id.setting_F:
                if(unit.equals("metric"))
                {
                    unit = "imperial";
                    current_unit = unit;
                    changeUnit(unit,-32);
                    adapter.notifyDataSetChanged();
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
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i=0; i<maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(addressStr);
                builder.append(" ");
            }

//            Log.e("latituteField",String.valueOf(lat));
//            Log.e("longitudeField",String.valueOf(lng));
            String current_city = parseAddress(address.get(0).getAddressLine(1));
            Log.e("current city is:",current_city);
            CityData city_data = new CityData();
            city_data.setCurrent_city(current_city);
            if(!city_data.exist(current_city)){
                city_data.add(current_city);
                new LoadCurrentWeatherAsync().execute(current_city);
            }

        } catch (IOException e) {
            // Handle IOException
        }
    }

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            locationManager.removeUpdates(this);
            onLocationChanged(location);
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
    private String parseAddress(String address)
    {
        if(address == null)
        {
            return null;
        }
        for(int i =0;i<address.length();i++)
        {
            if(address.charAt(i)==',')
            {
                return address.substring(0,i);
            }
        }
        return address;
    }
}
