package com.example.chasetest.weather.data.remote


import com.example.chasetest.weather.domain.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = "33e7c9aae4cfd6ed1a34f3b613f23fe8",//BuildConfig.API_KEY,
        @Query("units") units: String = "metric"  // You can use imperial or other units
    ): Response<WeatherResponse>

    @GET("weather")
    suspend fun getWeatherByLatLon(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = "33e7c9aae4cfd6ed1a34f3b613f23fe8",//BuildConfig.API_KEY,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>
}