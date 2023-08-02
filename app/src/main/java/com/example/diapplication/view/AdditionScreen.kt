@file:OptIn(FlowPreview::class, ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.diapplication.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.diapplication.R
import com.example.diapplication.data.model.DateConverter
import com.example.diapplication.domain.entity.Weather
import com.example.diapplication.presentation.WeatherViewModel
import kotlinx.coroutines.FlowPreview

@Composable
fun ForecastWeatherScreen(weatherState: Weather?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    )
    {
        HourlyForecastScreen(weatherState = weatherState)
        Spacer(modifier = Modifier.height(32.dp))
        DailyForecastScreen(weatherState = weatherState)
    }
}

@Composable
fun HourlyForecastScreen(weatherState: Weather?) {
    val lazyListState = rememberLazyListState()
    Text(
        text = "Hourly Forecast", style = TextStyle(
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
            fontWeight = FontWeight(400),
            color = Color.Gray,
        ), modifier = Modifier.padding(start = 32.dp, bottom = 16.dp)
    )
    LazyRow(
        state = lazyListState,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(start = 32.dp, end = 32.dp)
            .fillMaxWidth()
    ) {
        for (i in weatherState?.forecast?.forecastDayList?.get(0)?.hourList?.indices!!) {
            item {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                ) {
                    Text(
                        text = "$i:00",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                            fontWeight = FontWeight(400),
                            color = Color.White,
                        ),
                    )
                    WeatherConditionImage(
                        weatherCondition = weatherState.forecast.forecastDayList[0].hourList[i].condition.text,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                            .size(36.dp)

                    )
                    Text(
                        text = weatherState.forecast.forecastDayList[0].hourList[i].tempC.toString() + "°C",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                            fontWeight = FontWeight(400),
                            color = Color.Gray,
                        ),
                    )
                }
            }

        }
    }
}


@Composable
fun DailyForecastScreen(weatherState: Weather?) {
    Text(
        text = "Daily Forecast", style = TextStyle(
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
            fontWeight = FontWeight(400),
            color = Color.Gray,
        ), modifier = Modifier.padding(start = 32.dp, bottom = 16.dp)
    )
    LazyRow(
        modifier = Modifier
            .padding(start = 32.dp, end = 32.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for (j in 0..2) {
            for (i in 0 until weatherState?.forecast?.forecastDayList!!.size) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = DateConverter.invertDate(weatherState.forecast.forecastDayList[i].date),
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                                fontWeight = FontWeight(400),
                                color = Color.White,
                            ),
                        )
                        WeatherConditionImage(
                            weatherCondition = weatherState.forecast.forecastDayList[i].day.condition.text,
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 8.dp)
                                .size(36.dp)

                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.down_arrow),
                                contentDescription = "down_arrow",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = weatherState.forecast.forecastDayList[i].day.minimumTemperature.toInt()
                                    .toString() + "°C",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                                    fontWeight = FontWeight(400),
                                    color = Color.Gray,
                                ),
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.up_arrow),
                                contentDescription = "down_arrow",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = weatherState.forecast.forecastDayList[i].day.maximumTemperature.toInt()
                                    .toString() + "°C",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                                    fontWeight = FontWeight(400),
                                    color = Color.Gray,
                                ),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}


@Composable
fun AddLocationScreen(
    weatherViewModel: WeatherViewModel, navController: NavController
) {
    var city by remember { mutableStateOf("") }
    val errorState by weatherViewModel.anotherCityError.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(32.dp),
            text = errorState.toString(),
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color.White,
            ),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = {
                Text(
                    text = "Enter city name",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                        fontWeight = FontWeight(400),
                        color = Color.White,
                    )
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color.White,
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.White
                ),
        )
        Spacer(modifier = Modifier.height(32.dp))
        ExtendedFloatingActionButton(
            onClick = { weatherViewModel.updateAnotherCityWeather(cityName = city,
                navController = navController) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.upload_cloud),
                    contentDescription = "add",
                    tint = Color.White
                )
            },
            text = {
                Text(
                    text = "Add another city",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                        fontWeight = FontWeight(400),
                        color = Color.White,
                    )
                )
            },
            containerColor = Color.Transparent)
    }
}


@Composable
fun DetailsScreen(weatherState: Weather?) {
    Column(modifier = Modifier.padding(start = 32.dp)) {
        Text(
            text = "Precipitation",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color(0xFF616161),
            ),
        )
        Text(
            text = weatherState?.forecast?.forecastDayList?.get(0)?.totalPrecipitationMm.toString() + " mm",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color.White,
            ),
        )
    }
    Column(modifier = Modifier.padding(start = 32.dp, top = 8.dp)) {
        Text(
            text = weatherState?.current?.windDirection.toString() + " Wind",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color(0xFF616161),
            ),
        )
        Text(
            text = weatherState?.current?.windKph.toString() + " km/h",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color.White,
            ),
        )
    }
    Column(modifier = Modifier.padding(start = 32.dp, top = 8.dp)) {
        Text(
            text = "Humidity",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color(0xFF616161),
            ),
        )
        Text(
            text = weatherState?.current?.humidity.toString() + " %",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color.White,
            ),
        )
    }
    Column(modifier = Modifier.padding(start = 32.dp, top = 8.dp)) {
        Text(
            text = "UV",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color(0xFF616161),
            ),
        )
        Text(
            text = weatherState?.current?.uv.toString(),
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color.White,
            ),
        )
    }
    Column(modifier = Modifier.padding(start = 32.dp, top = 8.dp)) {
        Text(
            text = "Feels Like",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color(0xFF616161),
            ),
        )
        Text(
            text = weatherState?.current?.feelsLikeCelsius.toString(),
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color.White,
            ),
        )
    }
    Column(modifier = Modifier.padding(start = 32.dp, top = 8.dp)) {
        Text(
            text = "Pressure",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color(0xFF616161),
            ),
        )
        Text(
            text = weatherState?.current?.pressureMb.toString() + " mb",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.ubuntu_condensed)),
                fontWeight = FontWeight(400),
                color = Color.White,
            ),
        )
    }
}