package com.example.chasetest.weather.domain.model

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
)

data class Main(
    val temp: Double,
    val pressure: Int,
    val humidity: Int,
)

data class Weather(
    val description: String,
    val icon: String,
)
