package com.trenchant.weather;

import com.trenchant.weather.model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface JsonPlaceHolderApi
{

    String baseURL = "https://api.openweathermap.org/data/2.5/weather?q=";

    @Headers("Content-Type: application/json")
    @GET("ceed18b0971117288ee6eff9953b0440")
    Call<Feed> getData();
}
