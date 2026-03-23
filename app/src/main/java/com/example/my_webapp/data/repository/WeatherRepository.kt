package com.example.my_webapp.data.repository

import com.example.my_webapp.BuildConfig
import com.example.my_webapp.data.api.RetrofitInstance
import com.example.my_webapp.data.model.WeatherResponse

class WeatherRepository {

    private val apiKey = BuildConfig.WEATHER_API_KEY

    init {
        require(apiKey.isNotEmpty()) {
            "WEATHER_API_KEY is not set. Add it to local.properties (see local.properties.example)."
        }
    }

    suspend fun getWeather(city: String): WeatherResponse {
        return RetrofitInstance.api.getWeather(
            city = city,
            apiKey = apiKey,
            units = "metric"
        )
    }
}