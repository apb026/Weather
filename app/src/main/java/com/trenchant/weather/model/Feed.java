package com.trenchant.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trenchant.weather.model.weather.Weather;

import java.util.ArrayList;

public class Feed
{
    @SerializedName("weather")
    @Expose
    private ArrayList<Weather> weather;

    @SerializedName("main")
    @Expose
    private Data main;

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public Data getMain() {
        return main;
    }

    public void setMain(Data main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "weather=" + weather +
                ", main=" + main +
                '}';
    }
}
