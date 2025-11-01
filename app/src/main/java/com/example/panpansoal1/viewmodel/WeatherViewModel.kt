package com.example.panpansoal1.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.panpansoal1.model.WeatherModel
import com.example.panpansoal1.repository.WeatherRepository
import kotlinx.coroutines.launch
import android.util.Log

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherState = mutableStateOf<WeatherModel?>(null)
    val weatherState: State<WeatherModel?> = _weatherState

    private val _error = mutableStateOf("")
    val error: State<String> = _error

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val result = repository.getWeather(city, apiKey)
                Log.d("WeatherViewModel", "Result: $result")
                _weatherState.value = result
                _error.value = ""
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error: ${e.message}")
                _error.value = "Failed to fetch weather data"
            }
        }
    }
}
