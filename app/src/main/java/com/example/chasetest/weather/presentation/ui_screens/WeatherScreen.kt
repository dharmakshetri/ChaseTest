package com.example.chasetest.weather.presentation.ui_screens

import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.chasetest.utils.Result
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import kotlinx.coroutines.delay

@Composable
fun WeatherScreen(location: Location?, viewModel: WeatherViewModel = hiltViewModel()) {
    val weatherState by viewModel.weatherState.collectAsState()

    LaunchedEffect(location) {
        location?.let {
            viewModel.fetchWeatherByLocation(it.latitude, it.longitude)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        CitySearchField { cityName ->
            viewModel.fetchWeatherByCity(cityName)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (weatherState) {
            is Result.Loading -> CircularProgressIndicator()
            is Result.Success -> {
                val weather = (weatherState as Result.Success).data
                Text(text = "City: ${weather.name}")
                Text(text = "Temperature: ${weather.main.temp}°C")
                Text(text = "Weather: ${weather.weather[0].description}")
                // Display weather icon
                Image(
                    painter = rememberImagePainter("http://openweathermap.org/img/w/${weather.weather[0].icon}.png"),
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(64.dp)
                )
            }
            is Result.Error -> {
                Text(text = "Error: ${(weatherState as Result.Error).message}")
            }
        }


    }
}

@Composable
fun CitySearchField(onSearch: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(searchText) {
        delay(500)  // 500ms debounce
        if (searchText.isNotEmpty()) {
            onSearch(searchText)
        }
    }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        label = { Text("Enter city name") }
    )
}