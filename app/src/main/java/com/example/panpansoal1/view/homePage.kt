package com.example.panpansoal1.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.panpansoal1.R
import com.example.panpansoal1.viewmodel.WeatherViewModel
import com.example.panpansoal1.repository.WeatherRepository


@Composable
fun HomePage(viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf("") }
    val weather = viewModel.weatherState.value
    val error = viewModel.error.value

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.weather___home_2),
            contentDescription = "Background Weather",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Enter City") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.fetchWeather(city, "2833abb8068f9cb2346de10e82a6f2c1") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🧾 LazyColumn for weather content
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    if (error.isNotEmpty()) {
                        Text(text = error, color = Color.Red)
                    } else if (weather != null) {
                        val weatherType = weather.weather.firstOrNull()?.main ?: "Clear"
                        val pandaImage = when (weatherType) {
                            "Rain" -> R.drawable.rain
                            "Clouds" -> R.drawable.cloud
                            else -> R.drawable.clear
                        }

                        Image(
                            painter = painterResource(id = pandaImage),
                            contentDescription = "Panda Weather",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "${weather.main.temp}°C",
                            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp)
                        )
                        Text(
                            text = weather.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = weather.weather.firstOrNull()?.description ?: "",
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    val repository = WeatherRepository()
    val dummyViewModel = WeatherViewModel(repository)
    HomePage(viewModel = dummyViewModel)
}


