package com.example.chasetest.weather.presentation.util

import android.location.Geocoder
import android.location.Location

fun Location.toCityName(geocoder: Geocoder): String {
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
    return addresses?.firstOrNull()?.locality ?: "Unknown"
}