package com.example.chasetest

import com.example.chasetest.weather.data.remote.WeatherApi
import com.example.chasetest.weather.domain.model.Main
import com.example.chasetest.weather.domain.model.Weather
import com.example.chasetest.weather.domain.model.WeatherResponse
import com.example.chasetest.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import com.example.chasetest.utils.Result
import okhttp3.ResponseBody
import retrofit2.Response

class WeatherRepositoryTest {

    private lateinit var repository: WeatherRepository
    private lateinit var apiService: WeatherApi

    @Before
    fun setUp() {
        apiService = mock(WeatherApi::class.java)
        repository = WeatherRepository(apiService)
    }

    @Test
    fun `test getWeatherByCity returns success`(): Unit = runBlocking {
        // Mock API response
        val weatherResponse = WeatherResponse(
            name = "New York",
            main = Main(temp = 22.0, pressure = 1012, humidity = 73),
            weather = listOf(Weather(description = "Clear sky", icon = "01d"))
        )
        val mockResponse = Response.success(weatherResponse)  // Wrap in Response.success

        // Mock the API service to return the mockResponse
        `when`(apiService.getWeatherByCity("New York")).thenReturn(mockResponse)

        // Call the repository method
        val result = repository.getWeatherByCity("New York")

        result.collect { emittedResult ->
            // Log or print the actual result emitted by the flow
            println(emittedResult)

            // Now check if it's a success
            assertTrue(emittedResult is Result.Success)
            if (emittedResult is Result.Success) {
                assertEquals("New York", emittedResult.data.name)
            }
        }

        // Verify that the API service was called
        verify(apiService).getWeatherByCity("New York")
    }

    @Test
    fun `test getWeatherByCity returns error`(): Unit = runBlocking {
        // Mock an error response (HTTP 404 or similar)
        val errorResponse = Response.error<WeatherResponse>(404, ResponseBody.create(null, "City not found"))

        // Mock the API service to return the errorResponse
        `when`(apiService.getWeatherByCity("UnknownCity")).thenReturn(errorResponse)

        // Call the repository method
        val result = repository.getWeatherByCity("UnknownCity")

        result.collect {
            assertTrue(it is Result.Error)
            assertEquals("API Error: City not found", (it as Result.Error).message)
        }

        // Verify that the API service was called
        verify(apiService).getWeatherByCity("UnknownCity")
    }
}