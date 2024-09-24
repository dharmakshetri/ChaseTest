package com.example.chasetest.weather.domain.repository

import com.example.chasetest.weather.data.remote.WeatherApi
import com.example.chasetest.utils.Result
import com.example.chasetest.weather.domain.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val apiService: WeatherApi
) {
    suspend fun getWeatherByCity(cityName: String): Flow<Result<WeatherResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getWeatherByCity(cityName)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response"))
            } else {
                emit(Result.Error("API Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network Error: ${e.message}"))
        }
    }

    suspend fun getWeatherByCoordinates(lat: Double, lon: Double): Flow<Result<WeatherResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getWeatherByLatLon(lat, lon)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Result.Success(it))
                } ?: emit(Result.Error("Empty response"))
            } else {
                emit(Result.Error("API Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Network Error: ${e.message}"))
        }
    }
}