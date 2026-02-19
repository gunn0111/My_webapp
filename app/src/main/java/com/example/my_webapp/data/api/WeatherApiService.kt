package com.example.my_webapp.data.api
import com.example.my_webapp.data.model.WeatherResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("appid") apiKey: String,
        @Query("q") city: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}