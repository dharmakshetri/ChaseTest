package com.example.chasetest

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.chasetest.weather.presentation.ui_screens.WeatherScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.chasetest.utils.Result
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class WeatherScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
                WeatherScreen(location = null)  // Pass your location or mock data

        }
    }

    @Test
    fun testWeatherScreen_displayCityAndWeather() {
        // Check if text for the search bar exists
        composeTestRule.onNodeWithText("Enter city name").assertExists()

        // Test that after entering a city name, the correct result is displayed
        composeTestRule.onNodeWithText("Enter city name").performClick()
        composeTestRule.onNodeWithText("New York").assertExists()

        // You can simulate user inputs or check UI elements based on test scenarios
    }
}