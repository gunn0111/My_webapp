package com.example.my_webapp.data.model

data class WeatherResponse(
    val name: String,
    val main: Main,
    val wind: Wind
)

data class Main(
    val temp: Double
)

data class Wind(
    val speed: Double
)
