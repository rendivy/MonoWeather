package com.example.diapplication.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Forecastday(
    val date: String,
    @SerialName("day") val day: Day,
    @SerialName("astro") val astro: Astro?,
    @SerialName("hour") val hourList: List<Hour>,
    @SerialName("totalprecip_mm") val totalPrecipitationMm: Double = 0.0,
)