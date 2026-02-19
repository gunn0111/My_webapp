package com.example.my_webapp.data.repository

import com.example.my_webapp.data.api.RetrofitInstance
import com.example.my_webapp.data.model.WeatherResponse

class WeatherRepository {

    private val apiKey = "cc4b1c969d20849d80ee8cfc2df39e38"

    suspend fun getWeather(city: String): WeatherResponse {
        return RetrofitInstance.api.getWeather(
            city = city,
            apiKey = apiKey,
            units = "metric"
        )
    }
}