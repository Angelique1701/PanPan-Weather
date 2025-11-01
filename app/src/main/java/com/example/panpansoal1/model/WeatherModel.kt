package com.example.panpansoal1.model

data class WeatherModel(
    val weather: List<WeatherDesc>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val rain: Rain?,
    val sys: Sys,
    val name: String,
    val datetime: Any,
    val updatedAt: String
)

data class WeatherDesc(
    val main: String,
    val description: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double
)

data class Clouds(
    val all: Int
)

data class Rain(
    val `1h`: Double? = 0.0
)

data class Sys(
    val sunrise: Long,
    val sunset: Long
)
