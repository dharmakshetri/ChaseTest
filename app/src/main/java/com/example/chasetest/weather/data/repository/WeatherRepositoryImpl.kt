package com.example.chasetest.weather.data.repository

import arrow.core.Either
import com.example.chasetest.weather.data.mapper.toGeneralError
import com.example.chasetest.weather.data.remote.WeatherApi
import com.example.chasetest.weather.domain.model.NetworkError
import com.example.chasetest.weather.domain.model.WeatherResponse
import com.example.chasetest.weather.domain.repository.WeatherRepository
import javax.inject.Inject

/*
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getWeather(cityName: String): Either<NetworkError, WeatherResponse> {
        return Either.catch {
            weatherApi.getWeatherByCity(cityName, "33e7c9aae4cfd6ed1a34f3b613f23fe8")
        } .mapLeft { it.toGeneralError() }
    }
}*/
