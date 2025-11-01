package com.example.panpansoal1.model

data class WeatherModel(
    val weather: List<Weather>,
    val main: Main,
    val name: String
)

data class Weather(
    val main: String,
    val description: String
)

data class Main(
    val temp: Double
)
