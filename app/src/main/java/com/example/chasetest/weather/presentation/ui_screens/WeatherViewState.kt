package com.example.chasetest.weather.presentation.ui_screens

import com.example.chasetest.weather.domain.model.WeatherResponse

data class WeatherViewState(
    val isLoading: Boolean = false,
    val weatherResponse: WeatherResponse? = null,
    val error: String? = null
)