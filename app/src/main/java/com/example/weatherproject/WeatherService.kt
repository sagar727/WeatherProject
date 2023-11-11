package com.example.weatherproject

import com.example.weather.test.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast.json?")

    suspend fun getWeather(
        @Query("key")
        key: String,
        @Query("q")
        place: String,
        @Query("days")
        number: Int = 3
    ): WeatherData
}