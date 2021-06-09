package com.trenchant.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText uanme,upass;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uanme = findViewById(R.id.uname);
        upass = findViewById(R.id.upass);

        submit = findViewById(R.id.btn_submit);

            submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                 != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1000);
                }
                else
                {
                    LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    try {
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        String city = hereLocation(location.getLatitude(), location.getLongitude());

                        if(uanme.getText().toString().trim().equalsIgnoreCase("Atharva") && upass.getText().toString().equals("12345"))
                        {
                            //String city=uanme.getText().toString().toLowerCase();
                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            intent.putExtra("city", city);
                            //intent.putExtra("lat", lat);
                            //intent.putExtra("lon", lon);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Exception : "+e, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private String hereLocation(Double latit, Double longi)
    {
        String cityName = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latit,longi,1);
            if (addresses.size() > 0)
            {
                for (Address address : addresses)
                {
                    if(address.getLocality() != null && address.getLocality().length() > 0)
                    {
                        cityName = address.getLocality();
                        break;
                    }
                }
            }
        }catch (IOException e)
        {
            Toast.makeText(this, "Exception : "+e, Toast.LENGTH_SHORT).show();
        }
        return cityName;
    }
}