package com.example.panpansoal1.repository

import com.example.panpansoal1.model.WeatherModel
import com.example.panpansoal1.service.ApiClient

class WeatherRepository {
    private val api = ApiClient.retrofit

    suspend fun getWeather(city: String, apiKey: String): WeatherModel {
        return api.getWeather(city, apiKey)
    }
}


