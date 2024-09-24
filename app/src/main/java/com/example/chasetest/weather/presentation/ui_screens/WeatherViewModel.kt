package com.example.chasetest.weather.presentation.ui_screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.chasetest.weather.domain.model.WeatherResponse
import com.example.chasetest.weather.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.chasetest.utils.Result
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherState = MutableStateFlow<Result<WeatherResponse>>(Result.Loading)
    val weatherState: StateFlow<Result<WeatherResponse>> = _weatherState

    fun fetchWeatherByCity(cityName: String) {
        viewModelScope.launch {
            repository.getWeatherByCity(cityName).collect {
                _weatherState.value = it
            }
        }
    }

    fun fetchWeatherByLocation(lat: Double, lon: Double) {
        viewModelScope.launch {
            repository.getWeatherByCoordinates(lat, lon).collect {
                _weatherState.value = it
            }
        }
    }
}