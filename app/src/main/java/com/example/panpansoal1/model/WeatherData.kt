package com.example.panpansoal1.model

import com.example.panpansoal1.R

object WeatherDataMapper {

    fun fromApi(model: WeatherModel): List<Weather> {
        return listOf(
            Weather(R.drawable.icon_humidity, "HUMIDITY", model.main.humidity),
            Weather(R.drawable.icon_wind, "WIND", model.wind.speed.toInt()),
            Weather(R.drawable.icon_feels_like, "FEELS LIKE", model.main.feels_like.toInt()),
            Weather(R.drawable.vector_2, "RAIN FALL", model.rain?.`1h`?.toInt() ?: 0),
            Weather(R.drawable.devices, "PRESSURE", model.main.pressure),
            Weather(R.drawable.cloud, "CLOUDS", model.clouds.all)
        )
    }
}

data class Weather(
    val imageResId: Int,
    val name: String,
    val percentage: Int
)
