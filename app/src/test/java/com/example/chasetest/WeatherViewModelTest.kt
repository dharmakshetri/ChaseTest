package com.example.chasetest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.chasetest.weather.domain.model.Main
import com.example.chasetest.weather.domain.model.Weather
import com.example.chasetest.weather.domain.model.WeatherResponse
import com.example.chasetest.weather.domain.repository.WeatherRepository
import com.example.chasetest.weather.presentation.ui_screens.WeatherViewModel
import com.example.chasetest.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()  // Rule to allow LiveData to work synchronously

    private lateinit var viewModel: WeatherViewModel
    private val repository: WeatherRepository = mock()  // Mock the repository
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        viewModel = WeatherViewModel(repository)
    }

    @Test
    fun `test fetchWeatherByCity returns success`() = runTest(testDispatcher) {
        // Mock response data
        val weatherResponse = WeatherResponse(
            name = "New York",
            main = Main(temp = 22.0, pressure = 1012, humidity = 73),
            weather = listOf(Weather(description = "Clear sky", icon = "01d"))
        )

        // Mock repository to return the success flow
        `when`(repository.getWeatherByCity("New York")).thenReturn(
            flow { emit(Result.Success(weatherResponse)) }
        )

        // Trigger the function
        viewModel.fetchWeatherByCity("New York")

        // Assert that the state flow contains success
        assertTrue(viewModel.weatherState.value is Result.Success)
        assertEquals("New York", (viewModel.weatherState.value as Result.Success).data.name)

        // Verify that the repository was called
        verify(repository).getWeatherByCity("New York")
    }

    @Test
    fun `test fetchWeatherByCity returns error`() = runTest(testDispatcher) {
        // Mock repository to return an error
        `when`(repository.getWeatherByCity("UnknownCity")).thenReturn(
            flow { emit(Result.Error("City not found")) }
        )

        // Trigger the function
        viewModel.fetchWeatherByCity("UnknownCity")

        // Assert that the state flow contains an error
        assertTrue(viewModel.weatherState.value is Result.Error)
        assertEquals("City not found", (viewModel.weatherState.value as Result.Error).message)

        // Verify that the repository was called
        verify(repository).getWeatherByCity("UnknownCity")
    }
}