package com.trenchant.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.trenchant.weather.model.Data;
import com.trenchant.weather.model.Feed;
import com.trenchant.weather.model.weather.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity {

    private static final String APP_ID = "ceed18b0971117288ee6eff9953b0440";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    TextView nameOfCity, nameOfCountry, weatherStatus, Temperature, minTempMax, weatherStatus1, weatherStatus2, weatherStatus3;

    ImageView mweatherIcon, mweatherIcon1, mweatherIcon2, mweatherIcon3;
    String str = "";
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        //String lat = intent.getStringExtra("lat");
        //String lon = intent.getStringExtra("lon");

        weatherStatus = findViewById(R.id.status);
        Temperature = findViewById(R.id.currentTemp);
        minTempMax = findViewById(R.id.min_temp_max);
        nameOfCity = findViewById(R.id.city);
        nameOfCountry = findViewById(R.id.country);
        weatherStatus1 = findViewById(R.id.status_1);
        weatherStatus2 = findViewById(R.id.status_2);
        weatherStatus3 = findViewById(R.id.status_3);

        mweatherIcon = findViewById(R.id.img_status);
        mweatherIcon1 = findViewById(R.id.status_img_1);
        mweatherIcon2 = findViewById(R.id.status_img_2);
        mweatherIcon3 = findViewById(R.id.status_img_3);

        if(city.charAt(0)>=97 && city.charAt(0)<=122)
        {
            str = str + ((char) (city.charAt(0) - 32));
        }
        else
        {
            str = str + (city.charAt(0));
        }
        for (int j = 1; j < city.length(); j++) {
            str += city.charAt(j);
        }
        nameOfCity.setText(str + ", ");

        Calendar calendar = Calendar.getInstance();
        //System.out.println(new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime()));

        SimpleDateFormat sdf = new SimpleDateFormat("EE",Locale.ENGLISH);

        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();
        weatherStatus1.setText(sdf.format(date));
        calendar.add(Calendar.DATE, 1);
        Date date2 = calendar.getTime();
        weatherStatus2.setText(sdf.format(date2));
        calendar.add(Calendar.DATE, 1);
        Date date3 = calendar.getTime();
        weatherStatus3.setText(sdf.format(date3));

        mQueue = VolleySingleton.getInstance(this).getRequestQueue();

        String url = BASE_URL+city+"&appid="+APP_ID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("weather");
                            JSONObject singleObj = jsonArray.getJSONObject(0);

                            int resourceID = getResources().getIdentifier(getIcon(singleObj.getInt("id")), "drawable",getPackageName());
                            mweatherIcon.setImageResource(resourceID);

                            String main = singleObj.getString("main");
                            String description = singleObj.getString("description");

                            weatherStatus.setText(main);

                            JSONObject jsonObject = response.getJSONObject("main");

                            Double temp = jsonObject.getDouble("temp");
                            Temperature.setText(String.format("%.1f",temp-273.17)+"°C");

                            Double tempMin = (jsonObject.getDouble("temp_min")-273.15);
                            Double tempMax = (jsonObject.getDouble("temp_max")-273.15);
                            minTempMax.setText("H "+String.format("%.0f",tempMax)+" L "+String.format("%.0f",tempMin));

                            JSONObject jsonObject2 = response.getJSONObject("sys");
                            nameOfCountry.setText(jsonObject2.getString("country"));


                        } catch (JSONException e) {
                            Toast.makeText(SecondActivity.this, "Exception : "+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SecondActivity.this, "Error : "+error, Toast.LENGTH_SHORT).show();
            }
        });

        mQueue.add(request);
    }

    private String getIcon(int uid)
    {
        if (uid>=200 && uid<=232)
        {
            return "thunderstrom";
        }
        else if (uid>=300 && uid<=321)
        {
            return "humidity";
        }
        else if (uid>=500 && uid<=531)
        {
            return "shower";
        }
        else if (uid>=600 && uid<=622)
        {
            return "fog";
        }
        else if (uid>=701 && uid<=781)
        {
            return "fog";
        }else if (uid==800)
        {
            return "sunrise";
        }
        else if (uid>=801 && uid<=804)
        {
            return "cloudy";
        }

        return "error";
    }
}