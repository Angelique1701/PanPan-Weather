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
import com.example.panpansoal1.model.WeatherDataMapper
import com.example.panpansoal1.viewmodel.WeatherViewModel
import com.example.panpansoal1.repository.WeatherRepository
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.text.font.FontWeight.Companion.Bold


@Composable
fun HomePage(viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf("") }
    val weather = viewModel.weatherState.value
    val error = viewModel.error.value
    val currentTime = remember { SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()) }
    val currentDate = remember {
        SimpleDateFormat("d MMM", Locale.getDefault()).format(Date())
    }
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

            // 🔍 TextField dan tombol search sejajar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Enter City") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { viewModel.fetchWeather(city, "2833abb8068f9cb2346de10e82a6f2c1") },
                    modifier = Modifier.height(56.dp)
                ) {
                    Text("Search")
                }
            }

            // 🧾 Konten utama
            if (error.isNotEmpty()) {
                Text(text = error, color = Color.Red)
            } else if (weather != null) {

                val weatherType = weather.weather.firstOrNull()?.main ?: "Clear"
                val pandaImage = when (weatherType) {
                    "Rain" -> R.drawable.rain
                    "Clouds" -> R.drawable.clouds
                    else -> R.drawable.clear
                }

                // 🏙️ Kota & tanggal
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Location Icon",
                            tint = Color.White,
                        )
                        Text(
                            text = weather.name,
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = currentDate,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = Bold
                        )
                    )

                    Text(
                        text = "Updated at: $currentTime",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                    )
                }

                // 🌡️ Suhu + gambar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "${weather.main.temp}°C",
                            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 36.sp),
                            color = Color.White
                        )
                        Text(
                            text = weather.weather.firstOrNull()?.description ?: "",
                            color = Color.LightGray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Image(
                        painter = painterResource(id = pandaImage),
                        contentDescription = "Weather Image",
                        modifier = Modifier.size(120.dp)
                    )
                }

                // 📊 Grid info (humidity, wind, dll)
                val weatherList = WeatherDataMapper.fromApi(weather)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(weatherList) { item ->
                        WeatherCard(item = item)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🌅 Sunrise & Sunset
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.vector),
                            contentDescription = "Sunrise",
                            modifier = Modifier.size(48.dp)
                        )
                        Text("Sunrise", color = Color.White)
                        Text(formatUnixTime(weather.sys.sunrise), color = Color.Yellow)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.vector_21png),
                            contentDescription = "Sunset",
                            modifier = Modifier.size(48.dp)
                        )
                        Text("Sunset", color = Color.White)
                        Text(formatUnixTime(weather.sys.sunset), color = Color(0xFFFF7043))
                    }
                }
            }
        }
    }
}


    fun formatUnixTime(timestamp: Long): String {
    val date = Date(timestamp * 1000)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    val repository = WeatherRepository()
    val dummyViewModel = WeatherViewModel(repository)
    HomePage(viewModel = dummyViewModel)
}


