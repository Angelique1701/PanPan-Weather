package com.example.panpansoal1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.panpansoal1.repository.WeatherRepository
import com.example.panpansoal1.viewmodel.WeatherViewModel
import com.example.panpansoal1.view.HomePage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = WeatherRepository()
        val viewModel = WeatherViewModel(repository)

        setContent {
            HomePage(viewModel = viewModel)
        }
    }
}
