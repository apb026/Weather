package com.trenchant.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data
{
    @SerializedName("temp")
    @Expose
    private Double temp;

    @SerializedName("temp_min")
    @Expose
    private Double temp_min;

    @SerializedName("temp_max")
    @Expose
    private Double temp_max;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    @Override
    public String toString() {
        return "Data{" +
                "temp=" + temp +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                '}';
    }
}
